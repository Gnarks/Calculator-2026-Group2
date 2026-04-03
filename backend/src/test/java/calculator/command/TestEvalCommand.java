package calculator.command;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEvalCommand {

	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();
	private EvalCommand evalCommand;

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
		System.setErr(new PrintStream(errStreamCaptor));
		evalCommand = new EvalCommand();
	}

	@AfterEach
	public void tearDown() {
		System.setOut(standardOut);
	}

	@Test
	void testExecuteWithValidExpression() {
		boolean result = evalCommand.execute("3 + 4 * 2");
		assertTrue(result);
		assertTrue(outputStreamCaptor.toString().contains("11"));
	}

	@Test
	void testExecuteWithEmptyString() {
		boolean result = evalCommand.execute("");
		assertTrue(result);
		assertTrue(outputStreamCaptor.toString().trim().isEmpty());
	}

	@Test
	void testExecuteWithNull() {
		boolean result = evalCommand.execute(null);
		assertTrue(result);
		assertTrue(outputStreamCaptor.toString().trim().isEmpty());
	}

	@Test
	void testExecuteWithInvalidExpression() {

		boolean result = evalCommand.execute("3 + * 4");
		assertTrue(result);
		assertEquals(
				"SEVERE: Invalid expression: Invalid expression at line 1:4 - extraneous input '*' expecting {'cos', 'sin', 'tan', 'acos', 'asin', 'atan', 'cosh', 'sinh', 'tanh', 'ln', 'log', 'sqrt', '(', '+', '-', 'pi', 'e', 'i', INT}",
				errStreamCaptor.toString().split("\n")[1]);
	}
}
