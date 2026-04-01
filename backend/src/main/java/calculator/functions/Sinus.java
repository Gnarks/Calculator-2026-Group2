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
 * This class represents the arithmetic unary operation "sin".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Sinus extends UnaryFunction {

	/**
	 * Class constructor specifying an Expression to apply the sine function.
	 *
	 * @param arg The Expression to apply the operation on
	 * @throws IllegalConstruction If a null argument is passed
	 */
	public Sinus(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "sin";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.isPlusInf()) {
			return Real.nan();
		}
		BigDecimal val = BigDecimalMath.sin(r.getValue(), Real.context);
		return new Real(val);
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		double sinVal = Math.sin(i.getValue());
		return new IntegerAtom((int) Math.round(sinVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().sin();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		double sinVal = Math.sin(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction.from(sinVal);
		return new Rationnal(result);
	}
}
