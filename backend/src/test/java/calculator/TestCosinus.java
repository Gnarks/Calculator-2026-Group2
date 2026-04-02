package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Cosinus;
import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;

class TestCosinus {

	private final int value1 = 0;
	private Cosinus op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Cosinus(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testConstructor1() {
		// It should not be possible to create an expression with null parameter
		assertThrows(IllegalConstruction.class, () -> op = new Cosinus(null));
	}

	@Test
	void testEquals() {
		try {
			Cosinus e = new Cosinus(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		BigDecimal bd = new BigDecimal(value1);
		BigDecimal cos = BigDecimalMath.cos(bd, Real.context);
		assertEquals(new Real(cos), op.op(param));
	}

}
