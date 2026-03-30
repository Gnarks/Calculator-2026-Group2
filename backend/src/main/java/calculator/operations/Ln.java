package calculator.operations;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the natural logarithm unary operation "ln".
 * The class extends an abstract superclass UnaryOperation.
 */
public final class Ln extends UnaryOperation {

	public Ln(Expression arg) throws IllegalConstruction {
		super(arg);
		symbol = "ln";
	}

	@Override
	public Real op(Real r) {
		if (r.isNan() || r.isMinusInf() || r.getValue().doubleValue() < 0) {
			throw new IllegalArgumentException("Logarithm of negative number");
		}
		if (r.getValue().doubleValue() == 0) {
			return Real.minusInf();
		}
		if (r.isPlusInf()) {
			return Real.plusInf();
		}
		double lnVal = Math.log(r.getValue().doubleValue());
		return new Real(new java.math.BigDecimal(lnVal));
	}

	@Override
	public IntegerAtom op(IntegerAtom i) {
		if (i.getValue() < 0) {
			throw new IllegalArgumentException("Logarithm of negative number");
		}
		double lnVal = Math.log(i.getValue());
		return new IntegerAtom((int) Math.round(lnVal));
	}

	@Override
	public Complex op(Complex c) {
		org.apache.commons.numbers.complex.Complex val = c.getValue().log();
		return new Complex(val);
	}

	@Override
	public Rationnal op(Rationnal q) {
		if (q.getValue().doubleValue() < 0) {
			throw new IllegalArgumentException("Logarithm of negative number");
		}
		double lnVal = Math.log(q.getValue().doubleValue());
		org.apache.commons.numbers.fraction.Fraction result = org.apache.commons.numbers.fraction.Fraction.from(lnVal);
		return new Rationnal(result);
	}
}
