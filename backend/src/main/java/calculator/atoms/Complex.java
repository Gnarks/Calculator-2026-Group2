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
	public Complex apply(Operation o, Atom a) {
		return o.op(this, (Complex) a);
	}

	@Override
	public Atom apply(BinaryFunction f, Atom a) {
		return f.op(this, (Complex) a);
	}

	@Override
	public Complex apply(UnaryFunction o) {
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
		if (!(o instanceof Complex))
			return false;
		Complex other = (Complex) o;
		return this.value.equals(other.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {return value.toString(); }

}
