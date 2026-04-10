package calculator.atoms;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import calculator.functions.BinaryFunction;
import calculator.functions.UnaryFunction;
import visitor.Visitor;
import java.util.Objects;

/**
 * Rationnal Number type is used to represent fractions e.g. 1/2
 * 
 * @see Expression
 * @see Operation
 * @see Atom
 */
public class Rationnal implements Atom {

	private final org.apache.commons.numbers.fraction.Fraction value;

	/**
	 * Constructor method
	 * * @param numerator The numerator
	 * 
	 * @param denominator The denominator
	 */
	public Rationnal(int numerator, int denominator) {
		try {
			this.value = org.apache.commons.numbers.fraction.Fraction.of(numerator, denominator);
		} catch (ArithmeticException e) {
			throw new IllegalArgumentException("Denominator cannot be zero", e);
		}
	}

	/**
	 * Constructor method for a whole number
	 * * @param value The integer value (e.g., 5 becomes 5/1)
	 */
	public Rationnal(int value) {
		this.value = org.apache.commons.numbers.fraction.Fraction.of(value);
	}

	/**
	 * Constructor method from an existing Fraction object
	 * * @param value The commons-numbers Fraction object
	 */
	public Rationnal(org.apache.commons.numbers.fraction.Fraction value) {
		this.value = value;
	}

	/**
	 * getter method to obtain the value contained in the object
	 *
	 * @return The commons-numbers Fraction number contained in the object
	 */
	public org.apache.commons.numbers.fraction.Fraction getValue() {
		return value;
	}

	@Override
        /**
         * applies an operation between two Rationnal
         *
         * @param o the operation to apply
         * @param a the other Rationnal
         * @return The result of the application of o on this instance and a
         */
	public Atom apply(Operation o, Atom a) {
		return o.op(this, (Rationnal) a);
	}

	@Override
        /**
         * applies a binary function between two Rationnal
         *
         * @param f the binary function to apply
         * @param a the other Rationnal
         * @return The result of the application of f on this instance and a
         */
	public Atom apply(BinaryFunction f, Atom a) {
		return f.op(this, (Rationnal) a);
	}

	@Override
        /**
         * applies a unary operation to the Rationnal
         *
         * @param o the operation to apply
         * @return The result of the application
         */
	public Rationnal apply(UnaryFunction o) {
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
         * Checks equality based on the fraction value.
         */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (!(o instanceof Rationnal))
			return false;
		Rationnal other = (Rationnal) o;
		return this.value.equals(other.getValue());
	}

	@Override
        /**
         * Generates the hash code based on the inner fraction value.
         */
	public int hashCode() {
		return Objects.hash(value);
	}

        /**
         * @return the numerator of the fraction
         */
	public int getNumerator() {
		return value.getNumerator();
	}

        /**
         * @return the denominator of the fraction
         */
	public int getDenominator() {
		return value.getDenominator();
	}

	@Override
        /**
         * @return the string representation of the fraction without spaces
         */
	public String toString(){ return value.toString().replaceAll("\\s",""); }

	@Override
        /**
         * Converts the fraction value to a Real representing radians
         *
         * @return a Real number of the converted value
         */
	public Atom toRadian() { return new Real(Math.toRadians(value.doubleValue())); }

}
