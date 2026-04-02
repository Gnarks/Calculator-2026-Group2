package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import calculator.atoms.Real;
import calculator.operations.*;
import visitor.Counter;

import java.util.Arrays;
import java.util.List;

class TestOperation {

	private Operation o;
	private Operation o2;

	@BeforeEach
	void setUp() throws Exception {
		List<Expression> params1 = Arrays.asList(new Real(3), new Real(4), new Real(5));
		List<Expression> params2 = Arrays.asList(new Real(5), new Real(4));
		List<Expression> params3 = Arrays.asList(new Plus(params1), new Minus(params2), new Real(7));
		o = new Divides(params3);
		o2 = new Divides(params3);
	}

	@Test
	void testEquals() {
		assertEquals(o, o2);
	}

	@Test
	void testCountDepth() {
		Counter c = new Counter();
		o.accept(c);
		assertEquals(2, c.getDepth());
	}

	@Test
	void testCountOps() {
		Counter c = new Counter();
		o.accept(c);
		assertEquals(3, c.getNbOps());
	}

	@Test
	void testCountNbs() {
		Counter c = new Counter();
		o.accept(c);
		assertEquals(Integer.valueOf(6), c.getNbNbs());
	}

}
