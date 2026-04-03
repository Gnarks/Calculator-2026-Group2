package calculator.command;

public class ExitCommand implements CLICommand {
    @Override
    public boolean execute(String args) {
        System.exit(0);
        return true;
    }
}
