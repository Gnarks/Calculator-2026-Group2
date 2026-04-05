package calculator.command;

import calculator.atoms.Real;

public class PrecisionCommand implements CLICommand {

    @Override
    public boolean execute(String args) {
        if (args == null || args.trim().isEmpty()) {
            System.out.println("Error: Missing arguments. Usage: precision <number>");
            return true;
        }
        try {
            int newPrecision = Integer.parseInt(args.trim());
            if (newPrecision < 0) {
                System.out.println("Error: Precision must be a non-negative integer.");
            } else {
                Real.setPrecision(newPrecision);
                System.out.println("Precision set to " + newPrecision + " decimal places.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid precision value. Usage: precision <number>");
        }
        return true;
    }
}
