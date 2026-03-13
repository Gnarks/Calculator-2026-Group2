package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import calculator.atoms.Real;

import java.util.ArrayList;

class TestReal {

	private final int value = 8;
	private Real number;

	@BeforeEach
	void setUp() {
		number = new Real(value);
	}

	@Test
	void testEquals() {
		// Two distinct Real, constructed separately (using a different constructor)
		// but containing the same value should be equal
		assertEquals(new Real(value), number);
		// Two Reals containing a distinct value should not be equal:
		int otherValue = 7;
		assertNotEquals(new Real(otherValue), number);
		assertEquals(number, number); // Identity check (for coverage, as this should always be true)
		assertNotEquals(number, value); // number is of type Real, while value is of type int, so not equal
		try {
			assertNotEquals(new Times(new ArrayList<>()), number);
		} catch (IllegalConstruction _) {
			fail();
		}
	}
}
