package calculator;

import calculator.command.CLICommand;
import calculator.command.EvalCommand;
import calculator.command.ExitCommand;
import calculator.command.HelpCommand;
import calculator.command.PrecisionCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import calculator.atoms.Real;
import calculator.operations.*;

/**
 * A very simple calculator in Java
 * University of Mons - UMONS
 * Software Engineering Lab
 * Faculty of Sciences
 *
 * @author tommens
 */
public class Main {

	/**
	 * This is the main method of the application.
	 * It provides examples of how to use it to construct and evaluate arithmetic
	 * expressions.
	 *
	 * @param args Command-line parameters are not used in this version
	 */
	public static void main(String[] args) {
		// Commands registration
        Map<String, CLICommand> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("eval", new EvalCommand());
        commands.put("precision", new PrecisionCommand());

		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Calculator! Type 'help' for assistance.");
		
		boolean running = true;
        while (running) {
            System.out.print("calc> ");
            String input = scanner.nextLine().trim();  // Trim input to handle extra spaces
            
            if (input.isEmpty()) continue;
            
            String[] parts = input.split(" ", 2);  // Split to get command (first word)
            String commandName = parts[0].toLowerCase(); // Command names are case-insensitive
            String arguments = parts.length > 1 ? parts[1] : "";  // The rest of the input is considered as arguments
            
            CLICommand command = commands.get(commandName);  // = null if command not found (or not eval at the beginning of the input)
            
            if (command != null) {
                running = command.execute(arguments);
            } else {
                CLICommand defaultEval = commands.get("eval");
                running = defaultEval.execute(input);
            }
        }
        scanner.close();
	}

}