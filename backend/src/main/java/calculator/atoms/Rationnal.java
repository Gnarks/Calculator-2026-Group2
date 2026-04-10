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
	public Atom apply(Operation o, Atom a) {
		return o.op(this, (Rationnal) a);
	}

	@Override
	public Atom apply(BinaryFunction f, Atom a) {
		return f.op(this, (Rationnal) a);
	}

	@Override
	public Rationnal apply(UnaryFunction o) {
		return o.op(this);
	}

	@Override
	public void accept(AtomVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
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
	public int hashCode() {
		return Objects.hash(value);
	}

	public int getNumerator() {
		return value.getNumerator();
	}

	public int getDenominator() {
		return value.getDenominator();
	}

	@Override
	public String toString(){ return value.toString().replaceAll("\\s",""); }

	@Override
	public Atom toRadian() { return new Real(Math.toRadians(value.doubleValue())); }

}
