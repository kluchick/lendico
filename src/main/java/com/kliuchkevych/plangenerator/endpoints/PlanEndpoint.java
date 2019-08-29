package com.kliuchkevych.plangenerator.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.kliuchkevych.plangenerator.exceptions.RepaymentCalculationException;
import com.kliuchkevych.plangenerator.mappers.LoanMapper;
import com.kliuchkevych.plangenerator.mappers.RepaymentMapper;
import com.kliuchkevych.plangenerator.model.api.LoanApi;
import com.kliuchkevych.plangenerator.model.api.RepaymentApi;
import com.kliuchkevych.plangenerator.model.domain.Loan;
import com.kliuchkevych.plangenerator.service.RepaymentPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint to generate loan repayment plan.
 * <p>
 * allowed URLs:
 * localhost:8080/repayment-plan
 *
 * @author ievgen.kliuchkevych (ikliuchk)
 * @since 0.1
 */
@RestController
public class PlanEndpoint {

    /*Comment:
     *   @Autowired or @Inject annotation could be skipped starting from Spring 4.3
     */

    private final RepaymentPlanService planService;

    private final RepaymentMapper repaymentMapper;

    private final LoanMapper loanMapper;

    /*Comment:
     *   Constructor injection is used. Can be hide using Lombok.
     */
    public PlanEndpoint(final RepaymentPlanService planService,
                        final RepaymentMapper repaymentMapper,
                        final LoanMapper loanMapper) {
        this.planService = planService;
        this.repaymentMapper = repaymentMapper;
        this.loanMapper = loanMapper;
    }

    /**
     * Endpoint to generate repayment plan.
     * <p>
     * Comment: base validation is applied for input fields.
     * input and output values are represented as API entities for convenience, security and validation.
     *
     * @param loanApi with input parameters
     * @return generated plan.
     */
    @PostMapping("/repayment-plan")
    @ResponseBody
    public List<RepaymentApi> generatePlan(@Valid @RequestBody LoanApi loanApi) {
        final Loan loan = loanMapper.toDomain(loanApi);
        return repaymentMapper.toApi(planService.generatePlan(loan));
    }

    /**
     * Handler for validation exceptions.
     * Allows format and process validation exceptions caused by @Valid mechanism
     *
     * @param ex exception to handle
     * @return formatted errors.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handler to catch our own {@link RepaymentCalculationException}.
     * We want to notify user about exception in controlled way rather that throw Server error.
     *
     * @param ex to notify about
     * @return formatted errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RepaymentCalculationException.class)
    public Map<String, String> handleValidationExceptions(RepaymentCalculationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("ArithmeticException is happened", ex.getMessage());
        errors.put("Please check input parameters", ex.getLoan().toString());
        return errors;
    }
}
