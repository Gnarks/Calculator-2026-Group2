package calculator;

import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.Real;
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
    void testToRadian() {
        Rationnal r = new Rationnal(Fraction.of(34, 9));
        Real.scale = 8;
        Real converted = (Real) r.toRadian();
        assertEquals(new Real(0.06593466), converted);
    }
}