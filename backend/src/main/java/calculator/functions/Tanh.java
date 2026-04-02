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
 * This class represents the arithmetic unary operation "tanh".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Tanh extends UnaryFunction {

	/**
	 * Class constructor specifying an Expression to apply the hyperbolic tangent
	 * function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Tanh(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "tanh";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}
		BigDecimal val = BigDecimalMath.tanh(r.getValue(), Real.context);
		return new Real(val);
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double tanVal = Math.tanh(i.getValue());
		return new IntegerAtom((int) Math.round(tanVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().tanh();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double tanVal = Math.tanh(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction.from(tanVal);
		return new Rationnal(result);
	}
}
