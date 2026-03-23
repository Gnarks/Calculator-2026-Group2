package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import calculator.atoms.Complex;
import calculator.operations.*;

import java.util.ArrayList;

class TestComplex {

	private final int value = 8;
	private Complex number;

	@BeforeEach
	void setUp() {
		number = new Complex(value, value);
	}

	@Test
	void testEquals() {
		// Two distinct Complex, constructed separately (using a different constructor)
		// but containing the same value should be equal
		assertEquals(new Complex(value, value), number);
		// Two Complex containing a distinct value should not be equal:
		int otherValue = 7;
		assertNotEquals(new Complex(otherValue, otherValue), number);
		assertEquals(number, number); // Identity check (for coverage, as this should always be true)
		assertNotEquals(number, value); // number is of type Complex, while value is of type int, so not equal
		try {
			assertNotEquals(new Times(new ArrayList<>()), number);
		} catch (IllegalConstruction _) {
			fail();
		}

		int otherValue2 = 8;
		assertNotEquals(new Complex(otherValue, otherValue2), number); // Distinct real part
		assertNotEquals(new Complex(otherValue2, otherValue), number); // Distinct imaginary part
	}

	@Test
	void testNaN() {
		Complex nan = Complex.nan();
		assertEquals(Complex.nan(), nan);
	}
}
