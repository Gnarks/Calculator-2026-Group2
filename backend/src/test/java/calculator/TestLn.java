package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Ln;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestLn {

	private final int value1 = 2;
	private Ln op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Ln(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Ln e = new Ln(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal ln = BigDecimalMath.log(bd, Real.context);
		assertEquals(new Real(ln), op.op(param));
	}
}
