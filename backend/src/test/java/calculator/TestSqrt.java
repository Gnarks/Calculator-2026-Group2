package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.operations.Sqrt;
import calculator.operations.Plus;

import java.math.BigDecimal;
import java.util.ArrayList;

class TestSqrt {

	private final int value1 = 4;
	private Sqrt op;
	private Real param;

	@BeforeEach
	void setUp() {
		param = new Real(value1);
		try {
			op = new Sqrt(param);
		} catch (IllegalConstruction e) {
			fail();
		}
	}


	@Test
	void testEquals() {
		try {
			Sqrt e = new Sqrt(new Real(value1));
			assertEquals(op, e);
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
    void testCompute() {
        assertEquals(new Real(new BigDecimal(Math.sqrt(value1))), op.op(param));
    }
}