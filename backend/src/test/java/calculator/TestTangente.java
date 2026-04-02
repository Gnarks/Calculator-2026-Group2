package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Tangente;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestTangente {

	private final int value1 = 0;
	private Tangente op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Tangente(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Tangente e = new Tangente(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal tan = BigDecimalMath.tan(bd, Real.context);
		assertEquals(new Real(tan), op.op(param));
	}
}
