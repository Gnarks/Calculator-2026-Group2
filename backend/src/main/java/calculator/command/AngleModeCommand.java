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
        AngleMode angleMode = null;
        switch (mode) {
            case "rad" -> angleMode = AngleMode.RAD;
            case "deg" -> angleMode = AngleMode.DEG;
            default -> LOGGER.warning("Error: Invalid angle representation. Must either be RAD or DEG.");
        }
        if(angleMode != null) {
            Calculator.mode = angleMode;
            LOGGER.info("Angle representation successfully set to " + Calculator.mode + " mode.");
        }
        return true;
    }
}
