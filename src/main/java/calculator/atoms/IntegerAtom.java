package calculator.atoms;

import calculator.atoms.visitor.AtomVisitor;
import calculator.operations.Operation;
import visitor.Visitor;

/**
 * IntegerAtom is a concrete class that represents arithmetic (integer) numbers,
 * which are Atoms, a special kind of Expressions, just like operations are.
 *
 * @see Expression
 * @see Operation
 */
public class IntegerAtom implements Atom {

	/* The internal integer value */
	private int value;

	/**
	 * Constructor method
	 *
	 * @param i The integer value
	 */
	public IntegerAtom(int i) {
		value = i;
	}

	/**
	 * accept method to implement the visitor design pattern to traverse Atoms.
	 * Each Integer will pass itself to the Atomvisitor object to get processed
	 * by the Atomvisitor.
	 * 
	 * @param v The Atomvisitor object
	 */
	@Override
	public void accept(AtomVisitor v) {
		v.visit(this);
	}

	/**
	 * accept method to implement the visitor design pattern to traverse arithmetic
	 * expressions.
	 * Each Integer number will pass itself to the visitor object to get processed
	 * by the visitor.
	 *
	 * @param v The visitor object
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	/**
	 * Two Integers Atoms are equal if the values they contain are equal
	 *
	 * @param o The object to compare to
	 * @return A boolean representing the result of the equality test
	 */
	@Override
	public boolean equals(Object o) {
		// No object should be equal to null (not including this check can result in an
		// exception if a IntegerAtom is tested against null)
		if (o == null)
			return false;

		// If the object is compared to itself then return true
		if (o == this) {
			return true;
		}

		// If the object is of another type then return false
		if (!(o instanceof IntegerAtom)) {
			return false;
		}
		// return the equality between
		return this.value == ((IntegerAtom) o).getValue();
	}

	/**
	 * The method hashCode needs to be overridden it the equals method is
	 * overridden;
	 * otherwise there may be problems when you use your object in hashed
	 * collections
	 * such as HashMap, HashSet, LinkedHashSet.
	 *
	 * @return The result of computing the hash.
	 */
	@Override
	public int hashCode() {
		return value;
	}

	/**
	 * applies an operation between two IntegerAtom
	 *
	 * @param o the operation to apply
	 * @param a the other IntegerAtom
	 * @return The result of the application of o on a and this instance
	 */
	public IntegerAtom apply(Operation o, Atom a) {
		return o.op(this, (IntegerAtom) a);
	}

	/**
	 * @return the value of the integer
	 */
	public int getValue() {
		return value;
	}
}
