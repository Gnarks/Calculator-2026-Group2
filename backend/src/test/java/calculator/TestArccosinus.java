package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Arccosinus;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestArccosinus {

	private final int value1 = 0;
	private Arccosinus op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Arccosinus(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Arccosinus e = new Arccosinus(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal aCos = BigDecimalMath.acos(bd, Real.context);
		assertEquals(new Real(aCos), op.op(param));
	}
}
