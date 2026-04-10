package calculator.api.exception;

import calculator.api.dto.EvaluationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for the API controllers.
 * Intercepts common exceptions to provide structured EvaluationResponse formats.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Intercepts type mismatches on query/path parameters, for instance when parsing enum values (like AtomType).
     *
     * @return a structured failure response indicating invalid type formatting.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public EvaluationResponse handleEnumError() {
        return new EvaluationResponse(0, "Invalid atom type");
    }

}
