package calculator.command;

import calculator.Calculator;
import calculator.atoms.AngleMode;
import java.util.Locale;
import java.util.logging.Logger;

public class AngleModeCommand implements CLICommand {

    private static final Logger LOGGER = Logger.getLogger(AngleModeCommand.class.getName());

    @Override
    public boolean execute(String args) {
        if (args == null || args.trim().isEmpty()) {
            LOGGER.warning("Error: Missing arguments. Usage: mode <DEG|RAD>");
            return true;
        }
        String mode = args.trim().toLowerCase(Locale.ENGLISH);
        switch (mode) {
            case "rad" -> Calculator.mode = AngleMode.RAD;
            case "deg" -> Calculator.mode = AngleMode.DEG;
            default -> {
                LOGGER.warning("Error: Invalid angle representation. Must either be RAD or DEG.");
                return true;
            }
        }
        LOGGER.info("Angle representation successfully set to " + Calculator.mode + " mode.");
        return true;
    }
}
