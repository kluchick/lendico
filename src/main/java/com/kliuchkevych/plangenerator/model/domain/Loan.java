package com.kliuchkevych.plangenerator.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.validation.constraints.Positive;

/**
 * Domain representation of the loan entity.
 * BigDecimal type is chosen to more control of number mutations.
 * <p>
 * Note: getters, setters and other stuff is nice to replace with Lombok which can generate all this things
 * automatically.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
public class Loan {

    // amount of the loan
    private BigDecimal loanAmount;

    // loan interest rate (% per year)
    private BigDecimal nominalRate;

    // loan duration (month)
    @Positive
    private int duration;

    // loan start date
    private LocalDateTime startDate;

    public Loan(final BigDecimal loanAmount,
                final BigDecimal nominalRate,
                final int duration,
                final LocalDateTime startDate) {
        this.loanAmount = loanAmount;
        this.nominalRate = nominalRate;
        this.duration = duration;
        this.startDate = startDate;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(final BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getNominalRate() {
        return nominalRate;
    }

    public void setNominalRate(final BigDecimal nominalRate) {
        this.nominalRate = nominalRate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Loan)) {
            return false;
        }
        final Loan loan = (Loan) o;
        return Objects.equals(loanAmount, loan.loanAmount) &&
                Objects.equals(nominalRate, loan.nominalRate) &&
                Objects.equals(duration, loan.duration) &&
                Objects.equals(startDate, loan.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanAmount, nominalRate, duration, startDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanAmount=" + loanAmount +
                ", nominalRate=" + nominalRate +
                ", duration=" + duration +
                ", startDate=" + startDate +
                '}';
    }
}
