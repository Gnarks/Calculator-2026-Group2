package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Sinus;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestSinus {

	private final int value1 = 0;
	private Sinus op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Sinus(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Sinus e = new Sinus(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal sin = BigDecimalMath.sin(bd, Real.context);
		assertEquals(new Real(sin), op.op(param));
	}

	@Test
	void testToString() {
		assertEquals("sin(0)", op.toString());
	}

}
