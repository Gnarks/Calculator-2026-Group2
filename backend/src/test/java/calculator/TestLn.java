package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.operations.Ln;
import calculator.operations.Plus;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        assertEquals(new Real(new BigDecimal(Math.log(value1))), op.op(param));
    }
}