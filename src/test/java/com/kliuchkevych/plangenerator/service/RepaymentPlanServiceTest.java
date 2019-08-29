package com.kliuchkevych.plangenerator.service;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.kliuchkevych.plangenerator.exceptions.RepaymentCalculationException;
import com.kliuchkevych.plangenerator.model.domain.Loan;
import com.kliuchkevych.plangenerator.model.domain.Repayment;
import org.junit.Test;

/**
 * I'll not check the correct parameters of the {@link Loan} entity. It should be done by validation of input entity.
 */
public class RepaymentPlanServiceTest {

    RepaymentPlanService unit = new RepaymentPlanService();


    @Test
    public void generatePlan_happyPath() {

        //GIVEN
        final Loan loan =
                new Loan(BigDecimal.valueOf(5000), BigDecimal.valueOf(5), 24,
                         LocalDateTime.parse("2019-08-29T14:23:44"));

        final Repayment firstRecord = Repayment.builder()
                                               .borrowerPaymentAmount(new BigDecimal("219.356949567"))
                                               .date(LocalDateTime.parse("2019-09-29T14:23:44"))
                                               .initialOutstandingPrincipal(new BigDecimal("5000"))
                                               .interest(new BigDecimal("20.833333334"))
                                               .principal(new BigDecimal("198.523616233"))
                                               .remainingOutstandingPrincipal(new BigDecimal("4801.476383767"))
                                               .build();
        final Repayment lastRecord = Repayment.builder()
                                              .borrowerPaymentAmount(new BigDecimal("219.356926997"))
                                              .date(LocalDateTime.parse("2021-08-28T14:23:44"))
                                              .initialOutstandingPrincipal(new BigDecimal("218.446732279"))
                                              .interest(new BigDecimal("0.910194718"))
                                              .principal(new BigDecimal("218.446732279"))
                                              .remainingOutstandingPrincipal(new BigDecimal("0E-9"))
                                              .build();

        //WHEN
        final ArrayList<Repayment> plan = unit.generatePlan(loan);

        //THEN
        assertNotNull(plan);
        assertEquals(24, plan.size());
        assertEquals(firstRecord, plan.get(0));
        assertEquals(lastRecord, plan.get(plan.size() - 1));

    }

    @Test(expected = RepaymentCalculationException.class)
    public void generatePlan_exceptional_divideByZero() {

        //GIVEN
        final Loan loan =
                new Loan(new BigDecimal(0), new BigDecimal(0), 0, null);

        //WHEN-THEN
        final ArrayList<Repayment> plan = unit.generatePlan(loan);
    }

    @Test(expected = RepaymentCalculationException.class)
    public void generatePlan_exceptional_negativeDuration() {

        //GIVEN
        final Loan loan =
                new Loan(BigDecimal.valueOf(5000), BigDecimal.valueOf(5), -1,
                         LocalDateTime.parse("2019-08-29T14:23:44"));

        //WHEN-THEN
        final ArrayList<Repayment> plan = unit.generatePlan(loan);
    }

    //TODO: continue testing with different input values

}