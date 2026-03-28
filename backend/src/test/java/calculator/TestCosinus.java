package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.atoms.Real;
import calculator.operations.Cosinus;
import calculator.operations.Plus;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;

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
        assertEquals(new Real(new BigDecimal(Math.cos(value1))), op.op(param));
    }

}