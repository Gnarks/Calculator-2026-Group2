package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Sinh;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestSinh {

	private final int value1 = 0;
	private Sinh op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Sinh(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Sinh e = new Sinh(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal sinh = BigDecimalMath.sinh(bd, Real.context);
		assertEquals(new Real(sinh), op.op(param));
	}
}
