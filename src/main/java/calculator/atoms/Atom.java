
package calculator.atoms;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;

/**
 * Atom abstract class
 * An Atom represents a number (or an operand of operations ).
 * The implemented number (atom) types are present in the AtomType enum
 * 
 * @see AtomType
 */
public interface Atom extends Expression {

	/**
	 * applies an operation between two atoms of the same concrete type
	 * The function supposes that both atoms are of the same concrete type
	 * 
	 * @see AtomType
	 *
	 * @param o the operation to apply
	 * @param a the other atom
	 * @return the result of the operation having the same type as a
	 */
	public Atom apply(Operation o, Atom a);

	/**
	 * accepts the specific AtomVisitor
	 *
	 * @param aV The Atomvisitor object being passed as a parameter
	 */
	public void accept(AtomVisitor aV);
}
