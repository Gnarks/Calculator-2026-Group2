package calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import calculator.atoms.Real;
import calculator.functions.Cosinus;
import calculator.atoms.Complex;
import calculator.operations.*;
import java.util.Arrays;
import java.util.List;

class TestNotation {

	/*
	 * This is an auxilary method to avoid code duplication.
	 */
	void testNotation(String s, Operation o, Notation n) {
		assertEquals(s, o.toString(n));
	}

	/*
	 * This is an auxilary method to avoid code duplication.
	 */
	void testNotations(String symbol, int value1, int value2, Operation op) {
		// prefix notation:
		testNotation(symbol + " (" + value1 + ", " + value2 + ")", op, Notation.PREFIX);
		// infix notation:
		testNotation("( " + value1 + " " + symbol + " " + value2 + " )", op, Notation.INFIX);
		// postfix notation:
		testNotation("(" + value1 + ", " + value2 + ") " + symbol, op, Notation.POSTFIX);
	}

	@ParameterizedTest
	@ValueSource(strings = { "*", "+", "/", "-" })
	void testOutput(String symbol) {
		int value1 = 8;
		int value2 = 6;
		Operation op = null;
		List<Expression> params = Arrays.asList(new Real(value1), new Real(value2));
		try {
			// construct another type of operation depending on the input value of the
			// parameterised test
			switch (symbol) {
				case "+" -> op = new Plus(params);
				case "-" -> op = new Minus(params);
				case "*" -> op = new Times(params);
				case "/" -> op = new Divides(params);
				default -> fail();
			}
		} catch (IllegalConstruction _) {
			fail();
		}
		testNotations(symbol, value1, value2, op);
	}

	@Test
	void testSeveralNotation() {
		// Construction of an expression with several notations and check that the
		// notation of the inner operation does not affect the printing of the outer
		// operation.
		try {
			List<Expression> innerParams = Arrays.asList(new Real(3), new Real(4));
			Plus inner = new Plus(innerParams);

			List<Expression> outerParams = Arrays.asList(inner, new Real(2));
			Times outer = new Times(outerParams);

			assertEquals("* (+ (3, 4), 2)", outer.toString(Notation.PREFIX));
			assertEquals("( ( 3 + 4 ) * 2 )", outer.toString(Notation.INFIX));
			assertEquals("((3, 4) +, 2) *", outer.toString(Notation.POSTFIX));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testUnaryNotation() {
		try {
			Cosinus op = new Cosinus(new Real(8));
			assertEquals("cos(8)", op.toString(Notation.PREFIX));
			assertEquals("cos(8)", op.toString(Notation.INFIX));
			assertEquals("(8)cos", op.toString(Notation.POSTFIX));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testNotationsComplex() {
		// Test that the notations work for complex numbers as well.
		Complex c1 = new Complex(3, 2);
		Complex c2 = new Complex(1, 4);
		List<Expression> params = Arrays.asList(c1, c2);
		try {
			Times op = new Times(params);
			assertEquals("* (+ 3.0 2.0i, + 1.0 4.0i)", op.toString(Notation.PREFIX));
			assertEquals("( 3.0 + 2.0i * 1.0 + 4.0i )", op.toString(Notation.INFIX));
			assertEquals("(3.0 2.0i +, 1.0 4.0i +) *", op.toString(Notation.POSTFIX));
		} catch (IllegalConstruction e) {
			fail();
		}
	}
}
