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
 * This class represents the square root unary operation "sqrt".
 * The class extends an abstract superclass UnaryFunction.
 */
public final class Sqrt extends UnaryFunction {

	public Sqrt(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "sqrt";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.getValue().doubleValue() < 0) {
			throw new IllegalArgumentException("Square root of negative number");
		}
		if (r.isPlusInf()) {
			return Real.plusInf();
		}
		BigDecimal val = BigDecimalMath.sqrt(r.getValue(), Real.context);
		return new Real(val);
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		if (i.getValue() < 0) {
			throw new IllegalArgumentException("Square root of negative number");
		}
		double sqrtVal = Math.sqrt(i.getValue());
		return new IntegerAtom((int) Math.round(sqrtVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().sqrt();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		if (q.getValue().doubleValue() < 0) {
			throw new IllegalArgumentException("Square root of negative number");
		}
		double sqrtVal = Math.sqrt(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction
				.from(sqrtVal);
		return new Rationnal(result);
	}
}
