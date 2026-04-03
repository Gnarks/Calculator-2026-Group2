package calculator;

import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import org.apache.commons.numbers.fraction.Fraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.functions.Log;

import java.math.BigDecimal;

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


	@Test
	void testLogWithIntegers() {
		IntegerAtom base = new IntegerAtom(10);
		IntegerAtom x = new IntegerAtom(100);
		try {
			Log log = new Log(base, x);
			assertEquals(new Real(2), log.op(base, x));
		} catch(IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testLogWithRational() {
		Rationnal base = new Rationnal(Fraction.of(3, 2));
		Rationnal x = new Rationnal(Fraction.of(9));
		try {
			Log log = new Log(base, x);
			assertEquals(new Real(5.4190225827029).getValue().doubleValue(), log.op(base, x).getValue().doubleValue(), 1e-9);
		} catch(IllegalConstruction _) {
			fail();
		}
	}
}
