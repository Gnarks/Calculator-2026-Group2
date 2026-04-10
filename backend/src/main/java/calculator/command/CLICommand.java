package calculator.command;

/**
 * Common interface for all Command Line Interface (CLI) commands.
 * Examples include evaluating expressions, handling angles, managing precision,
 * or listing available commands (help).
 */
public interface CLICommand {
    /**
     * Executes the specific CLI command.
     *
     * @param args The remaining part of the input string typed by the user
     * @return a boolean indicating whether the CLI loop should continue (true) or terminate (false - usually for EXIT)
     */
    boolean execute(String args);
}