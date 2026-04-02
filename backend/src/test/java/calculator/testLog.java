package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Log;

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
		assertEquals(op.op(base, n), new Real(1));
	}
}
