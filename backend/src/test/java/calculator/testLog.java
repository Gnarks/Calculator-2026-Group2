package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Log;

import java.math.BigDecimal;
import java.math.MathContext;

class TestLog {

	private final Real base = new Real(2);
	private final Real n = new Real(2);
	private Log op;

	@BeforeEach
	void setUp() {
		try {
			op = new Log(base, n);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEquals() {
		try {
			Log e = new Log(base, n);
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testCompute() {
		// tests equality without precision
		assertEquals(new BigDecimal(1).compareTo(op.op(base, n).getValue()), 0);
	}
}
