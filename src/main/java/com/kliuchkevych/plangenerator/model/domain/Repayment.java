package com.kliuchkevych.plangenerator.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain representation of the repayment plan entry.
 * BigDecimal type is chosen to more control of number mutations.
 * <p>
 * Internal subclass of {@link Repayment.RepaymentBuilder} simplify filling of the fields.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
public class Repayment {

    private BigDecimal borrowerPaymentAmount;
    private LocalDateTime date;
    private BigDecimal initialOutstandingPrincipal;
    private BigDecimal interest;
    private BigDecimal principal;
    private BigDecimal remainingOutstandingPrincipal;

    public Repayment(final BigDecimal borrowerPaymentAmount,
                     final LocalDateTime date,
                     final BigDecimal initialOutstandingPrincipal,
                     final BigDecimal interest,
                     final BigDecimal principal,
                     final BigDecimal remainingOutstandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interest = interest;
        this.principal = principal;
        this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    public static Repayment.RepaymentBuilder builder() {
        return new Repayment.RepaymentBuilder();
    }

    public BigDecimal getBorrowerPaymentAmount() {
        return borrowerPaymentAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getInitialOutstandingPrincipal() {
        return initialOutstandingPrincipal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Repayment)) {
            return false;
        }
        final Repayment repayment = (Repayment) o;
        return Objects.equals(borrowerPaymentAmount, repayment.borrowerPaymentAmount) &&
                Objects.equals(date, repayment.date) &&
                Objects.equals(initialOutstandingPrincipal, repayment.initialOutstandingPrincipal) &&
                Objects.equals(interest, repayment.interest) &&
                Objects.equals(principal, repayment.principal) &&
                Objects.equals(remainingOutstandingPrincipal, repayment.remainingOutstandingPrincipal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowerPaymentAmount, date, initialOutstandingPrincipal, interest, principal,
                            remainingOutstandingPrincipal);
    }

    @Override
    public String toString() {
        return "Repayment{" +
                "borrowerPaymentAmount=" + borrowerPaymentAmount +
                ", date=" + date +
                ", initialOutstandingPrincipal=" + initialOutstandingPrincipal +
                ", interest=" + interest +
                ", principal=" + principal +
                ", remainingOutstandingPrincipal=" + remainingOutstandingPrincipal +
                '}';
    }

    /**
     * Allows construct the entity in more readable way.
     */
    public static class RepaymentBuilder {

        BigDecimal borrowerPaymentAmount;
        LocalDateTime date;
        BigDecimal initialOutstandingPrincipal;
        BigDecimal interest;
        BigDecimal principal;
        BigDecimal remainingOutstandingPrincipal;

        RepaymentBuilder() {
        }

        public Repayment.RepaymentBuilder borrowerPaymentAmount(BigDecimal borrowerPaymentAmount) {
            this.borrowerPaymentAmount = borrowerPaymentAmount;
            return this;
        }

        public Repayment.RepaymentBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Repayment.RepaymentBuilder initialOutstandingPrincipal(BigDecimal initialOutstandingPrincipal) {
            this.initialOutstandingPrincipal = initialOutstandingPrincipal;
            return this;
        }

        public Repayment.RepaymentBuilder interest(BigDecimal interest) {
            this.interest = interest;
            return this;
        }

        public Repayment.RepaymentBuilder principal(BigDecimal principal) {
            this.principal = principal;
            return this;
        }

        public Repayment.RepaymentBuilder remainingOutstandingPrincipal(BigDecimal remainingOutstandingPrincipal) {
            this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
            return this;
        }

        public Repayment build() {
            return new Repayment(this.borrowerPaymentAmount,
                                 this.date,
                                 this.initialOutstandingPrincipal,
                                 this.interest,
                                 this.principal,
                                 this.remainingOutstandingPrincipal);
        }
    }
}
