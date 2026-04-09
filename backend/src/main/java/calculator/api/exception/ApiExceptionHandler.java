package calculator.api.exception;

import calculator.api.dto.EvaluationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public EvaluationResponse handleEnumError() {
        return new EvaluationResponse(0, "Invalid atom type");
    }

}
