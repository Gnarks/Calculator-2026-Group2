package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic unary operation "cos".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Cosinus extends UnaryFunction {

	/**
	 * Class constructor specifying an Expression to apply the cosine function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Cosinus(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "cos";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}
		double cosVal = Math.cos(r.getValue().doubleValue());
		return new Real(new java.math.BigDecimal(cosVal));
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double cosVal = Math.cos(i.getValue());
		return new IntegerAtom((int) Math.round(cosVal)); // Throw an error instead?
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().cos();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double cosVal = Math.cos(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction.from(cosVal);
		return new Rationnal(result);
	}
}
