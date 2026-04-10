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
 * This class represents the arithmetic unary operation "atan".
 * The class extends an abstract superclass UnaryFunction.
 */
public final class Arctangente extends UnaryFunction {

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
			return new Real(BigDecimal.valueOf(Math.PI / 2));
		}
		if (r.isMinusInf()) {
			return new Real(BigDecimal.valueOf(-Math.PI / 2));
		}

		BigDecimal val = BigDecimalMath.atan(r.getValue(), Real.context);
		return new Real(val);
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
