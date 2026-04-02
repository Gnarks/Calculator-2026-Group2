package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Arctangente;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestArctangente {

	private final int value1 = 0;
	private Arctangente op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Arctangente(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Arctangente e = new Arctangente(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal aTan = BigDecimalMath.atan(bd, Real.context);
		assertEquals(new Real(aTan), op.op(param));
	}
}
