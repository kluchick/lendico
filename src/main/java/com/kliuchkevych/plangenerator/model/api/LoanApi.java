package com.kliuchkevych.plangenerator.model.api;

import java.util.Objects;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * API representation of the Loan entity.
 * <p>
 * All fields defined as String to simplifier deserialization.
 * It allows us to process and validate fields content using our validation mechanism and not fail base on JSON
 * parsing issues.
 * <p>
 * Base validation is implemented using javax.validation.constraints. Appripriate dependncy is added to the pom file
 * to add implementation of this interface.
 * <p>
 * Comment: getters, setters and other stuff nice to replace with Lombok which can generate all this things
 * automatically.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
public class LoanApi {

    @NotBlank(message = "Loan amount should be filled")
    @Digits(message = "Please check loan amount field", integer = 9, fraction = 2)
    private String loanAmount;

    @NotBlank(message = "Loan rate should be filled")
    @Digits(message = "Please check rate field", integer = 2, fraction = 2)
    private String nominalRate;

    @NotBlank(message = "Loan duration should be filled")
    @Digits(message = "Please check duration field", integer = 2, fraction = 0)
    private String duration;

    @NotBlank(message = "Start date should be filled")
    private String startDate;

    /**
     * Default constructor and setters are used for deserialization.
     */
    public LoanApi() {
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(final String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getNominalRate() {
        return nominalRate;
    }

    public void setNominalRate(final String nominalRate) {
        this.nominalRate = nominalRate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanApi)) {
            return false;
        }
        final LoanApi loanApi = (LoanApi) o;
        return Objects.equals(loanAmount, loanApi.loanAmount) &&
                Objects.equals(nominalRate, loanApi.nominalRate) &&
                Objects.equals(duration, loanApi.duration) &&
                Objects.equals(startDate, loanApi.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanAmount, nominalRate, duration, startDate);
    }
}
