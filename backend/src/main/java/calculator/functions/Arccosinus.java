package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic unary operation "acos".
 * The class extends an abstract superclass UnaryFunction.
 */
public final class Arccosinus extends UnaryFunction {

	/**
	 * Class constructor specifying an Expression to apply the arccosine function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Arccosinus(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "acos";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}
		double acosVal = Math.acos(r.getValue().doubleValue());
		if (Double.isNaN(acosVal)) {
			return Real.nan();
		}
		return new Real(new java.math.BigDecimal(acosVal));
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double acosVal = Math.acos(i.getValue());
		return new IntegerAtom((int) Math.round(acosVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().acos();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double val = q.getValue().doubleValue();
		if (val < -1.0 || val > 1.0) {
			throw new ArithmeticException("Domain error: Arccosinus requires input in the range [-1, 1].");
		}
		double acosVal = Math.acos(val);
		return new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(acosVal));
	}
}
