package calculator.command;

import java.io.InputStream;
import java.util.Scanner;

public class HelpCommand implements CLICommand {
	@Override
	public boolean execute(String args) {

		// gets the resource file help.txt at src/main/resources/help.txt
		// and renders it
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("help.txt");
		try (Scanner myReader = new Scanner(is)) {
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
		}

		return true;
	}
}
