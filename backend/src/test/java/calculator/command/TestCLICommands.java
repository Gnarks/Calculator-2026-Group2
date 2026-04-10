package calculator.command;

import calculator.Calculator;
import calculator.atoms.AngleMode;
import calculator.atoms.Real;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

class TestCLICommands {

	private final PrintStream standardOut = System.out;
	private final PrintStream standardErr = System.err;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();
	private StreamHandler testLogHandler;
	private static final Logger LOGGER = Logger.getLogger(""); // Root logger to handle CLI logger
	private int originalScale;

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
		System.setErr(new PrintStream(errStreamCaptor));
		
		// forcing Logger to be caught by captor
		testLogHandler = new StreamHandler(errStreamCaptor, new SimpleFormatter());
		testLogHandler.setLevel(Level.ALL);
		LOGGER.addHandler(testLogHandler);

		originalScale = Real.scale;
		Calculator.mode = AngleMode.RAD;
	}

	@AfterEach
	public void tearDown() {
		System.setOut(standardOut);
		System.setErr(standardErr);
		LOGGER.removeHandler(testLogHandler);
		Real.scale = originalScale; // Restore original precision after each test
	}

	@Test
	void testPrecisionCommandValid() {
		PrecisionCommand cmd = new PrecisionCommand();
		boolean result = cmd.execute("15");

		testLogHandler.flush();

		assertTrue(result);
		assertEquals(15, Real.scale);
		assertTrue(errStreamCaptor.toString().contains("Precision set to 15 decimal places."));
	}

	@Test
	void testPrecisionCommandInvalid() {
		PrecisionCommand cmd = new PrecisionCommand();
		boolean result = cmd.execute("abc");

		testLogHandler.flush();

		assertTrue(result);
		assertTrue(errStreamCaptor.toString().contains("Error: Invalid precision value. Usage: precision <number>"));
	}

	@Test
	void testPrecisionCommandNegative() {
		PrecisionCommand cmd = new PrecisionCommand();
		boolean result = cmd.execute("-5");

		testLogHandler.flush();

		assertTrue(result);
		assertTrue(errStreamCaptor.toString().contains("Error: Precision must be a non-negative integer."));
	}

	@Test
	void testPrecisionCommandEmpty() {
		PrecisionCommand cmd = new PrecisionCommand();
		boolean result = cmd.execute("");

		testLogHandler.flush();

		assertTrue(result);
		assertTrue(errStreamCaptor.toString().contains("Error: Missing arguments. Usage: precision <number>"));
	}

	@Test
	void testHelpCommand() {
		HelpCommand cmd = new HelpCommand();
		boolean result = cmd.execute("");

		assertTrue(result);
		// The help command reads from help.txt, making sure we get text output
		assertFalse(outputStreamCaptor.toString().isEmpty());
	}

	@Test
	void testModeCommand() {
		assertEquals(AngleMode.RAD, Calculator.mode);
		AngleModeCommand angleModeCommand = new AngleModeCommand();
		boolean result = angleModeCommand.execute("DEG");
		assertTrue(result);
		assertEquals(AngleMode.DEG, Calculator.mode);
		result = angleModeCommand.execute("RAD");
		assertTrue(result);
		assertEquals(AngleMode.RAD, Calculator.mode);
	}

	@Test
	void testModeCommand_invalidArgs() {
		AngleModeCommand angleModeCommand = new AngleModeCommand();
		boolean result = angleModeCommand.execute("invalid");
		testLogHandler.flush();
		assertTrue(result);
		assertTrue(errStreamCaptor.toString()
				.contains("Error: Invalid angle representation. Must either be RAD or DEG."));
	}

	@Test
	void testModeCommand_emptyArgs() {
		AngleModeCommand angleModeCommand = new AngleModeCommand();
		boolean result = angleModeCommand.execute("");
		testLogHandler.flush();
		assertTrue(result);
		assertTrue(errStreamCaptor.toString().contains("Error: Missing arguments. Usage: mode <DEG|RAD>"));
	}
}
