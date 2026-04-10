package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.Atom;
import org.junit.jupiter.api.*;

import calculator.atoms.AtomType;
import calculator.atoms.Complex;
import calculator.atoms.Real;
import calculator.atoms.visitor.AtomCaster;
import calculator.operations.*;
import visitor.Evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@Test
	void testCastFromNaNReal() {
		Real nan = Real.nan();
		AtomCaster aC = new AtomCaster(AtomType.COMPLEX);

		aC.visit(nan);

		assertEquals(aC.getResult(), Complex.nan());
	}

	@Test
	void testNaNOperations() {
		Complex nan = Complex.nan();
		Complex other = new Complex(2, 5);
		List<Expression> params = Arrays.asList(nan, other);

		try {
			Times t = new Times(params);
			Divides d = new Divides(params);
			Plus p = new Plus(params);
			Minus m = new Minus(params);

			Evaluator evaluator = new Evaluator();
			evaluator.visit(p);
			assertEquals(evaluator.getResult(), Complex.nan());

			evaluator = new Evaluator();
			evaluator.visit(t);
			assertEquals(evaluator.getResult(), Complex.nan());

			evaluator = new Evaluator();
			evaluator.visit(m);
			assertEquals(evaluator.getResult(), Complex.nan());

			evaluator = new Evaluator();
			evaluator.visit(d);
			assertEquals(evaluator.getResult(), Complex.nan());

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testToRadian() {
		Complex c = new Complex(3, 2);
		Complex converted = (Complex) c.toRadian();
		assertEquals(c, converted);
	}

	@Test
	void testToStringComplex() {
		assertEquals("0", new Complex(0, 0).toString());
		assertEquals("2.0i", new Complex(0, 2).toString());
		assertEquals("-2.0i", new Complex(0, -2).toString());
		assertEquals("2.0 + 1.0i", new Complex(2, 1).toString());
		assertEquals("4.0 - 3.0i", new Complex(4, -3).toString());
		assertEquals("4.0", new Complex(4, 0).toString());
	}
}
