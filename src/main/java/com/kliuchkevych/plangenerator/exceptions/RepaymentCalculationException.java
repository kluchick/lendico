package com.kliuchkevych.plangenerator.exceptions;

import com.kliuchkevych.plangenerator.model.domain.Loan;

/**
 * Our own business exception to notify user about errors during repayment plan generation.
 * Includes {@link Loan} entity as main reason of exception rising.
 *
 * Extends from RuntimeException to simplify development.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
public class RepaymentCalculationException extends RuntimeException {

    private final Loan loan;

    public RepaymentCalculationException(final String message, final Loan loan) {
        super(message);
        this.loan = loan;
    }

    public Loan getLoan() {
        return loan;
    }
}
