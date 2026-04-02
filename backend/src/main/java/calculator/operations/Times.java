package calculator.operations;

import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic multiplication operation "*".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * 
 * @see Operation
 * @see Minus
 * @see Plus
 * @see Divides
 */
public final class Times extends Operation {
	/**
	 * Class constructor specifying a number of Expressions to multiply.
	 *
	 * @param elist The list of Expressions to multiply
	 * @throws IllegalConstruction If an empty list of expressions if passed as
	 *                             parameter
	 * @see #Times(List<Expression>)
	 */
	public Times(List<Expression> elist) throws IllegalConstruction {
		super(elist);
		symbol = "*";
		neutral = 1;
	}

	/**
	 * The actual computation of the (binary) arithmetic multiplication of two Reals
	 * 
	 * @param r1 The first Real
	 * @param r2 The second Real
	 * @return The (new) Real that is the result of the multiplication
	 */
	public Real op(Real r1, Real r2) {

		if (r1.isNan() || r2.isNan()) // NaN * x = NaN
			return Real.nan();

		// case of inf * 0 = NaN
		if (r1.getValue().doubleValue() == 0 && (r2.isPlusInf() || r2.isMinusInf()))
			return Real.nan();

		if (r2.getValue().doubleValue() == 0 && (r1.isPlusInf() || r1.isMinusInf()))
			return Real.nan();

		if (r1.isPlusInf() || r1.isMinusInf() || r2.isPlusInf() || r2.isMinusInf()) { // inf/x = +/- inf
			// int whose sign is the resulting sign of the infinity
			Double sign = r1.getValue().doubleValue() * r2.getValue().doubleValue();
			if (sign > 0)
				return Real.plusInf();
			else
				return Real.minusInf();
		}
		return new Real(r1.getValue().multiply(r2.getValue()));
	}

	/**
	 * The actual computation of the (binary) arithmetic multiplication of two
	 * Integers
	 * 
	 * @param i1 The first IntegerAtom
	 * @param i2 The second IntegerAtom
	 * @return The (new) IntegerAtom that is the result of the multiplication
	 */
	public IntegerAtom op(IntegerAtom i1, IntegerAtom i2) {
		return new IntegerAtom(i1.getValue() * i2.getValue());
	}

	@Override
	public Complex op(Complex c1, Complex c2) {
		if (c1.isNaN() || c2.isNaN()) {
			return Complex.nan();
		}
		org.apache.commons.numbers.complex.Complex result = c1.getValue().multiply(c2.getValue());
		return new Complex(result);
	}

	@Override
	public Rationnal op(Rationnal q1, Rationnal q2) {
		org.apache.commons.numbers.fraction.Fraction result = q1.getValue().multiply(q2.getValue());
		return new Rationnal(result);
	}
}
