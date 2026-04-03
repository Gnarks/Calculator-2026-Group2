
package calculator.atoms;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import calculator.functions.BinaryFunction;
import calculator.functions.UnaryFunction;
import visitor.Visitor;

/**
 * Real is a concrete class that represents arithmetic (Real) numbers,
 * which are Atoms, a special kind of Expressions.
 *
 * @see Expression
 * @see Operation
 */
public class Real implements Atom {

	public static int scale = 64;
	public static MathContext context = new MathContext(scale + 1, RoundingMode.HALF_EVEN);

	/*
	 * the actual Real value
	 * in case of a special real value it is used to determine which special real
	 * it represents
	 */
	private final BigDecimal value;

	/*
	 * A boolean flag used to know if the Real is a special value
	 * if the flag is true, there is 3 possibilities :
	 * value = 1 => the Real represents +\inf
	 * value = -1 => the Real represents -\inf
	 * value = 0 => the Real represents a NaN
	 */
	private boolean special = false;

	/**
	 * getter method to obtain the value contained in the object
	 *
	 * @return The integer number contained in the object
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Constructor method for a string
	 *
	 * @param s The string to be represented in the Real
	 */
	public /* constructor */ Real(String s) {
		BigDecimal temp = new BigDecimal(s, context);
		value = temp.setScale(Real.scale, RoundingMode.HALF_EVEN);
	}

	/**
	 * Constructor method for a BigDecimal
	 *
	 * @param d the BigDecimal to be represented in the Real
	 */
	public /* constructor */ Real(BigDecimal d) {
		value = d.setScale(Real.scale, RoundingMode.HALF_EVEN);
	}

	/**
	 * Constructor method for a double
	 *
	 * @param d The double to be represented in the Real
	 */
	public /* constructor */ Real(double d) {
		BigDecimal temp = BigDecimal.valueOf(d);
		value = temp.setScale(Real.scale, RoundingMode.HALF_EVEN);
	}

	/**
	 * Constructor method
	 *
	 * @param i The int value to be contained in the object
	 */
	public /* constructor */ Real(int i) {
		BigDecimal temp = BigDecimal.valueOf(i);
		value = temp.setScale(Real.scale, RoundingMode.HALF_EVEN);
	}

	/**
	 * Constructor for special Reals
	 *
	 * @param v either 1
	 * @throws IllegalConstruction
	 */
	private /* constructor */ Real(boolean s, int v) {
		BigDecimal temp = BigDecimal.valueOf(v);
		value = temp.setScale(Real.scale, RoundingMode.HALF_EVEN);
		special = s;
	}

	/**
	 * Returns a NaN Real
	 * 
	 * @return A new Real number representing a NaN
	 */
	public static Real nan() {
		return new Real(true, 0);
	};

	/**
	 * Returns a Real representing +infinity
	 * 
	 * @return A new Real number representing +infinity
	 */
	public static Real plusInf() {
		return new Real(true, 1);
	};

	/**
	 * Returns a Real representing -infinity
	 * 
	 * @return A new Real number representing -infinity
	 */
	public static Real minusInf() {
		return new Real(true, -1);
	};

	/**
	 * Returns if the Real is representing -infinity
	 * 
	 * @return if the Real is representing -infinity
	 */
	public boolean isMinusInf() {
		return special && value.doubleValue() == -1;
	}

	/**
	 * Returns if the Real is representing +infinity
	 * 
	 * @return if the Real is representing +infinity
	 */
	public boolean isPlusInf() {
		return special && value.doubleValue() == 1;
	}

	/**
	 * Returns if the Real is representing NaN
	 * 
	 * @return if the Real is representing NaN
	 */
	public boolean isNan() {
		return special && value.doubleValue() == 0;
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
		return this.value.equals(((Real) o).getValue()) && special == ((Real) o).special;
		// return this.value.compareTo(((Real) o).value) == 0;
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

	@Override
	public Atom apply(BinaryFunction f, Atom a) {
		return f.op(this, (Real) a);
	}

	@Override
	public Real apply(UnaryFunction o) {
		return o.op(this);
	}

	@Override
	public String toString() { return value.stripTrailingZeros().toPlainString(); }
}
