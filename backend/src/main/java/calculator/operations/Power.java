package calculator.operations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.*;
import calculator.atoms.visitor.AtomCaster;
import ch.obermuhlner.math.big.BigDecimalMath;

import org.apache.commons.numbers.fraction.Fraction;

public final class Power extends Operation {

	public Power(List<Expression> elist) throws IllegalConstruction {
		super(elist);
		symbol = "**";
		neutral = 1;
	}

	/**
	 * The actual exponentiation of two Reals
	 *
	 * @param r1 the base
	 * @param r2 the exponent
	 * @return the result of the operation
	 */
	@Override
	public Real op(Real r1, Real r2) {

		if (r1.isNan() || r2.isNan())
			return Real.nan();

		BigDecimal base = r1.getValue();
		BigDecimal exponent = r2.getValue();

		// case b^-1 with b=0 or neg
		if (base.compareTo(new BigDecimal(0)) == 0 && exponent.compareTo(new BigDecimal(0)) <= 0) {
			return Real.nan();
		}

		if (r2.isPlusInf()) {
			if (r1.isMinusInf())
				return Real.nan();
			else if (base.compareTo(new BigDecimal(0)) > 0 && base.compareTo(new BigDecimal(1)) < 0)
				return new Real(0);

			else if (base.compareTo(new BigDecimal(0)) == 0 || base.compareTo(new BigDecimal(1)) == 0)
				return Real.nan();

			return Real.plusInf();
		}

		if (r2.isMinusInf()) {
			if (r1.isMinusInf())
				return Real.nan();
			if (base.compareTo(new BigDecimal(0)) > 0 && base.compareTo(new BigDecimal(1)) < 0)
				return Real.plusInf();
			return new Real(0);
		}

		// if negative base, result undefined is irrational exponent or rational
		// exponent with even denominator

		if (base.compareTo(new BigDecimal(0)) < 0) {

			BigInteger[] expFrac = toRational(exponent);
			BigDecimal numerator = new BigDecimal(expFrac[0]);
			BigDecimal denominator = new BigDecimal(expFrac[1]);

			if (expFrac[1].mod(new BigInteger("2")).equals(BigInteger.ONE)) {

				BigDecimal root = BigDecimalMath.pow(base.abs(), BigDecimal.ONE.divide(denominator), Real.context)
						.negate();
				BigDecimal powed = BigDecimalMath.pow(root, numerator, Real.context);
				return new Real(powed);
			}

			// is the expo in not an integer
			if (r2.getValue().stripTrailingZeros().scale() > 0)
				return Real.nan();
		}
		BigDecimal powed = BigDecimalMath.pow(base, exponent, Real.context);
		return new Real(powed);
	}

	public static BigInteger[] toRational(BigDecimal decimal) {
		int scale = decimal.scale();
		if (scale <= 0) {
			return new BigInteger[] { decimal.toBigInteger(), BigInteger.ONE };
		} else {
			BigInteger denominator = BigInteger.TEN.pow(scale);
			BigInteger numerator = decimal.unscaledValue();
			BigInteger d = numerator.gcd(denominator);
			return new BigInteger[] { numerator.divide(d), denominator.divide(d) };
		}
	}

	/**
	 * The actual exponentiation of two Integers.
	 *
	 * @param i1 the base
	 * @param i2 the exponent
	 * @return the result of the operation
	 */
	@Override
	public Real op(IntegerAtom i1, IntegerAtom i2) {
		int base = i1.getValue();
		int exp = i2.getValue();
		if (base == 0 && exp < 0) {
			return Real.nan();
		}
		try {
			if (exp < 0) {
				long denom = Math.powExact(base, -exp);
				return new Real(1.0 / denom);
			} else {
				return new Real(Math.powExact(base, exp));
			}
		} catch (ArithmeticException e) {
			throw new ArithmeticException("Overflow");
		}
	}

	/**
	 * The exponentiation of complex numbers
	 *
	 * @param c1 the base
	 * @param c2 the exponent
	 * @throws ArithmeticException since the exponentiation of complex numbers is
	 *                             impossible
	 */
	@Override
	public Complex op(Complex c1, Complex c2) {
		throw new ArithmeticException("The exponentiation of two complex numbers is impossible");
	}

	/**
	 * The actual exponentiation of two rational numbers
	 *
	 * @param q1 the base
	 * @param q2 the exponent
	 * @return the result of the exponentiation
	 */
	@Override
	public Real op(Rationnal q1, Rationnal q2) {
		AtomCaster aC = new AtomCaster(AtomType.REAL);
		aC.visit(q1);
		Real r1 = (Real) aC.getResult();
		aC.visit(q2);
		Real r2 = (Real) aC.getResult();
		return op(r1, r2);
	}
}
