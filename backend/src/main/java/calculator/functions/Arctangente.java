package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic unary operation "atan".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Arctangente extends UnaryOperation {

	/**
	 * Class constructor specifying an Expression to apply the arctangent function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Arctangente(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "atan";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan()) {
			return Real.nan();
		}
		if (r.isPlusInf()) {
			return new Real(new java.math.BigDecimal(Math.PI / 2));
		}
		if (r.isMinusInf()) {
			return new Real(new java.math.BigDecimal(-Math.PI / 2));
		}
		double atanVal = Math.atan(r.getValue().doubleValue());
		return new Real(new java.math.BigDecimal(atanVal));
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double atanVal = Math.atan(i.getValue());
		return new IntegerAtom((int) Math.round(atanVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().atan();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double atanVal = Math.atan(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction
				.from(atanVal);
		return new Rationnal(result);
	}
}
