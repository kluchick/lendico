package com.kliuchkevych.plangenerator.service;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.kliuchkevych.plangenerator.exceptions.RepaymentCalculationException;
import com.kliuchkevych.plangenerator.model.domain.Loan;
import com.kliuchkevych.plangenerator.model.domain.Repayment;
import org.springframework.stereotype.Service;

/**
 * Service layer of the application contains all business logic of generation repayment plan base on input loan data.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
@Service
public class RepaymentPlanService {

    /**
     * Responsible to generate repayment plan base on Loan data.
     *
     * @param loan with input values.
     * @return generated plan, represented as List of {@link Repayment} entities
     */
    public ArrayList<Repayment> generatePlan(final Loan loan) {

        try {
            // Comment: part of our contract. Can be used in systems with defined way of collaboration with outer
            // systems.
            // assertion should be turned on during run 'java -ea'
            assert loan.getDuration() > 0 : "duration value is negative";

            // data for calculate annuity
            final BigDecimal rate = loan.getNominalRate().divide(BigDecimal.valueOf(100), 9, RoundingMode.CEILING);
            final BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), 9, RoundingMode.CEILING);

            // calculate annuity
            final BigDecimal annuity = (monthlyRate.multiply(loan.getLoanAmount()))
                    .divide(
                            ONE.subtract((ONE.add(monthlyRate))
                                                 .pow(0 - loan.getDuration(), MathContext.DECIMAL64)), 9,
                            RoundingMode.CEILING);

            // prepare list for store plan entries
            final ArrayList<Repayment> plan = new ArrayList<>();

            // calculate first payment base on data provided by user.
            final Repayment firstRepayment =
                    calculateRepayment(annuity, loan.getLoanAmount(), rate, loan.getStartDate());

            // recursive function calculates each new payment base on previous one. Store entries into the prepared
            // List.
            fillPlan(plan, annuity, rate, firstRepayment);

            return plan;

            // catch exception to correctly dispay it to the end user
        } catch (ArithmeticException | AssertionError e) {
            throw new RepaymentCalculationException(e.getMessage(), loan);
        }
    }

    /**
     * Recursive function which calculates entries of the plan base on previous entry.
     * Store calculated entries into provided list.
     *
     * @param plan          list to store calculated entries
     * @param baseAnnuity   annuity calculated base on user input data
     * @param monthlyRate   loan rate per month
     * @param prevRepayment previously calculated payment to get data from
     */
    private void fillPlan(final ArrayList<Repayment> plan,
                          final BigDecimal baseAnnuity,
                          final BigDecimal monthlyRate,
                          final Repayment prevRepayment) {
        if (prevRepayment == null) {
            return;
        }

        plan.add(prevRepayment);

        if (prevRepayment.getRemainingOutstandingPrincipal().compareTo(ZERO) > 0) {
            final Repayment nextPayment =
                    calculateRepayment(baseAnnuity, prevRepayment.getRemainingOutstandingPrincipal(), monthlyRate,
                                       prevRepayment.getDate());
            fillPlan(plan, baseAnnuity, monthlyRate, nextPayment);
        }
    }

    /**
     * Calculate one repayment base on input parameters
     *
     * @param baseAnnuity annuity
     * @param amount      of money
     * @param rate        per month
     * @param prevDate    of the payment
     * @return calculated next payment
     */
    private Repayment calculateRepayment(final BigDecimal baseAnnuity,
                                         final BigDecimal amount,
                                         final BigDecimal rate,
                                         final LocalDateTime prevDate) {
        try {
            BigDecimal annuity = baseAnnuity;
            BigDecimal interest = rate.multiply(BigDecimal.valueOf(30))
                                      .multiply(amount)
                                      .divide(BigDecimal.valueOf(360), 9, RoundingMode.CEILING);
            BigDecimal principal = annuity.subtract(interest);
            if (amount.compareTo(principal) < 0) {
                principal = amount;
                annuity = principal.add(interest);
            }


            return Repayment.builder()
                            .borrowerPaymentAmount(annuity)
                            .interest(interest)
                            .principal(principal)
                            .initialOutstandingPrincipal(amount)
                            .remainingOutstandingPrincipal(amount.subtract(principal))
                            .date(prevDate.plusMonths(1))
                            .build();
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
