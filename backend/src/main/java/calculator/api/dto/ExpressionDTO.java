package calculator.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object containing the expression sent by the client.
 */
@Data
@AllArgsConstructor
public class ExpressionDTO {

    /**
     * The string representation of the arithmetic expression.
     */
    private String expression;

    /**
     * The requested floating-point precision (number of digits after the decimal).
     */
    private int precision;

}
