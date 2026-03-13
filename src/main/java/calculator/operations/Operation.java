package calculator.operations;

import java.util.ArrayList;
import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.Notation;
import calculator.atoms.Complex;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import visitor.Printer;
import visitor.Visitor;

/**
 * Operation is an abstract class that represents arithmetic operations,
 * which are a special kind of Expressions, just like numbers are.
 *
 * @see Expression
 * @see MyNumber
 */
public abstract class Operation implements Expression {
	/**
	 * The list of expressions passed as an argument to the arithmetic operation
	 */
	public List<Expression> args;

	/**
	 * The character used to represent the arithmetic operation (e.g. "+", "*")
	 */
	protected String symbol;

	/**
	 * The neutral element of the operation (e.g. 1 for *, 0 for +)
	 */
	protected int neutral;

	/**
	 * To construct an operation with a list of expressions as arguments,
	 * as well as the Notation used to represent the operation.
	 *
	 * @param elist The list of expressions passed as argument to the arithmetic
	 *              operation
	 * @throws IllegalConstruction Exception thrown if a null list of expressions is
	 *                             passed as argument
	 */
	protected /* constructor */ Operation(List<Expression> elist)
			throws IllegalConstruction {
		if (elist == null) {
			throw new IllegalConstruction();
		} else {
			args = new ArrayList<>(elist);
		}
	}

	/**
	 * getter method to return the number of arguments of an arithmetic operation.
	 *
	 * @return The number of arguments of the arithmetic operation.
	 */
	public List<Expression> getArgs() {
		return args;
	}

	/**
	 * getter method to return the symbol of the arithmetic operation.
	 *
	 * @return The symbol of the arithmetic operation (e.g. "+", "-", "*", "/").
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Abstract method representing the actual binary arithmetic operation to
	 * compute on Reals
	 * 
	 * @param r1 first Real of the binary operation
	 * @param r2 second Real of the binary operation
	 * @return result of computing the binary operation
	 */
	public abstract Real op(Real r1, Real r2);

	/**
	 * Abstract method representing the actual binary arithmetic operation to
	 * compute on Complexes
	 * 
	 * @param c1 first Complex of the binary operation
	 * @param c2 second Complex of the binary operation
	 * @return result of computing the binary operation
	 */
	public abstract Real op(Complex c1, Complex c2);

	/**
	 * Abstract method representing the actual binary arithmetic operation to
	 * compute on Rationnals
	 * 
	 * @param q1 first Rationnal of the binary operation
	 * @param q2 second Rationnal of the binary operation
	 * @return result of computing the binary operation
	 */
	public abstract Real op(Rationnal q1, Rationnal q2);

	/**
	 * Add more parameters to the existing list of parameters
	 *
	 * @param params The list of parameters to be added
	 */
	public void addMoreParams(List<Expression> params) {
		args.addAll(params);
	}

	/**
	 * Accept method to implement the visitor design pattern to traverse arithmetic
	 * expressions.
	 * Each operation will delegate the visitor to each of its arguments
	 * expressions,
	 * and will then pass itself to the visitor object to get processed by the
	 * visitor object.
	 *
	 * @param v The visitor object
	 */
	public void accept(Visitor v) {
		v.visit(this);
	}

	/**
	 * Convert the arithmetic operation into a String to allow it to be printed,
	 * using the default printer
	 *
	 * @return The String that is the result of the conversion.
	 */
	@Override
	public final String toString() {
		Printer p = new Printer();
		this.accept(p);
		return p.getResult();
	}

	/**
	 * Appeal to the visitor to convert the arithmetic operation into a String to
	 * allow it to be printed,
	 * using the notation n (prefix, infix or postfix) that is specified as a
	 * parameter.
	 *
	 * @param n The notation to be used for representing the operation (prefix,
	 *          infix or postfix)
	 * @return The String that is the result of the conversion.
	 */
	public final String toString(Notation n) {
		Printer p = new Printer(n);
		this.accept(p);
		return p.getResult();
	}

	/**
	 * Two operation objects are equal if their list of arguments is equal and they
	 * correspond to the same operation.
	 *
	 * @param o The object to compare with
	 * @return The result of the equality comparison
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false; // No object should be equal to null

		if (this == o)
			return true; // If it's the same object, they're obviously equal

		if (getClass() != o.getClass())
			return false; // getClass() instead of instanceof() because an addition is not the same as a
										// multiplication

		Operation other = (Operation) o;
		return this.args.equals(other.getArgs());
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
		int result = 5;
		int prime = 31;
		result = prime * result + neutral;
		result = prime * result + symbol.hashCode();
		result = prime * result + args.hashCode();
		return result;
	}

}
