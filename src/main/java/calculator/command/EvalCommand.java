package calculator.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import calculator.Expression;
import calculator.Notation;
import calculator.Calculator;
import calculator.Divides;
import calculator.IllegalConstruction;
import calculator.Main;
import calculator.Minus;
import calculator.MyNumber;
import calculator.Plus;
import calculator.Times;

public class EvalCommand implements CLICommand {
    @Override
    public boolean execute(String args) {
        try {
            // Add call to parsing and evaluation logic
            System.out.println(args + " = ");
            


            // ------------------OLD MAIN----------------------------------------------------
            Expression e;
            Calculator c = new Calculator();
            Logger logger = Logger.getLogger(Main.class.getName());

            
            try{

			e = new MyNumber(8);
			c.print(e);

			List<Expression> params = new ArrayList<>();
			Collections.addAll(params, new MyNumber(3), new MyNumber(4), new MyNumber(5));
			e = new Plus(params);
			c.printExpressionDetails(e);

			List<Expression> params2 = new ArrayList<>();
			Collections.addAll(params2, new MyNumber(5), new MyNumber(3));
			e = new Minus(params2);
			c.print(e);

			List<Expression> params3 = new ArrayList<>();
			Collections.addAll(params3, new Plus(params), new Minus(params2));
			e = new Times(params3);
			c.printExpressionDetails(e);

			List<Expression> params4 = new ArrayList<>();
			Collections.addAll(params4, new Plus(params), new Minus(params2), new MyNumber(0));
			e = new Divides(params4);
			c.print(e);
		}

		catch(IllegalConstruction _) {
			logger.info("cannot create operations without parameters");
		}
		catch(ArithmeticException error) {
			System.out.println("Error: " + error.getMessage() + " (NaN)");
		}
        // ----------------------------------------------------------------------



        } catch (Exception e) {
            System.out.println("Error evaluating expression: " + e.getMessage());
        }
        return true;
    }
    
}
