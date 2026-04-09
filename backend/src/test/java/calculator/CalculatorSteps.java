package calculator;

import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import calculator.operations.*;
import calculator.functions.*;

public class CalculatorSteps {

	private ArrayList<Expression> params;
	private Operation op;
	private Expression unaryOp;
	private String unaryOpName;
	private Calculator c;

    @Before
	public void resetMemoryBeforeEachScenario() {
		params = null;
		op = null;
		unaryOp = null;
		unaryOpName = null;
	}

	@Given("I initialise a calculator")
	public void givenIInitialiseACalculator() {
		c = new Calculator();
	}

	@Given("an integer operation {string}")
	public void givenAnIntegerOperation(String s) {
		// Write code here that turns the phrase above into concrete actions
		params = new ArrayList<>(); // create an empty set of parameters to be filled in
		try {
			switch (s) {
				case "+" -> op = new Plus(params);
				case "-" -> op = new Minus(params);
				case "*" -> op = new Times(params);
				case "//" -> op = new Divides(params);
				case "**" -> op = new Power(params);
				default -> fail();
			}
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	// The following example shows how to use a DataTable provided as input.
	// The example looks slightly complex, since DataTables can take as input
	// tables in two dimensions, i.e. rows and lines. This is why the input
	// is a list of lists.
	@Given("the following list of integer numbers")
	public void givenTheFollowingListOfNumbers(List<List<String>> numbers) {
		params = new ArrayList<>();
		// Since we only use one line of input, we use get(0) to take the first line of
		// the list,
		// which is a list of strings, that we will manually convert to integers:
		numbers.get(0).forEach(n -> params.add(new IntegerAtom(Integer.parseInt(n))));
		params.forEach(n -> System.out.println("value =" + n));
		op = null;
	}

	// The string in the Given annotation shows how to use regular expressions...
	// In this example, the notation d+ is used to represent numbers, i.e. nonempty
	// sequences of digits
	@Given("^the sum of two numbers (\\d+) and (\\d+)$")
	// The alternative, and in this case simpler, notation would be:
	// @Given("the sum of two numbers {int} and {int}")
	public void givenTheSum(int n1, int n2) {
		try {
			params = new ArrayList<>();
			params.add(new IntegerAtom(n1));
			params.add(new IntegerAtom(n2));
			op = new Plus(params);
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Then("^its (.*) notation is (.*)$")
	public void thenItsNotationIs(String notation, String s) {
		if (notation.equals("PREFIX") || notation.equals("POSTFIX") || notation.equals("INFIX")) {
			assertEquals(s, op.toString(Notation.valueOf(notation)));
		} else
			fail(notation + " is not a correct notation! ");
	}

	@When("^I provide a (.*) number (-?\\d+)$")
	public void whenIProvideANumber(String s, int val) {
		// add extra parameter to the operation
		params = new ArrayList<>();
		params.add(new IntegerAtom(val));
		op.addMoreParams(params);
	}

	@When("I provide a complex number {double} and {double}i")
	public void whenIProvideAComplexNumber(double real, double imaginary) {
		params = new ArrayList<>();
		params.add(new Complex(real, imaginary));
		if (op != null) {
			op.addMoreParams(params);
		}
	}

	@When("I provide a real number {double}")
	public void whenIProvideARealNumber(double real) {
		params = new ArrayList<>();
		params.add(new Real(real));
		if (op != null) {
			op.addMoreParams(params);
		}
	}

	@Then("^the (.*) is (\\d+)$")
	public void thenTheOperationIs(String s, int val) {
		try {
			Atom expected;
			switch (s) {
				case "sum" -> {
					op = new Plus(params);
					expected = new IntegerAtom(val);
				}
				case "product" -> {
					op = new Times(params);
					expected = new IntegerAtom(val);
				}
				case "quotient" -> {
					op = new Divides(params);
					expected = new IntegerAtom(val);
				}
				case "difference" -> {
					op = new Minus(params);
					expected = new IntegerAtom(val);
				}
				case "exponentiation" -> {
					op = new Power(params);
					expected = new Real(val);
				}
				default -> {
					fail();
					return;
				}
			}
			assertEquals(expected, c.eval(op));
		} catch (IllegalConstruction _) {
			fail();
		}
	}

	@Then("the operation evaluates to {int}")
	public void thenTheOperationEvaluatesTo(int val) {
		assertEquals(new IntegerAtom(val), c.eval(op));
	}

	@Then("the operation evaluates to the complex number {double} and {double}i")
	public void thenTheOperationEvaluatesToComplex(double real, double imaginary) {
		assertEquals(new Complex(real, imaginary), c.eval(op));
	}

	@Then("the operation throws an ArithmeticException")
	public void thenTheOperationThrowsAnArithmeticException() {
		try {
			c.eval(op);
			fail("should have thrown an ArithmeticException");
		} catch (ArithmeticException e) {
			assertEquals("Division by zero", e.getMessage());
		}
	}

	@When("I provide a rational number {int} \\/ {int}")
	public void whenIProvideARationalNumber(int numerator, int denominator) {
		params = new ArrayList<>();
		params.add(new Rationnal(numerator, denominator));
		if (op != null) {
			op.addMoreParams(params);
		}
	}

	@Then("the operation evaluates to the rational number {int} \\/ {int}")
	public void thenTheOperationEvaluatesToRational(int numerator, int denominator) {
		assertEquals(new Rationnal(numerator, denominator), c.eval(op));
	}

	@Then("the operation evaluates to the real number {double}")
	public void thenTheOperationEvaluatesToReal(double real) {
		Real expected = new Real(real);
		Real result = (Real) c.eval(op);
		assertEquals(expected, result);
	}

	@Then("the operation is not possible")
	public void thenTheOperationIsNotPossible() {
		assertThrows(ArithmeticException.class, () -> c.eval(op));
	}

	@When("I set the precision to {int}")
	public void whenISetThePrecision(int precision) {
		Real.scale = precision;
	}

	@Given("a unary function {string}")
	public void givenAUnaryFunction(String s) {
		unaryOpName = s;
	}

	@When("I provide a single real number {double}")
	public void whenIProvideASingleRealNumber(double real) {
		try {
			Expression arg = new Real(real);
			switch (unaryOpName) {
				case "sin" -> unaryOp = new Sinus(arg);
				case "cos" -> unaryOp = new Cosinus(arg);
				case "tan" -> unaryOp = new Tangente(arg);
				case "asin" -> unaryOp = new Arcsinus(arg);
				case "acos" -> unaryOp = new Arccosinus(arg);
				case "atan" -> unaryOp = new Arctangente(arg);
				case "sinh" -> unaryOp = new Sinh(arg);
				case "cosh" -> unaryOp = new Cosh(arg);
				case "tanh" -> unaryOp = new Tanh(arg);
				case "ln" -> unaryOp = new Ln(arg);
				case "sqrt" -> unaryOp = new Sqrt(arg);
				default -> fail("Unknown unary function: " + unaryOpName);
			}
		} catch (IllegalConstruction _) {
			fail("Illegal construction for unary function");
		}
	}

	@Then("the unary operation evaluates to the real number {double}")
	public void thenTheUnaryOperationEvaluatesToReal(double real) {
		Real expected = new Real(real);
		Real result = (Real) c.eval(unaryOp);
		assertEquals(expected, result);
	}
}
