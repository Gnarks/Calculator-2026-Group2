package calculator.functions;

import java.math.BigDecimal;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import ch.obermuhlner.math.big.BigDecimalMath;

/**
 * This class represents the arithmetic unary operation "cosh".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Cosh extends UnaryFunction {

	/**
	 * Class constructor specifying an Expression to apply the hyperbolic cosine
	 * function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Cosh(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "cosh";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}

		BigDecimal val = BigDecimalMath.cosh(r.getValue(), Real.context);
		return new Real(val);
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double sinVal = Math.cosh(i.getValue());
		return new IntegerAtom((int) Math.round(sinVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().cosh();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double cosVal = Math.cosh(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction.from(cosVal);
		return new Rationnal(result);
	}
}
