package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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

		Expression e;
		Calculator c = new Calculator();
		Logger logger = Logger.getLogger(Main.class.getName());

		try {

			e = new Real(8);
			c.print(e);
			c.eval(e);

			List<Expression> params = new ArrayList<>();
			Collections.addAll(params, new Real(3), new Real(4), new Real(5));
			e = new Plus(params);
			c.printExpressionDetails(e);
			c.eval(e);

			List<Expression> params2 = new ArrayList<>();
			Collections.addAll(params2, new Real(5), new Real(3));
			e = new Minus(params2);
			c.print(e);
			c.eval(e);

			List<Expression> params3 = new ArrayList<>();
			Collections.addAll(params3, new Plus(params), new Minus(params2));
			e = new Times(params3);
			c.printExpressionDetails(e);

			System.out.println("PREFIX notation: " + c.format(e, Notation.PREFIX));
			System.out.println("POSTFIX notation: " + c.format(e, Notation.POSTFIX));

			c.eval(e);

			List<Expression> params4 = new ArrayList<>();
			Collections.addAll(params4, new Plus(params), new Minus(params2), new Real(0));
			e = new Divides(params4);
			c.print(e);
			c.eval(e);
		}

		catch (IllegalConstruction _) {
			logger.info("cannot create operations without parameters");
		} catch (ArithmeticException error) {
			System.out.println("Error: " + error.getMessage() + " (NaN)");
		}
	}

}
