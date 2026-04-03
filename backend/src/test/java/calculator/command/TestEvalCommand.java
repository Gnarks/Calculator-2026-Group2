package calculator.command;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEvalCommand {

	private final PrintStream standardOut = System.out;
	private final PrintStream standardErr = System.err;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();
	private EvalCommand evalCommand;
	private StreamHandler testLogHandler;
	private static final Logger LOGGER = Logger.getLogger(""); // Root logger to handle CLI logger

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
		System.setErr(new PrintStream(errStreamCaptor));

		// forcing Logger to be caught by captor
		testLogHandler = new StreamHandler(errStreamCaptor, new SimpleFormatter());
		testLogHandler.setLevel(Level.ALL);
		LOGGER.addHandler(testLogHandler);

		evalCommand = new EvalCommand();
	}

	@AfterEach
	public void tearDown() {
		System.setOut(standardOut);
		System.setErr(standardErr);

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
		testLogHandler.flush();
		assertTrue(result);

		String capturedErr = errStreamCaptor.toString();
		assertTrue(capturedErr.contains("No expression provided."));
	}

	@Test
	void testExecuteWithNull() {
		boolean result = evalCommand.execute(null);
		assertTrue(result);

		testLogHandler.flush();
		assertTrue(result);

		String capturedErr = errStreamCaptor.toString();
		assertTrue(capturedErr.contains("No expression provided."));
	}

	@Test
	void testExecuteWithInvalidExpression() {

		boolean result = evalCommand.execute("3 + * 4");
		testLogHandler.flush();
		assertTrue(result);

		String capturedErr = errStreamCaptor.toString();
		assertTrue(capturedErr.contains("SEVERE"));
		assertTrue(capturedErr.contains("Invalid expression: Invalid expression at line 1:4"));
	}
}
