package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import calculator.atoms.Real;
import calculator.operations.*;
import visitor.Evaluator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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

	@Test
	void testNan() {
		List<Expression> params = Arrays.asList(Real.nan(), new Real(value));
		try {
			Plus p = new Plus(params);
			Minus m = new Minus(params);
			Times t = new Times(params);
			Divides d = new Divides(params);

			Evaluator evaluator = new Evaluator();
			evaluator.visit(p);
			assertEquals(evaluator.getResult(), Real.nan());

			evaluator = new Evaluator();
			evaluator.visit(t);
			assertEquals(evaluator.getResult(), Real.nan());

			evaluator = new Evaluator();
			evaluator.visit(m);
			assertEquals(evaluator.getResult(), Real.nan());

			evaluator = new Evaluator();
			evaluator.visit(d);
			assertEquals(evaluator.getResult(), Real.nan());

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void dividesCornerCases() {
		List<Expression> params = Arrays.asList(Real.minusInf(), new Real(value));

		try {
			Divides d = new Divides(params);
			Divides reversedD = new Divides(params.reversed());

			Evaluator evaluator = new Evaluator();
			evaluator.visit(d);

			assertEquals(evaluator.getResult(), Real.minusInf());

			evaluator = new Evaluator();
			evaluator.visit(reversedD);

			assertEquals(evaluator.getResult(), new Real(0));

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void timesCornerCases() {
		List<Expression> params = Arrays.asList(Real.minusInf(), new Real(0));
		List<Expression> otherParams = Arrays.asList(new Real(-5), Real.plusInf());

		try {
			Times t = new Times(params);

			Evaluator evaluator = new Evaluator();
			evaluator.visit(t);
			assertEquals(evaluator.getResult(), Real.nan());

			t = new Times(otherParams);

			evaluator = new Evaluator();
			evaluator.visit(t);
			assertEquals(evaluator.getResult(), Real.minusInf());

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void addCornerCases() {
		List<Expression> params = Arrays.asList(Real.plusInf(), Real.minusInf());

		try {
			Plus t = new Plus(params);

			Evaluator evaluator = new Evaluator();
			evaluator.visit(t);

			assertEquals(evaluator.getResult(), Real.nan());

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testScale() {
		// equals to 0.001
		Real su = new Real(0.001);
		Real.scale = 2;
		// equals to 0.00 due to scale change
		Real s1 = new Real(0.001);

		// reset scale to default
		Real.scale = 64;
		assertNotEquals(su, s1);
	}

	@Test
	void testRounding() {
		// equals to 0.010000000 (with 63 0's)
		Real s1 = new Real("0.1");
		Real.scale = 1;
		// equals to 0.00 due to scale change
		Real s99 = new Real(0.099);

		// shouldn't be equal du to different scales
		assertNotEquals(s1, s99);

		// with new scale = 0.1
		s1 = new Real("0.1");
		assertEquals(s1, s99);
	}
}
