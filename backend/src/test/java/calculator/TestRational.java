package calculator;

import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.Real;
import calculator.functions.Log;
import calculator.functions.Sinus;
import calculator.functions.UnaryFunction;
import org.apache.commons.numbers.fraction.Fraction;
import org.junit.jupiter.api.*;

import calculator.atoms.Rationnal;
import calculator.operations.*;

import java.util.ArrayList;

/**
 * Unit tests for the Rationnal atom class.
 */
class TestRationnal {

    private final int num = 1;
    private final int den = 2;
    private Rationnal fraction;

    @BeforeEach
    void setUp() {
        fraction = new Rationnal(num, den);
    }

    @Test
    void testEquals() {
        // Two distinct Rationnal, constructed separately but containing the same value should be equal
        assertEquals(new Rationnal(num, den), fraction);

        // Two Rationnal that can be simplified to the same value should be equal (2/4 == 1/2)
        assertEquals(new Rationnal(2, 4), fraction);

        // Two Rationnal containing distinct values should not be equal
        assertNotEquals(new Rationnal(3, 4), fraction);
        assertNotEquals(new Rationnal(1, 3), fraction);

        assertEquals(fraction, fraction); // Identity check (for coverage)

        assertNotEquals(null, fraction); // No object should be equal to null

        assertNotEquals(fraction, num); // fraction is of type Rationnal, while num is of type int, so not equal

        try {
            assertNotEquals(new Plus(new ArrayList<>()), fraction);
        } catch (IllegalConstruction _) {
            fail();
        }

        // Signs should be normalized: -1/2 must be equal to 1/-2
        assertEquals(new Rationnal(-1, 2), new Rationnal(1, -2));
        // Double negative should be positive
        assertEquals(new Rationnal(1, 2), new Rationnal(-1, -2));
    }

    @Test
    void testNewRationalFromInt() {
        Rationnal q = new Rationnal(6);
        assertEquals(Fraction.of(6, 1), q.getValue());
    }

    @Test
    void testNewRationalFromIntegers_denomZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Rationnal(6, 0));
    }

    @Test
    void testRationalOpLog() {
        Real.scale = 8;
        Rationnal base = new Rationnal(10);
        try {
            Log log = new Log(base, fraction);
            assertEquals(new Real(-0.30103), base.apply(log, fraction));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testRationalOpUnaryFunction() {
        Real.scale = 8;
        try {
            Sinus sinus = new Sinus(fraction);
            assertEquals(Fraction.from(0.47942554), fraction.apply(sinus).getValue());
        } catch (IllegalConstruction _) {
            fail();
        }
    }

}