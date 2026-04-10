package calculator.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing the outcome of an expression evaluation
 * or a random number generation. Sent back to the client.
 */
@Data
@AllArgsConstructor
public class EvaluationResponse {

    /**
     * Set to 1 if the operation was successful, 0 if an error occurred.
     */
    private int success;

    /**
     * The evaluated value returned as a string. Empty string if success = 0.
     */
    private String result;

}
