package calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
		List<Expression> params = Arrays.asList(new MyNumber(value1), new MyNumber(value2));
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
			List<Expression> innerParams = Arrays.asList(new MyNumber(3), new MyNumber(4));
			Plus inner = new Plus(innerParams);

			List<Expression> outerParams = Arrays.asList(inner, new MyNumber(2));
			Times outer = new Times(outerParams);

			assertEquals("* (+ (3, 4), 2)", outer.toString(Notation.PREFIX));
			assertEquals("( ( 3 + 4 ) * 2 )", outer.toString(Notation.INFIX));
			assertEquals("((3, 4) +, 2) *", outer.toString(Notation.POSTFIX));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

}
