package com.kliuchkevych.plangenerator.mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kliuchkevych.plangenerator.model.domain.Loan;
import com.kliuchkevych.plangenerator.model.api.LoanApi;
import org.springframework.stereotype.Component;

/**
 * Loan mapper class.
 * Responsible to convert input loan representation to internal one.
 * LoanApi store all fields in String format.
 * It simplifies deserialization issues and allow handle them on validation step.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
@Component
public class LoanMapper {

    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE_TIME;

    public Loan toDomain(final LoanApi loanApi) {
        final BigDecimal loanAmount = new BigDecimal(loanApi.getLoanAmount());
        final BigDecimal rate = new BigDecimal(loanApi.getNominalRate());
        final int duration = Integer.parseInt(loanApi.getDuration());
        final LocalDateTime date = LocalDateTime.parse(loanApi.getStartDate(), dateFormat);

        return new Loan(loanAmount, rate, duration, date);
    }
}
