package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import calculator.atoms.Real;
import calculator.operations.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

class TestEvaluator {

	private Calculator calc;
	private int value1, value2;

	@BeforeEach
	void setUp() {
		calc = new Calculator();
		value1 = 8;
		value2 = 6;
	}

	@Test
	void testEvaluatorReal() {
		assertEquals(new Real(value1), calc.eval(new Real(value1)));
	}

	@ParameterizedTest
	@ValueSource(strings = { "*", "+", "/", "-" })
	void testEvaluateOperations(String symbol) {
		List<Expression> params = Arrays.asList(new Real(value1), new Real(value2));
		try {
			// construct another type of operation depending on the input value
			// of the parameterised test
			switch (symbol) {
				case "+" -> assertEquals(new Real(value1 + value2), calc.eval(new Plus(params)));
				case "-" -> assertEquals(new Real(value1 - value2), calc.eval(new Minus(params)));
				case "*" -> assertEquals(new Real(value1 * value2), calc.eval(new Times(params)));
				case "/" ->
					assertEquals(new Real(new BigDecimal(value1).divide(new BigDecimal(value2), RoundingMode.HALF_UP)),
							calc.eval(new Divides(params)));
				default -> fail();
			}
		} catch (IllegalConstruction _) {
			fail();
		}
	}

}
