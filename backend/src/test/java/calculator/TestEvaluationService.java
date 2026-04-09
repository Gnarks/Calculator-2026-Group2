package calculator;

import calculator.api.EvaluationService;
import calculator.api.dto.EvaluationResponse;
import calculator.atoms.AngleMode;
import calculator.atoms.AtomType;
import calculator.atoms.Real;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testChangeAngleRepresentation() {
        assertEquals(AngleMode.RAD, Calculator.mode);
        evaluationService.setAngleMode(AngleMode.DEG);
        assertEquals(AngleMode.DEG, Calculator.mode);
    }
  
    @Test
    void testGenerateRandomInteger() {
        EvaluationResponse random = evaluationService.getRandomNumber(AtomType.INTEGER, 100, null);
        assertEquals(1, random.getSuccess());
        assertDoesNotThrow(() -> Integer.parseInt(random.getResult()));
    }

    @Test
    void testGenerateRandomRational() {
        EvaluationResponse random = evaluationService.getRandomNumber(AtomType.RATIONNAL, 100, null);
        assertEquals(1, random.getSuccess());
        assertTrue(random.getResult().contains("/"));
    }

    @Test
    void testGenerateRandomReal() {
        EvaluationResponse random = evaluationService.getRandomNumber(AtomType.REAL, 1, null);
        assertEquals(1, random.getSuccess());
        assertTrue(random.getResult().contains("."));
    }

    @Test
    void testGenerateRandomComplex() {
        EvaluationResponse random = evaluationService.getRandomNumber(AtomType.COMPLEX, 1, null);
        assertEquals(1, random.getSuccess());
        assertTrue(random.getResult().contains("i"));
    }

    @Test
    void testSameSeedProduceSameResult() {
        EvaluationResponse randomOne = evaluationService.getRandomNumber(AtomType.INTEGER, 1000, 222333L);
        EvaluationResponse randomTwo = evaluationService.getRandomNumber(AtomType.INTEGER, 1000, 222333L);
        assertEquals(1, randomOne.getSuccess(), randomTwo.getSuccess());
        assertEquals(randomOne.getResult(), randomTwo.getResult());
    }

    @Test
    void testDiffSeedProduceDiffResult() {
        EvaluationResponse randomOne = evaluationService.getRandomNumber(AtomType.INTEGER, 1000, 222333L);
        EvaluationResponse randomTwo = evaluationService.getRandomNumber(AtomType.INTEGER, 1000, 333222L);
        assertEquals(1, randomOne.getSuccess(), randomTwo.getSuccess());
        assertNotEquals(randomOne.getResult(), randomTwo.getResult());
    }

    @Test
    void testNegativeMax_invalidRequest() {
        EvaluationResponse random = evaluationService.getRandomNumber(AtomType.INTEGER, -1, null);
        assertEquals(0, random.getSuccess());
        assertEquals("", random.getResult());
    }

    @Test
    void testRandomLessThanMax() {
        int max = 2;
        EvaluationResponse random = evaluationService.getRandomNumber(AtomType.INTEGER, max, null);
        int result = Integer.parseInt(random.getResult());
        assertTrue(result <= max && result >= 0);
    }

}
