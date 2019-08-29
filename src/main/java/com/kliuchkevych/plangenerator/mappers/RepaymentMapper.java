package com.kliuchkevych.plangenerator.mappers;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.kliuchkevych.plangenerator.model.domain.Repayment;
import com.kliuchkevych.plangenerator.model.api.RepaymentApi;
import org.springframework.stereotype.Component;

/**
 * Repayment mapper class.
 * Responsible to convert internal representation of the plan to external one.
 * Number and date formatting rules are defined here.
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
@Component
public class RepaymentMapper {

    private DecimalFormat decimalFormat = new DecimalFormat();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE_TIME;

    public RepaymentMapper() {
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setGroupingUsed(false);
    }

    /**
     * Convert single entity for repayment to the API representation.
     *
     * @param repayment to convert
     * @return converted entity
     */
    public RepaymentApi toApi(final Repayment repayment) {
        return RepaymentApi.builder()
                           .borrowerPaymentAmount(decimalFormat.format(repayment.getBorrowerPaymentAmount()))
                           .date(dateFormat.format(repayment.getDate()))
                           .initialOutstandingPrincipal(
                                   decimalFormat.format(repayment.getInitialOutstandingPrincipal()))
                           .interest(decimalFormat.format(repayment.getInterest()))
                           .principal(decimalFormat.format(repayment.getPrincipal()))
                           .remainingOutstandingPrincipal(
                                   decimalFormat.format(repayment.getRemainingOutstandingPrincipal()))
                           .build();
    }

    /**
     * Converts a list of the repayment entities to API representation.
     *
     * @param repayments list of the {@link Repayment} entities
     * @return list of converted {@link RepaymentApi} entities
     */
    public List<RepaymentApi> toApi(final List<Repayment> repayments) {
        return repayments.stream().map(this::toApi).collect(Collectors.toList());
    }
}
