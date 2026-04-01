package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Tanh;

import java.math.BigDecimal;

class TestTanh {

	private final int value1 = 0;
	private Tanh op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Tanh(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Tanh e = new Tanh(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		assertEquals(new Real(new BigDecimal(Math.tanh(value1))), op.op(param));
	}
}
