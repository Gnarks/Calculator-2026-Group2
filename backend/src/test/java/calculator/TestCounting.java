package calculator;

//Import Junit5 libraries for unit testing:
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import calculator.atoms.Real;
import calculator.atoms.Complex;
import calculator.operations.*;

import static org.junit.jupiter.api.Assertions.*;

import visitor.Counter;

import java.util.Arrays;
import java.util.List;

class TestCounting {

	private int value1, value2;
	private Expression e;

	@BeforeEach
	void setUp() {
		value1 = 8;
		value2 = 6;
	}

	@Test
	void testNumberCounting() {
		e = new Real(value1);
		Counter c = new Counter();
		e.accept(c);
		// test whether a number has zero depth (i.e. no nested expressions)
		assertEquals(0, c.getDepth());
		// test whether a number contains zero operations
		assertEquals(0, c.getNbOps());
		// test whether a number contains 1 number
		assertEquals(1, c.getNbNbs());
	}

	@Test
	void testComplexNumberCounting() {
		e = new Complex(value1, value2);
		Counter c = new Counter();
		e.accept(c);
		// test whether a complex number has zero depth
		assertEquals(0, c.getDepth());
		// test whether a complex number contains zero operations
		assertEquals(0, c.getNbOps());
		// test whether a complex number counts as 1 number
		assertEquals(1, c.getNbNbs());
	}

	@ParameterizedTest
	@ValueSource(strings = { "*", "+", "/", "-" })
	void testOperationCounting(String symbol) {
		List<Expression> params = Arrays.asList(new Real(value1), new Real(value2));
		try {
			// construct another type of operation depending on the input value
			// of the parameterised test
			switch (symbol) {
				case "+" -> e = new Plus(params);
				case "-" -> e = new Minus(params);
				case "*" -> e = new Times(params);
				case "/" -> e = new Divides(params);
				default -> fail();
			}
		} catch (IllegalConstruction _) {
			fail();
		}
		Counter c = new Counter();
		e.accept(c);
		// test whether a binary operation has depth 1
		assertEquals(1, c.getDepth(), "counting depth of an Operation");
		// test whether a binary operation contains 1 operation
		assertEquals(1, c.getNbOps());
		// test whether a binary operation contains 2 numbers
		assertEquals(2, c.getNbNbs());
	}

	@Test
	void testComplexDeepExpressionCounting() {
		try {
			Expression c1 = new Complex(value1, value2);
			Expression c2 = new Complex(value2, value1);
			Expression times = new Times(Arrays.asList(c1, c2));
			Expression minus = new Minus(Arrays.asList(c1, c2));
			e = new Plus(Arrays.asList(times, minus));
		} catch (IllegalConstruction e1) {
			fail();
		}
		Counter c = new Counter();
		e.accept(c);
		// expected depth: 2 (Plus -> Times/Minus -> Complex)
		assertEquals(2, c.getDepth(), "counting depth of a deeper expression");
		// expected ops: 3 (Plus, Times, Minus)
		assertEquals(3, c.getNbOps());
		// expected numbers: 4 (c1, c2, c1, c2)
		assertEquals(4, c.getNbNbs());
	}

}
