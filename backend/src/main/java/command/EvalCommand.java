package calculator.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import calculator.Calculator;
import calculator.Expression;
import calculator.atoms.Real;
import calculator.operations.Divides;
import calculator.operations.Minus;
import calculator.operations.Plus;
import calculator.operations.Times;
import calculator.IllegalConstruction;
import calculator.Notation;

public class EvalCommand implements CLICommand {
    @Override
    public boolean execute(String args) {
        try {
            // Add call to parsing and evaluation logic
            System.out.println(args + " = ");
            
            Logger logger = Logger.getLogger("EvalCommand");

            // ------------------OLD MAIN----------------------------------------------------
           try {

            Calculator c = new Calculator();
            Expression e;

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
        // ----------------------------------------------------------------------



        } catch (Exception e) {
            System.out.println("Error evaluating expression: " + e.getMessage());
        }
        return true;
    }
    
}
