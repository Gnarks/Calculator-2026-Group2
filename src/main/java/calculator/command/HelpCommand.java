package calculator.command;

public class HelpCommand implements CLICommand {
    @Override
    public boolean execute(String args) {
        System.out.println("Help information (to complete).");
        return true;
    }
}
