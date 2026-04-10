package calculator;

import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import calculator.operations.Power;
import ch.obermuhlner.math.big.BigDecimalMath;

import org.apache.commons.numbers.fraction.Fraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPower {

	private final int base = 5;
	private final int exp = 2;
	private Power op;
	private List<Expression> params;

	@BeforeEach
	void setUp() {
		params = Arrays.asList(new Real(base), new Real(exp));
		try {
			op = new Power(params);
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testEquals() {
		List<Expression> p = Arrays.asList(new Real(base), new Real(exp));
		try {
			Power pow = new Power(p);
			assertEquals(op, pow);
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Test
	void testNull() {
		assertDoesNotThrow(() -> op == null);
	}

	@Test
	void testHashCode() {
		List<Expression> p = Arrays.asList(new Real(base), new Real(exp));
		try {
			Power pow = new Power(p);
			assertEquals(pow.hashCode(), op.hashCode());
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNullParamList() {
		assertThrows(IllegalConstruction.class, () -> op = new Power(null));
	}

	// INTEGERS
	@Test
	void testPosIntExpPosInt() {
		IntegerAtom i1 = new IntegerAtom(5);
		IntegerAtom i2 = new IntegerAtom(2);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(25), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testIntExpZero() {
		IntegerAtom i1 = new IntegerAtom(5);
		IntegerAtom i2 = new IntegerAtom(0);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(1), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegIntExpEvenPosInt() {
		IntegerAtom i1 = new IntegerAtom(-5);
		IntegerAtom i2 = new IntegerAtom(2);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(25), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegIntExpOddPosInt() {
		IntegerAtom i1 = new IntegerAtom(-2);
		IntegerAtom i2 = new IntegerAtom(3);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(-8), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testPosIntExpNegInt() {
		IntegerAtom i1 = new IntegerAtom(2);
		IntegerAtom i2 = new IntegerAtom(-2);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(0.25), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegIntExpEvenNegInt() {
		IntegerAtom i1 = new IntegerAtom(-2);
		IntegerAtom i2 = new IntegerAtom(-2);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(0.25), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegIntExpOddNegInt() {
		IntegerAtom i1 = new IntegerAtom(-2);
		IntegerAtom i2 = new IntegerAtom(-3);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(-0.125), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testZeroExpNegInt_isNaN() {
		IntegerAtom i1 = new IntegerAtom(0);
		IntegerAtom i2 = new IntegerAtom(-3);
		List<Expression> p = Arrays.asList(i1, i2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(i1, i2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	// RATIONALS
	@Test
	void testRatExpRat() {
		Rationnal r = new Rationnal(Fraction.of(1, 2));
		List<Expression> p = Arrays.asList(r, r);
		try {
			Power pow = new Power(p);
			BigDecimal bd = BigDecimal.valueOf(0.5);
			BigDecimal expected = BigDecimalMath.pow(bd, bd, Real.context);
			assertEquals(new Real(expected), pow.op(r, r));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testRatZeroExpRatZero_isNaN() {
		Real r = new Real(0);
		List<Expression> p = Arrays.asList(r, r);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r, r));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegRatExpRatWithOddDenom() {
		Rationnal r1 = new Rationnal(Fraction.of(-3, 2));
		Rationnal r2 = new Rationnal(Fraction.of(1, 3));
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegRatExpRatEqOne() {
		Rationnal r1 = new Rationnal(Fraction.of(-2, 2));
		Rationnal r2 = new Rationnal(Fraction.of(2, 2));
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);

			BigDecimal d1 = new BigDecimal("-1");
			BigDecimal d2 = new BigDecimal("1");
			BigDecimal expected = BigDecimalMath.pow(d1, d2, Real.context);
			assertEquals(new Real(expected), pow.op(r1, r2));

			assertEquals(new Real(-1), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testZeroExpRat() {
		Rationnal r1 = new Rationnal(Fraction.of(0, 1));
		Rationnal r2 = new Rationnal(Fraction.of(1, 3));
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);

			assertEquals(new Real(0), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testZeroExpNegRat_isNaN() {
		Rationnal r1 = new Rationnal(Fraction.of(0, 1));
		Rationnal r2 = new Rationnal(Fraction.of(-1, 3));
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	// REALS
	@Test
	void testRealExpReal() {
		Real r1 = new Real(3.254);
		Real r2 = new Real(1.548);
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			BigDecimal expected = BigDecimalMath.pow(r1.getValue(), r2.getValue(), Real.context);
			assertEquals(new Real(expected), pow.op(r1, r2));

		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegRealExpReal_isNaN() {
		Real r1 = new Real(-3.254);
		Real r2 = new Real(1.548);
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegRealExpRatWithEvenDenom_isNaN() {
		Real r1 = new Real(-3.254);
		Real r2 = new Real(2.1);
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNegRealExpInteger() {
		Real r1 = new Real(-2.3652);
		Real r2 = new Real(2);
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);

			BigDecimal expected = BigDecimalMath.pow(r1.getValue(), r2.getValue(), Real.context);
			assertEquals(new Real(expected), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testRealExpPlusInf() {
		Real r1 = new Real(3.254);
		Real r2 = Real.plusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.plusInf(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testOneExpPlusInf() {
		Real r1 = new Real(1);
		Real r2 = Real.plusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);

			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testZeroExpPlusInf() {
		Real r1 = new Real(0);
		Real r2 = Real.plusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testMinusInfExpPlusInf_isNaN() {
		Real r1 = Real.minusInf();
		Real r2 = Real.plusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testRealBetweenZeroAndOneExpPlusInf() {
		Real r1 = new Real(0.256);
		Real r2 = Real.plusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(0), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testZeroExpNegReal() {
		Real r1 = new Real(0);
		Real r2 = new Real(-2.364);
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testZeroExpZero_isNaN() {
		Real r = new Real(0);
		List<Expression> p = Arrays.asList(r, r);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r, r));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testMinusInfExpMinInf_isNaN() {
		Real r1 = Real.minusInf();
		Real r2 = Real.minusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testRealExpMinusInf() {
		Real r1 = new Real(3.254);
		Real r2 = Real.minusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(new Real(0), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testRealBetweenZeroAndOneExpMinusInf() {
		Real r1 = new Real(0.2564);
		Real r2 = Real.minusInf();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.plusInf(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testNaNExpNaN() {
		Real r1 = Real.nan();
		Real r2 = Real.nan();
		List<Expression> p = Arrays.asList(r1, r2);
		try {
			Power pow = new Power(p);
			assertEquals(Real.nan(), pow.op(r1, r2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	// COMPLEX
	@Test
	void testComplexExpComplex_throwsException() {
		Complex c1 = new Complex(3, 2);
		Complex c2 = new Complex(2, 1);
		List<Expression> p = Arrays.asList(c1, c2);
		try {
			Power pow = new Power(p);
			assertThrows(ArithmeticException.class, () -> pow.op(c1, c2));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

}
