package calculator.command;

import calculator.atoms.Real;
import java.util.logging.Logger;

public class PrecisionCommand implements CLICommand {

	private static final Logger LOGGER = Logger.getLogger(PrecisionCommand.class.getName());

	@Override
	public boolean execute(String args) {
		if (args == null || args.trim().isEmpty()) {
			LOGGER.warning("Error: Missing arguments. Usage: precision <number>");
			return true;
		}
		try {
			int newPrecision = Integer.parseInt(args.trim());
			if (newPrecision < 0) {
				LOGGER.warning("Error: Precision must be a non-negative integer.");
			} else {
				Real.scale = newPrecision;
				LOGGER.info("Precision set to " + newPrecision + " decimal places.");
			}
		} catch (NumberFormatException e) {
			LOGGER.warning("Error: Invalid precision value. Usage: precision <number>");
		}
		return true;
	}
}
