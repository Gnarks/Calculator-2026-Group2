package calculator;

import calculator.api.EvaluationService;
import calculator.api.dto.EvaluationResponse;
import calculator.atoms.Real;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEvaluationService {

    private final EvaluationService evaluationService = new EvaluationService();

    private final int precision = 64;

    @BeforeEach
    void setUp() {
        Real.scale = precision;
    }

    @Test
    void testNullArgs() {
        EvaluationResponse response = evaluationService.evaluate(null, precision);
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testEmptyArgs() {
        EvaluationResponse response = evaluationService.evaluate("", precision);
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testInvalidOperation() {
        String request = "9/0";
        EvaluationResponse response = evaluationService.evaluate(request, precision);
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testValidOperation() {
        String request = "(6 + 9 * 3)";
        EvaluationResponse response = evaluationService.evaluate(request, precision);
        assertEquals(1, response.getSuccess());
        assertEquals("33", response.getResult());
    }

    @Test
    void testNegativePrecision() {
        String request = "(6 + 9 * 3)";
        EvaluationResponse response = evaluationService.evaluate(request, -1);
        assertEquals(0, response.getSuccess());
        assertEquals("", response.getResult());
    }

    @Test
    void testPrecisionChanged() {
        assertEquals(64, Real.scale);
        String request = "(6 + 9 * 3)";
        evaluationService.evaluate(request, 15);
        assertEquals(15, Real.scale);
    }

}
