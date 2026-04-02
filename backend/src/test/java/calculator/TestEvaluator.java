package calculator;

//Import Junit5 libraries for unit testing:
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import calculator.atoms.Real;
import calculator.functions.*;
import calculator.atoms.Complex;
import calculator.operations.*;
import ch.obermuhlner.math.big.BigDecimalMath;
import calculator.atoms.Rationnal;

import java.math.BigDecimal;
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

	@Test
	void testEvaluateCosinus() {
		try {

			BigDecimal bd = BigDecimal.valueOf(Math.PI / 4);
			BigDecimal cos = BigDecimalMath.cos(bd, Real.context);
			assertEquals(new Real(cos), calc.eval(new Cosinus(new Real(Math.PI / 4))));

			bd = new BigDecimal(0);
			cos = BigDecimalMath.cos(bd, Real.context);
			assertEquals(new Real(cos), calc.eval(new Cosinus(new Real(0))));

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEvaluateSinus() {
		try {
			BigDecimal bd = BigDecimal.valueOf(Math.PI / 4);
			BigDecimal sin = BigDecimalMath.sin(bd, Real.context);
			assertEquals(new Real(sin), calc.eval(new Sinus(new Real(Math.PI / 4))));

			bd = new BigDecimal(0);
			sin = BigDecimalMath.sin(bd, Real.context);
			assertEquals(new Real(sin), calc.eval(new Sinus(new Real(0))));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEvaluateTangente() {
		try {
			BigDecimal bd = BigDecimal.valueOf(Math.PI / 4);
			BigDecimal tan = BigDecimalMath.tan(bd, Real.context);
			assertEquals(new Real(tan), calc.eval(new Tangente(new Real(Math.PI / 4))));

			bd = new BigDecimal(0);
			tan = BigDecimalMath.tan(bd, Real.context);
			assertEquals(new Real(tan), calc.eval(new Tangente(new Real(0))));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEvaluateArcsinus() {
		try {
			BigDecimal bd = new BigDecimal(0.5);
			BigDecimal asin = BigDecimalMath.asin(bd, Real.context);
			assertEquals(new Real(asin), calc.eval(new Arcsinus(new Real(0.5))));

			bd = new BigDecimal(0);
			asin = BigDecimalMath.asin(bd, Real.context);
			assertEquals(new Real(asin), calc.eval(new Arcsinus(new Real(0))));

		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEvaluateArccosinus() {
		try {
			BigDecimal bd = new BigDecimal(0.5);
			BigDecimal acos = BigDecimalMath.acos(bd, Real.context);
			assertEquals(new Real(acos), calc.eval(new Arccosinus(new Real(0.5))));

			bd = new BigDecimal(0);
			acos = BigDecimalMath.acos(bd, Real.context);
			assertEquals(new Real(acos), calc.eval(new Arccosinus(new Real(0))));
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEvaluateArctangente() {
		try {
			BigDecimal bd = new BigDecimal(0.5);
			BigDecimal atan = BigDecimalMath.atan(bd, Real.context);
			assertEquals(new Real(atan), calc.eval(new Arctangente(new Real(0.5))));

			bd = new BigDecimal(0);
			atan = BigDecimalMath.atan(bd, Real.context);
			assertEquals(new Real(atan), calc.eval(new Arctangente(new Real(0))));
		} catch (IllegalConstruction e) {
			fail();
		}
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
					assertEquals(new Real(new BigDecimal(value1).divide(new BigDecimal(value2), Real.context)),
							calc.eval(new Divides(params)));
				default -> fail();
			}
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Test
	void testEvaluatorComplex() {
		assertEquals(new Complex(value1, value2), calc.eval(new Complex(value1, value2)));
	}

	@ParameterizedTest
	@ValueSource(strings = { "*", "+", "/", "-" })
	void testEvaluateComplexOperations(String symbol) {
		// value1 = 8, value2 = 6
		Complex c1 = new Complex(value1, value2); // 8 + 6i
		Complex c2 = new Complex(value2, value1); // 6 + 8i
		List<Expression> params = Arrays.asList(c1, c2);
		try {
			switch (symbol) {
				case "+" -> assertEquals(new Complex(14, 14), calc.eval(new Plus(params)));
				case "-" -> assertEquals(new Complex(2, -2), calc.eval(new Minus(params)));
				case "*" -> assertEquals(new Complex(0, 100), calc.eval(new Times(params)));
				case "/" -> assertEquals(new Complex(0.96, -0.28), calc.eval(new Divides(params)));
				default -> fail();
			}
		} catch (IllegalConstruction e) {
			fail();
		}
	}

	@Test
	void testEvaluatorRationnal() {
		assertEquals(new Rationnal(value1, value2), calc.eval(new Rationnal(value1, value2)));
	}

	@ParameterizedTest
	@ValueSource(strings = { "*", "+", "/", "-" })
	void testEvaluateRationnalOperations(String symbol) {
		// value1 = 8, value2 = 6
		Rationnal q1 = new Rationnal(value1, value2); // equal to 8/6 = 4/3
		Rationnal q2 = new Rationnal(value2, value1); // equal to 6/8 = 3/4
		List<Expression> params = Arrays.asList(q1, q2);
		try {
			switch (symbol) {
				case "+" -> assertEquals(new Rationnal(25, 12), calc.eval(new Plus(params)));
				case "-" -> assertEquals(new Rationnal(7, 12), calc.eval(new Minus(params)));
				case "*" -> assertEquals(new Rationnal(1, 1), calc.eval(new Times(params)));
				case "/" -> assertEquals(new Rationnal(16, 9), calc.eval(new Divides(params)));
				default -> fail();
			}
		} catch (IllegalConstruction e) {
			fail();
		}
	}

}
