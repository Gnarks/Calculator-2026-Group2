package calculator;

import calculator.atoms.Atom;
import calculator.operations.Operation;
import visitor.Visitor;

/**
 * Expression is an abstract class that represents arithmetic expressions.
 * It has two concrete subclasses Operation and Atoms.
 *
 * @see Operation
 * @see Atom
 */
public interface Expression {

	/**
	 * accept is a method needed to implement the visitor design pattern
	 *
	 * @param v The visitor object being passed as a parameter
	 */
	void accept(Visitor v);

}
