
package calculator.functions;

import calculator.Expression;
import calculator.atoms.*;
import visitor.Visitor;

public abstract class BinaryFunction implements Expression {

	protected String symbol;
	private Expression firstArg;
	private Expression secondArg;

	public BinaryFunction(Expression firstArg, Expression secondArg) {
		this.firstArg = firstArg;
		this.secondArg = secondArg;
	}

	/**
	 * Abstract method representing the actual function result of
	 * two reals
	 * 
	 * @param r1 first Real of the binary operation
	 * @param r2 second Real of the binary operation
	 * @return result of the function on two Reals
	 */
	public abstract Atom op(Real r1, Real r2);

	/**
	 * Abstract method representing the actual function result of
	 * two complexes
	 * 
	 * @param c1 first Complex of the binary operation
	 * @param c2 second Complex of the binary operation
	 * @return result of the function on two Complexes
	 */
	public abstract Atom op(Complex c1, Complex c2);

	/**
	 * Abstract method representing the actual function result of
	 * two integers
	 * 
	 * @param i1 first Integer of the binary operation
	 * @param i2 second Integer of the binary operation
	 * @return result of the function on two IntegerAtoms
	 */
	public abstract Atom op(IntegerAtom i1, IntegerAtom i2);

	/**
	 * Abstract method representing the actual function result of
	 * two Rationnals
	 * 
	 * @param q1 first Rationnal of the binary operation
	 * @param q2 second Rationnal of the binary operation
	 * @return result of the function on two Rationnals
	 */
	public abstract Atom op(Rationnal q1, Rationnal q2);

	/**
	 * Accept method to implement the visitor design pattern to traverse arithmetic
	 * expressions.
	 *
	 * @param v The visitor object
	 */
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;
		BinaryFunction other = (BinaryFunction) o;
		return this.firstArg.equals(other.getFirstArg()) && this.secondArg.equals(other.getSecondArg());
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Expression getFirstArg() {
		return firstArg;
	}

	public void setFirstArg(Expression firstArg) {
		this.firstArg = firstArg;
	}

	public Expression getSecondArg() {
		return secondArg;
	}

	public void setSecondArg(Expression secondArg) {
		this.secondArg = secondArg;
	}
}
