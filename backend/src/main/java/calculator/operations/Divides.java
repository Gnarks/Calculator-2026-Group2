package calculator.operations;

import java.math.RoundingMode;
import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic division operation "/".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * 
 * @see Operation
 * @see Minus
 * @see Times
 * @see Plus
 */
public final class Divides extends Operation {

	/**
	 * Class constructor specifying a number of Expressions to divide.
	 *
	 * @param elist The list of Expressions to divide
	 * @throws IllegalConstruction If an empty list of expressions if passed as
	 *                             parameter
	 * @see #Divides(List<Expression>)
	 */
	public Divides(List<Expression> elist) throws IllegalConstruction {
		super(elist);
		symbol = "/";
		neutral = 1;
	}

	/**
	 * The actual computation of the (binary) arithmetic division of two integers
	 * 
	 * @param l The first integer
	 * @param r The second integer that should divide the first
	 * @return The integer that is the result of the division
	 */
	public int op(int l, int r) {
		if (r == 0) {
			throw new ArithmeticException("Division by zero");
		}
		return (l / r);
	}

	/**
	 * The actual computation of the (binary) arithmetic division of two Reals
	 * 
	 * @param r1 The first Real
	 * @param r2 The second Real
	 * @return The (new) Real that is the result of the division
	 */
	public Real op(Real r1, Real r2) {

		if (r1.isNan() || r2.isNan()) { // NaN / x = NaN
			return Real.nan();
		}

		if (r2.getValue().doubleValue() == 0) {
			if (r1.getValue().doubleValue() == 0)
				return Real.nan();
			else if (r1.getValue().doubleValue() > 0)
				return Real.plusInf();
			else if (r1.getValue().doubleValue() < 0)
				return Real.minusInf();
		}

		if ((r1.isPlusInf() && r2.isMinusInf())
				|| (r1.isMinusInf() && r2.isPlusInf())
				|| (r1.isMinusInf() && r2.isMinusInf())
				|| (r1.isPlusInf() && r2.isPlusInf())) { // the case with inf/inf = NaN
			return Real.nan();
		}

		if (r2.isPlusInf() || r2.isMinusInf()) { // x/inf = 0
			return new Real(0);
		}

		if (r1.isPlusInf() || r1.isMinusInf()) { // inf/x = +/- inf
			// int whose sign is the resulting sign of the infinity
			Double sign = r1.getValue().doubleValue() * r2.getValue().doubleValue();
			if (sign > 0)
				return Real.plusInf();
			else
				return Real.minusInf();
		}

		return new Real(r1.getValue().divide(r2.getValue(), RoundingMode.HALF_UP));
	}

	/**
	 * The actual computation of the (binary) arithmetic division of two
	 * Integers
	 * 
	 * @param i1 The first IntegerAtom
	 * @param i2 The second IntegerAtom
	 * @return The (new) IntegerAtom that is the result of the division
	 */
	public IntegerAtom op(IntegerAtom i1, IntegerAtom i2) {
		if (i2.getValue() == 0) {
			throw new ArithmeticException("Division by zero");
		}
		return new IntegerAtom(i1.getValue() / i2.getValue());
	}

	@Override
	public Complex op(Complex c1, Complex c2) {
		if (c2.getValue().equals(org.apache.commons.numbers.complex.Complex.ZERO)) {
			throw new ArithmeticException("Division by zero");
		}
		org.apache.commons.numbers.complex.Complex result = c1.getValue().divide(c2.getValue());
		return new Complex(result);
	}

	@Override
	public Rationnal op(Rationnal q1, Rationnal q2) {
		if (q2.getValue().equals(org.apache.commons.numbers.fraction.Fraction.ZERO)) {
			throw new ArithmeticException("Division by zero");
		}

		org.apache.commons.numbers.fraction.Fraction result = q1.getValue().divide(q2.getValue());
		return new Rationnal(result);
	}
}
