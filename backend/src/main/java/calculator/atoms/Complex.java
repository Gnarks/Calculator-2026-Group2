package calculator.atoms;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import visitor.Visitor;
import java.util.Objects;
import calculator.functions.*;

/**
 * Complex Number type is used to represent complex e.g. 3 + 2i
 * 
 * @see Expression
 * @see Operation
 * @see Atom
 */
public class Complex implements Atom {

	private final org.apache.commons.numbers.complex.Complex value;

	/**
	 * Constructor method
	 * 
	 * @param real      The real part
	 * @param imaginary The imaginary part
	 */
	public Complex(double real, double imaginary) {
		this.value = org.apache.commons.numbers.complex.Complex.ofCartesian(real, imaginary);
	}

	/**
	 * Constructor method from an existing Complex object
	 * 
	 * @param value The commons-numbers Complex object
	 */
	public Complex(org.apache.commons.numbers.complex.Complex value) {
		this.value = value;
	}

	/**
	 * Returns a Complex representing a NaN
	 *
	 * @return a NaN Complex
	 */
	public static Complex nan() {
		return new Complex(org.apache.commons.numbers.complex.Complex.ofCartesian(Double.NaN, Double.NaN));
	}

	/**
	 * Returns if a complex is a NaN
	 *
	 * @return true iff the complex represents a NaN
	 */
	public boolean isNaN() {
		return value.isNaN();

	}

	/**
	 * getter method to obtain the value contained in the object
	 *
	 * @return The commons-numbers Complex number contained in the object
	 */
	public org.apache.commons.numbers.complex.Complex getValue() {
		return value;
	}

	@Override
        /**
         * applies an operation between two Complex
         *
         * @param o the operation to apply
         * @param a the other Complex
         * @return The result of the application of o on this instance and a
         */
	public Complex apply(Operation o, Atom a) {
		return o.op(this, (Complex) a);
	}

	@Override
        /**
         * applies a binary function between two Complex
         *
         * @param f the binary function to apply
         * @param a the other Complex
         * @return The result of the application of f on this instance and a
         */
	public Atom apply(BinaryFunction f, Atom a) {
		return f.op(this, (Complex) a);
	}

	@Override
        /**
         * applies a unary operation to the Complex
         *
         * @param o the operation to apply
         * @return The result of the application
         */
	public Complex apply(UnaryFunction o) {
		return o.op(this);
	}

	@Override
        /**
         * accept method to implement the visitor design pattern to traverse Atoms.
         *
         * @param v The Atomvisitor object
         */
	public void accept(AtomVisitor v) {
		v.visit(this);
	}

	@Override
        /**
         * accept method to implement the visitor design pattern to traverse arithmetic expressions.
         *
         * @param v The visitor object
         */
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
        /**
         * Checks equality based on the complex value.
         */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (!(o instanceof Complex))
			return false;
		Complex other = (Complex) o;
		return this.value.equals(other.getValue());
	}

	@Override
        /**
         * Generates the hash code based on the inner complex value.
         */
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
        /**
         * Returns the string representation of this Complex number.
         */
	public String toString() {
		double realPart = value.getReal();
		double imaginaryPart = value.getImaginary();

		if (realPart == 0 && imaginaryPart == 0)
			return "0";

		if (realPart == 0)
			return imaginaryPart + "i";

		if (imaginaryPart == 0)
			return String.valueOf(realPart);

		if (imaginaryPart > 0) {
			return realPart + " + " + imaginaryPart + "i";
		} else {
			return realPart + " - " + Math.abs(imaginaryPart) + "i";
		}
	}

	@Override
        /**
         * Does nothing for Complex numbers (returns this).
         */
	public Atom toRadian() {
		return this;
	}
}
