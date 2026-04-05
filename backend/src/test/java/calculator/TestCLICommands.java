package calculator;

import calculator.command.ExitCommand;
import calculator.command.HelpCommand;
import calculator.command.PrecisionCommand;
import calculator.atoms.Real;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TestCLICommands {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private int originalScale;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        originalScale = Real.scale;
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        Real.setPrecision(originalScale); // Restore original precision after each test
    }

    @Test
    void testPrecisionCommandValid() {
        PrecisionCommand cmd = new PrecisionCommand();
        boolean result = cmd.execute("15");
        
        assertTrue(result);
        assertEquals(15, Real.scale);
        assertTrue(outputStreamCaptor.toString().contains("Precision set to 15 decimal places."));
    }

    @Test
    void testPrecisionCommandInvalid() {
        PrecisionCommand cmd = new PrecisionCommand();
        boolean result = cmd.execute("abc");
        
        assertTrue(result);
        assertTrue(outputStreamCaptor.toString().contains("Error: Invalid precision value. Usage: precision <number>"));
    }

    @Test
    void testPrecisionCommandNegative() {
        PrecisionCommand cmd = new PrecisionCommand();
        boolean result = cmd.execute("-5");
        
        assertTrue(result);
        assertTrue(outputStreamCaptor.toString().contains("Error: Precision must be a non-negative integer."));
    }

    @Test
    void testPrecisionCommandEmpty() {
        PrecisionCommand cmd = new PrecisionCommand();
        boolean result = cmd.execute("");
        
        assertTrue(result);
        assertTrue(outputStreamCaptor.toString().contains("Error: Missing arguments. Usage: precision <number>"));
    }

    @Test
    void testHelpCommand() {
        HelpCommand cmd = new HelpCommand();
        boolean result = cmd.execute("");
        
        assertTrue(result);
        // The help command reads from help.txt, making sure we get text output
        assertFalse(outputStreamCaptor.toString().isEmpty());
    }
}
