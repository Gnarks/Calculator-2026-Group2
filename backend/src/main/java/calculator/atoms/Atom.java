package calculator.atoms;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import calculator.functions.BinaryFunction;
import calculator.functions.UnaryFunction;

/**
 * Atom abstract class
 * An Atom represents a number (or an operand of operations).
 * The implemented number (atom) types are present in the AtomType enum
 * 
 * @see AtomType
 */
public interface Atom extends Expression {

	/**
	 * Applies an operation between two atoms of the same concrete type
	 * The function supposes that both atoms are of the same concrete type
	 * 
	 * @see AtomType
	 *
	 * @param o the operation to apply
	 * @param a the other atom
	 * @return the result of the operation having the same type as a
	 */
	Atom apply(Operation o, Atom a);

	/**
	 * Applies an Binary between two atoms of the same concrete type
	 * The function supposes that both atoms are of the same concrete type
	 * 
	 * @see AtomType
	 *
	 * @param f the binary function to with this atom as first parameter
	 * @param a the other atom
	 * @return the result of the operation having the same type as a
	 */
	Atom apply(BinaryFunction f, Atom a);

	/**
	 * Applies an operation on the atom
	 *
	 * @param f the unary operation to apply
	 * @return the result of the operation
	 */
	Atom apply(UnaryFunction f);

	/**
	 * accepts the specific AtomVisitor
	 *
	 * @param aV The Atomvisitor object being passed as a parameter
	 */
	void accept(AtomVisitor aV);

	Atom toRadian();

}
