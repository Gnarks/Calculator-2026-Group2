package visitor;

import calculator.Notation;

import calculator.operations.Operation;
import calculator.atoms.*;
import calculator.functions.BinaryFunction;
import calculator.functions.RandomFunction;
import calculator.functions.UnaryFunction;
import calculator.Expression;

/**
 * Printer is a Visitor that converts arithmetic expressions into Strings, using
 * a specified notation (prefix, infix or postfix).
 * It is used in the Calculator class to print arithmetic expressions in a
 * human-readable format.
 *
 * @see Visitor
 * @see calculator.Calculator
 */
public class Printer extends Visitor {

	/*
	 * The StringBuilder that is used to build the string representation of an
	 * arithmetic expression
	 */
	private final StringBuilder sb = new StringBuilder();
	/*
	 * The notation to be used for printing expressions (prefix, infix or postfix)
	 */
	private final Notation notation;

	private static final int DEFAULT_DENOMINATOR = 1;
	private static final String RANDINT_FUNC = "randint";
	private static final String RANDRAT_FUNC = "randrat";
	private static final String RANDREAL_FUNC = "randreal";
	private static final String RANDCOMPLEX_FUNC = "randcomplex";
	private static final String RAND_FUNC = "rand";

	/**
	 * Constructor of the Printer class, which takes a Notation as a parameter to
	 * specify the notation to be used for printing expressions.
	 * 
	 * @param notation The notation to be used for printing expressions (prefix,
	 *                 infix or postfix)
	 */
	public Printer(Notation notation) {
		this.notation = notation;
	}

	/**
	 * Constructor of the Printer class, which initializes the notation to infix by
	 * default.
	 */
	public Printer() {
		this.notation = Notation.INFIX; // Default notation is infix
	}

	/**
	 * The visit methods for Real.
	 * These methods build the string representation of the expression being
	 * visited, using the specified notation.
	 * 
	 * @param r The Real to be visited
	 */
	@Override
	public void visit(Real r) {
		if (r.isNan()) {
			sb.append("NaN");
		} else if (r.isPlusInf()) {
			sb.append("+infinity");
		} else if (r.isMinusInf()) {
			sb.append("-infinity");
		} else {
			sb.append(r.getValue().stripTrailingZeros());
		}
	}

	@Override
	public void visit(IntegerAtom i) {
		sb.append(i.getValue());
	}

	@Override
	public void visit(Complex c) {
		double realPart = c.getValue().getReal();
		double imaginaryPart = c.getValue().getImaginary();

		switch (notation) {
			case PREFIX -> {
				sb.append("+ ").append(realPart).append(" ").append(imaginaryPart).append("i");
			}
			case POSTFIX -> {
				sb.append(realPart).append(" ").append(imaginaryPart).append("i +");
			}
			case INFIX -> {
				sb.append(realPart).append(" + ").append(imaginaryPart).append("i");
			}
		}
	}

	@Override
	public void visit(Rationnal q) {
		int num = q.getNumerator();
		int den = q.getDenominator();

		if (den == DEFAULT_DENOMINATOR) {
			sb.append(num);
			return;
		}

		switch (notation) {
			case PREFIX -> {
				sb.append("/ ").append(num).append(" ").append(den);
			}
			case POSTFIX -> {
				sb.append(num).append(" ").append(den).append(" /");
			}
			case INFIX -> {
				sb.append(num).append("/").append(den);
			}
		}
	}

	/**
	 * The visit method for Operation. Needs to handle the different notations
	 * (prefix, infix and postfix) and the different number of arguments that an
	 * operation can have.
	 * It uses helper methods visitArgs and visitArgsInfix to handle the different
	 * notations.
	 * 
	 * @param o The Operation to be visited
	 */
	@Override
	public void visit(Operation o) {
		switch (notation) {
			case PREFIX -> {
				sb.append(o.getSymbol()).append(" (");
				visitArgs(o, ", ");
				sb.append(")");
			}
			case INFIX -> {
				sb.append("( ");
				visitArgs(o, " " + o.getSymbol() + " ");
				sb.append(" )");
			}
			case POSTFIX -> {
				sb.append("(");
				visitArgs(o, ", ");
				sb.append(") ").append(o.getSymbol());
			}
		}
	}

	@Override
	public void visit(UnaryFunction o) {
		switch (notation) {
			case PREFIX, INFIX -> {
				sb.append(o.getSymbol()).append("(");
				o.getArg().accept(this);
				sb.append(")");
			}
			case POSTFIX -> {
				sb.append("(");
				o.getArg().accept(this);
				sb.append(")").append(o.getSymbol());
			}
		}
	}

	/**
	 * Helper method to visit the arguments of an operation and build their string
	 * representation.
	 * 
	 * @param o         The operation whose arguments are to be visited
	 * @param separator The string used to separate the arguments
	 */
	private void visitArgs(Operation o, String separator) {
		for (Expression arg : o.getArgs()) {
			arg.accept(this);
			sb.append(separator);
		}
		// Remove the last separator
		if (sb.length() > 0) {
			sb.setLength(sb.length() - separator.length());
		}
	}

	/**
	 * Getter method to obtain the string representation of the expression that has
	 * been built by the visit methods.
	 * 
	 * @return The string representation of the expression that has been built by
	 *         the visit methods
	 */
	public String getResult() {
		return sb.toString();
	}

	@Override
	public void visit(BinaryFunction f) {
		switch (notation) {
			case PREFIX, INFIX -> {
				sb.append(f.getSymbol()).append("(");
				f.getFirstArg().accept(this);
				sb.append(", ");
				f.getSecondArg().accept(this);
				sb.append(")");
			}
			case POSTFIX -> {
				sb.append("(");
				f.getFirstArg().accept(this);
				sb.append(", ");
				f.getSecondArg().accept(this);
				sb.append(")").append(f.getSymbol());
			}
		}
	}

	/**
	 * Formats a {@code RandomFunction} node with corresponding function calls (e.g. 'randint', 'randrat').
	 * The output representation depends on the chosen {@link calculator.Notation}.
	 *
	 * @param f the {@code RandomFunction} node to append to the print result
	 */
	@Override
	public void visit(RandomFunction f) {
		String funcName;
		switch(f.getType()) {
			case INTEGER -> funcName = RANDINT_FUNC;
			case RATIONNAL -> funcName = RANDRAT_FUNC;
			case REAL -> funcName = RANDREAL_FUNC;
			case COMPLEX -> funcName = RANDCOMPLEX_FUNC;
			default -> funcName = RAND_FUNC;
		}
		
		switch (notation) {
			case PREFIX, INFIX -> {
				sb.append(funcName).append("(");
				for (int i = 0; i < f.getArgs().size(); i++) {
					if (i > 0) sb.append(", ");
					f.getArgs().get(i).accept(this);
				}
				sb.append(")");
			}
			case POSTFIX -> {
				sb.append("(");
				for (int i = 0; i < f.getArgs().size(); i++) {
					if (i > 0) sb.append(", ");
					f.getArgs().get(i).accept(this);
				}
				sb.append(")").append(funcName);
			}
		}
	}

}
