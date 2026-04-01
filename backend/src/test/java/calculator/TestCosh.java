package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Cosh;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestCosh {

	private final int value1 = 0;
	private Cosh op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Cosh(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Cosh e = new Cosh(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal cosh = BigDecimalMath.cosh(bd, Real.context);
		assertEquals(new Real(cosh), op.op(param));
	}
}
