package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic unary operation "sinh".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Sinh extends UnaryFunction {

	/**
	 * Class constructor specifying an Expression to apply the hyperbolic sine
	 * function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Sinh(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "sinh";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}
		double sinVal = Math.sinh(r.getValue().doubleValue());
		return new Real(new java.math.BigDecimal(sinVal));
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double sinVal = Math.sinh(i.getValue());
		return new IntegerAtom((int) Math.round(sinVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().sinh();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double sinVal = Math.sinh(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction.from(sinVal);
		return new Rationnal(result);
	}
}
