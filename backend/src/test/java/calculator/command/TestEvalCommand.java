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
    private EvalCommand evalCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
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
        // Log is printed via Logger which writes to System.err generally, 
        // but let's just make sure execute handles the error gracefully and doesn't crash 
        boolean result = evalCommand.execute("3 + * 4");
        assertTrue(result);
    }
}