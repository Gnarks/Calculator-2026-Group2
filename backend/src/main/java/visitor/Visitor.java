package visitor;

import calculator.operations.Operation;
import calculator.atoms.*;

/**
 * Visitor design pattern
 */
public abstract class Visitor {

	/**
	 * The Visitor can traverse a Real number (a subtype of Expression)
	 *
	 * @param r The Real number being visited
	 */
	public abstract void visit(Real r);

	/**
	 * The Visitor can traverse a Complex number (a subtype of Expression)
	 *
	 * @param r The Real number being visited
	 */
	public abstract void visit(Complex c);

	/**
	 * The Visitor can traverse a Rationnal number (a subtype of Expression)
	 *
	 * @param r The Real number being visited
	 */
	public abstract void visit(Rationnal q);

	/**
	 * The Visitor can traverse an operation (a subtype of Expression)
	 *
	 * @param o The operation being visited
	 */
	public abstract void visit(Operation o);
}
