package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import calculator.operations.*;
import calculator.atoms.Real;
import calculator.atoms.Complex;
import calculator.atoms.Rationnal;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestDivides {

	private final int value1 = 8;
	private final int value2 = 6;
	private Divides op;
	private List<Expression> params;

	@BeforeEach
	void setUp() {
		params = Arrays.asList(new Real(value1), new Real(value2));
		try {
			op = new Divides(params);
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testConstructor1() {
		// It should not be possible to create an expression without null parameter list
		assertThrows(IllegalConstruction.class, () -> op = new Divides(null));
	}

	@SuppressWarnings("AssertBetweenInconvertibleTypes")
	@Test
	void testConstructor2() {
		// A Times expression should not be the same as a Divides expression
		try {
			assertNotSame(op, new Times(new ArrayList<>()));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testEquals() {
		// Two similar expressions, constructed separately (and using different
		// constructors) should be equal
		List<Expression> p = Arrays.asList(new Real(value1), new Real(value2));
		try {
			Divides d = new Divides(p);
			assertEquals(op, d);
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Test
	void testNull() {
		assertDoesNotThrow(() -> op == null); // Direct way to to test if the null case is handled.
	}

	@Test
	void testHashCode() {
		// Two similar expressions, constructed separately (and using different
		// constructors) should have the same hashcode
		List<Expression> p = Arrays.asList(new Real(value1), new Real(value2));
		try {
			Divides e = new Divides(p);
			assertEquals(e.hashCode(), op.hashCode());
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNullParamList() {
		params = null;
		assertThrows(IllegalConstruction.class, () -> op = new Divides(params));
	}

	@Test
	void testDivideByZero() {
		List<Expression> paramsWithZero = Arrays.asList(new Real(value1), new Real(0));
		try {
			Divides d = new Divides(paramsWithZero);
			assertThrows(ArithmeticException.class, () -> d.op(value1, 0));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testDivideByZeroComplex() {
		List<Expression> paramsWithZero = Arrays.asList(new Complex(value1, value2), new Complex(0, 0));
		try {
			Divides d = new Divides(paramsWithZero);
			assertThrows(ArithmeticException.class, () -> d.op(new Complex(value1, value2), new Complex(0, 0)));
		} catch (IllegalConstruction _) {
			fail();
		}
	}
	@Test
    void testDivideRationnals() {
        Rationnal q1 = new Rationnal(1, 2); 
        Rationnal q2 = new Rationnal(1, 4);
		// (1/2) / (1/4) must be equal to 2/1 
        List<Expression> p = Arrays.asList(q1, q2);
        try {
            Divides d = new Divides(p);
            Rationnal expected = new Rationnal(2, 1);
            assertEquals(expected, d.op(q1, q2));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testDivideByZeroRationnal() {
        Rationnal q1 = new Rationnal(value1, value2);
        Rationnal zero = new Rationnal(0, 1);
        List<Expression> p = Arrays.asList(q1, zero);
        try {
            Divides d = new Divides(p);
            assertThrows(ArithmeticException.class, () -> d.op(q1, zero));
        } catch (IllegalConstruction _) {
            fail();
        }
    }
}
