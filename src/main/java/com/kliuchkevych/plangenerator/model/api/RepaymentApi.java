package com.kliuchkevych.plangenerator.model.api;

import com.kliuchkevych.plangenerator.mappers.RepaymentMapper;

/**
 * API representation of the repayment plan entry.
 * Responsible to show resulting data in the suitable for user format.
 * <p>
 * Converting is implemented in {@link RepaymentMapper}.
 * All fields are String to fully control of representation.
 * <p>
 * Internal subclass of {@link RepaymentApi.RepaymentApiBuilder} simplify filling of the fields.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
public class RepaymentApi {

    private String borrowerPaymentAmount;
    private String date;
    private String initialOutstandingPrincipal;
    private String interest;
    private String principal;
    private String remainingOutstandingPrincipal;

    public RepaymentApi(final String borrowerPaymentAmount,
                        final String date,
                        final String initialOutstandingPrincipal,
                        final String interest,
                        final String principal,
                        final String remainingOutstandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interest = interest;
        this.principal = principal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public static RepaymentApiBuilder builder() {
        return new RepaymentApiBuilder();
    }

    public String getBorrowerPaymentAmount() {
        return borrowerPaymentAmount;
    }

    public String getDate() {
        return date;
    }

    public String getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal;
    }

    public String getInterest() {
        return interest;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    /**
     * Responsible to fill fields of the entity in more readable manner.
     */
    public static class RepaymentApiBuilder {

        String borrowerPaymentAmount;
        String date;
        String initialOutstandingPrincipal;
        String interest;
        String principal;
        String remainingOutstandingPrincipal;

        RepaymentApiBuilder() {
        }

        public RepaymentApiBuilder borrowerPaymentAmount(String borrowerPaymentAmount) {
            this.borrowerPaymentAmount = borrowerPaymentAmount;
            return this;
        }

        public RepaymentApiBuilder date(String date) {
            this.date = date;
            return this;
        }

        public RepaymentApiBuilder initialOutstandingPrincipal(String initialOutstandingPrincipal) {
            this.initialOutstandingPrincipal = initialOutstandingPrincipal;
            return this;
        }

        public RepaymentApiBuilder interest(String interest) {
            this.interest = interest;
            return this;
        }

        public RepaymentApiBuilder principal(String principal) {
            this.principal = principal;
            return this;
        }

        public RepaymentApiBuilder remainingOutstandingPrincipal(String remainingOutstandingPrincipal) {
            this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
            return this;
        }

        public RepaymentApi build() {
            return new RepaymentApi(this.borrowerPaymentAmount,
                                    this.date,
                                    this.initialOutstandingPrincipal,
                                    this.interest,
                                    this.principal,
                                    this.remainingOutstandingPrincipal);
        }
    }
}
