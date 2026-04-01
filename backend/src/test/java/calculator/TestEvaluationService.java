package calculator;

import calculator.api.EvaluationService;
import calculator.api.dto.EvaluationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEvaluationService {

    private EvaluationService evaluationService;

    @BeforeEach
    void setUp() {
        evaluationService = new EvaluationService();
    }

    @Test
    void testNullArgs() {
        EvaluationResponse response = evaluationService.evaluate(null);
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testEmptyArgs() {
        EvaluationResponse response = evaluationService.evaluate("");
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testInvalidOperation() {
        String request = "9/0";
        EvaluationResponse response = evaluationService.evaluate(request);
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testValidOperation() {
        String request = "(6 + 9 * 3)";
        EvaluationResponse response = evaluationService.evaluate(request);
        assertEquals(1, response.getSuccess());
        assertEquals("33", response.getResult());
    }

}
