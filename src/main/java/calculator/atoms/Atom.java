
package calculator.atoms;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;

public interface Atom extends Expression {

	/**
	 * applies an operation between two atoms of the same concrete type
	 * The function supposes that both atoms are of the same concrete type
	 * 
	 * @see AtomType
	 *
	 * @param o the operation to apply
	 * @param a the other atom
	 * @return the result of the operation having the same type as {@value}a
	 */
	public Atom apply(Operation o, Atom a);

	/**
	 * accepts the specific AtomVisitor
	 *
	 * @param v The Atomvisitor object being passed as a parameter
	 */
	public void accept(AtomVisitor aV);
}
