package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.operations.Arctangente;
import calculator.operations.Plus;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        assertEquals(new Real(new BigDecimal(Math.atan(value1))), op.op(param));
    }
}