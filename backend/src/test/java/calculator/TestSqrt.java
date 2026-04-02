package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Sqrt;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestSqrt {

	private final int value1 = 4;
	private Sqrt op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Sqrt(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Sqrt e = new Sqrt(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal sqrt = BigDecimalMath.sqrt(bd, Real.context);
		assertEquals(new Real(sqrt), op.op(param));
	}
}
