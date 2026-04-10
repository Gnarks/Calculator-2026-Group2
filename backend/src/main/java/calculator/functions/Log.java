package calculator.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.*;
import calculator.operations.Divides;
import ch.obermuhlner.math.big.BigDecimalMath;

/**
 * This class represents the arithmetic binary operation "log(x, base)".
 */
public class Log extends BinaryFunction {

	public Log(Expression base, Expression number) throws IllegalConstruction {
		super(base, number);
		symbol = "log";
	}

	@Override
	public Real op(Real base, Real n) {

		if (base.isNan() || n.isNan()) { // NaN / x = NaN
			return Real.nan();
		}

		if (n.isMinusInf() || n.getValue().doubleValue() < 0
				|| base.isMinusInf() || base.getValue().doubleValue() < 0) {
			throw new IllegalArgumentException("Logarithm of negative number");
		}

		int precision = 0;
		if (base.getValue().precision() > n.getValue().precision())
			precision = base.getValue().precision();
		else
			precision = n.getValue().precision();

		Real logBase;
		Real logN;
		if (base.isPlusInf()) {
			logBase = Real.plusInf();
		} else {
			logBase = new Real(BigDecimalMath.log(base.getValue(), new MathContext(precision)));
		}

		if (n.isPlusInf()) {
			logN = Real.plusInf();
		} else {
			logN = new Real(BigDecimalMath.log(n.getValue(), new MathContext(precision)));
		}

		// uses the log_b(n) = log_e(n)/log_e(b)

		Divides d;
		try {
			d = new Divides(new ArrayList<>());

			return d.op(logN, logBase);
		} catch (IllegalConstruction e) {
			throw new IllegalStateException("Failed to construct Divides operation", e);
		}

	}

	@Override
	public Complex op(Complex base, Complex n) {

		// uses the log_b(n) = log_e(n)/log_e(b)

		org.apache.commons.numbers.complex.Complex logBase = base.getValue().log();
		org.apache.commons.numbers.complex.Complex logN = n.getValue().log();

		return new Complex(logN.divide(logBase));

	}

	@Override
	public Real op(IntegerAtom base, IntegerAtom n) {
		// uses the log_b(n) = log_e(n)/log_e(b)

		if (base.getValue() <= 0 || n.getValue() <= 0) {
			throw new IllegalArgumentException("Logarithm of negative number");
		}

		BigDecimal logBase = BigDecimalMath.log(new BigDecimal(base.getValue()), Real.context);
		BigDecimal logN = BigDecimalMath.log(new BigDecimal(n.getValue()), Real.context);

		return new Real(logN.divide(logBase, Real.context));

	}

	@Override
	public Real op(Rationnal base, Rationnal n) {

		if (base.getValue().doubleValue() <= 0 || n.getValue().doubleValue() <= 0) {
			throw new IllegalArgumentException("Logarithm of negative number");
		}

		// uses the log_b(n) = log_e(n)/log_e(b)
        BigDecimal logBase = BigDecimalMath.log(BigDecimal.valueOf(base.getValue().doubleValue()), Real.context);
        BigDecimal logN = BigDecimalMath.log(BigDecimal.valueOf(n.getValue().doubleValue()), Real.context);
		return new Real(logN.divide(logBase, Real.context));
	}
}
