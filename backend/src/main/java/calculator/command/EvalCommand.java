package calculator.command;

import calculator.Calculator;
import calculator.Expression;
import calculator.antlr.Parser;
import calculator.atoms.Atom;
import calculator.Notation;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvalCommand implements CLICommand {

	private static final Logger LOGGER = Logger.getLogger(EvalCommand.class.getName());

	@Override
	public boolean execute(String args) {
		try {
			if (args == null || args.trim().isEmpty()) {
				LOGGER.warning("No expression provided.");
				return true;
			}

			Parser parser = new Parser();
			Expression e = parser.parse(args);

			Calculator c = new Calculator();
			Atom result = c.eval(e);

			System.out.println(args + " = " + c.format(result, Notation.INFIX));

		} catch (IllegalArgumentException e) {
			LOGGER.log(Level.SEVERE, "Invalid expression: " + e.getMessage());
		} catch (IllegalStateException | UnsupportedOperationException | ArithmeticException e) {
			LOGGER.log(Level.SEVERE, "Error evaluating expression: {0}", e.getMessage());
		}
		return true;
	}
}
