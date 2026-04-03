package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.Notation;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import visitor.Printer;
import visitor.Visitor;

/**
 * UnaryFunction is an abstract class that represents unary arithmetic
 * operations,
 * which are a special kind of Expressions, just like binary operations are.
 */
public abstract class UnaryFunction implements Expression {
	/**
	 * The expression passed as an argument to the arithmetic operation
	 */
	public Expression arg;

	/**
	 * The character/string used to represent the arithmetic operation (e.g. "cos")
	 */
	protected String symbol;

	/**
	 * To construct a unary operation with an expression as argument
	 *
	 * @param arg The expression passed as argument to the arithmetic operation
	 * @throws IllegalConstruction Exception thrown if a null argument is passed
	 */
	protected /* constructor */ UnaryFunction(Expression arg)
			throws IllegalConstruction {
		if (arg == null) {
			throw new IllegalConstruction();
		} else {
			this.arg = arg;
		}
	}

	/**
	 * getter method to return the argument of an arithmetic operation.
	 *
	 * @return The argument of the arithmetic operation.
	 */
	public Expression getArg() {
		return arg;
	}

	/**
	 * getter method to return the symbol of the arithmetic operation.
	 *
	 * @return The symbol of the arithmetic operation.
	 */
	public String getSymbol() {
		return symbol;
	}

	public abstract Real op(Real r);

	public abstract Complex op(Complex c);

	public abstract IntegerAtom op(IntegerAtom i);

	public abstract Rationnal op(Rationnal q);

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
	public final String toString() {
		Printer p = new Printer();
		this.accept(p);
		return p.getResult();
	}

	public final String toString(Notation n) {
		Printer p = new Printer(n);
		this.accept(p);
		return p.getResult();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (getClass() != o.getClass())
			return false;
		UnaryFunction other = (UnaryFunction) o;
		return this.arg.equals(other.getArg());
	}

	@Override
	public int hashCode() {
		return arg.hashCode();
	}
}
