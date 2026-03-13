
package calculator.atoms;

import java.math.BigDecimal;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import visitor.Visitor;

/**
 * Real is a concrete class that represents arithmetic (Real) numbers,
 * which are Atoms, a special kind of Expressions.
 *
 * @see Expression
 * @see Operation
 */
public class Real implements Atom {
	private final BigDecimal value;

	/**
	 * getter method to obtain the value contained in the object
	 *
	 * @return The integer number contained in the object
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Constructor method
	 *
	 * @param v The BigDecimal value to be contained in the object
	 */
	public /* constructor */ Real(BigDecimal v) {
		value = v;
	}

	/**
	 * Constructor method
	 *
	 * @param v The double value to be contained in the object
	 */
	public /* constructor */ Real(double v) {
		value = new BigDecimal(v);
	}

	/**
	 * Constructor method
	 *
	 * @param v The int value to be contained in the object
	 */
	public /* constructor */ Real(int v) {
		value = new BigDecimal(v);
	}

	/**
	 * Constructor method
	 *
	 * @param v The float value to be contained in the object
	 */
	public /* constructor */ Real(float v) {
		value = new BigDecimal(v);
	}

	/**
	 * accept method to implement the visitor design pattern to traverse arithmetic
	 * expressions.
	 * Each real number will pass itself to the Atomvisitor object to get processed
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
	 * Each real number will pass itself to the visitor object to get processed by
	 * the
	 * visitor.
	 *
	 * @param v The visitor object
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	/**
	 * Two Real expressions are equal if the values they contain are equal
	 *
	 * @param o The object to compare to
	 * @return A boolean representing the result of the equality test
	 */
	@Override
	public boolean equals(Object o) {
		// No object should be equal to null (not including this check can result in an
		// exception if a Real is tested against null)
		if (o == null)
			return false;

		// If the object is compared to itself then return true
		if (o == this) {
			return true;
		}

		// If the object is of another type then return false
		if (!(o instanceof Real)) {
			return false;
		}
		// return the equality between BigDecimals
		return this.value.equals(((Real) o).value);
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
		return value.intValue();
	}

	/**
	 * applies an operation between two Reals
	 *
	 * @param o the operation to apply
	 * @param a the other Real
	 * @return The result of the application of o on a and this instance
	 */
	@Override
	public Real apply(Operation o, Atom a) {
		return o.op(this, (Real) a);
	}
}
