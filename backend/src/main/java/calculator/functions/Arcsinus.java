package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic unary operation "asin".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Arcsinus extends UnaryOperation {

	/**
	 * Class constructor specifying an Expression to apply the arcsine function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Arcsinus(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "asin";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}
		double asinVal = Math.asin(r.getValue().doubleValue());
		if (Double.isNaN(asinVal)) {
			return Real.nan();
		}
		return new Real(new java.math.BigDecimal(asinVal));
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double asinVal = Math.asin(i.getValue());
		return new IntegerAtom((int) Math.round(asinVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().asin();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double val = q.getValue().doubleValue();
		if (val < -1.0 || val > 1.0) {
			throw new ArithmeticException("Domain error: Arcsinus requires input in the range [-1, 1].");
		}
		double asinVal = Math.asin(val);
		return new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(asinVal));
	}
}
