package calculator.operations;

import java.math.BigDecimal;
import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.*;
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
        double base = r1.getValue().doubleValue();
        double exp = r2.getValue().doubleValue();

        if (r1.isNan() || r2.isNan())
            return Real.nan();

        if(base == 0) {
            if(exp < 0) {
                return Real.plusInf();
            } else if(exp == 0) {
                return Real.nan();
            } else {
                return new Real(0);
            }
        }

        if(r2.isPlusInf()) {
            if(r1.isMinusInf())
                return Real.nan();
            if(0 < base && base < 1)
                return new Real(0);
            return Real.plusInf();
        }

        if(r2.isMinusInf()) {
            if(0 < base && base < 1)
                return Real.plusInf();
            return new Real(0);
        }

        // if negative base, result undefined is irrational exponent or rational exponent with even denominator
        if(base < 0) {
            if(Fraction.from(exp).getDenominator() %2 == 1)
                return new Real(computePowerRationalWithOddDenom(base, exp));
            if(!isResultDefined(r2))
                return Real.nan();
        }

        if(base == 1)
            return new Real(1);

        return new Real(Math.pow(base, exp));
    }

    /**
     * Checks if the result of an exponentiation between two reals is defined when the base equals zero.
     * It is defined if the exponent is an integer or a rational with even base.
     *
     * @param r the exponent
     * @return true if the result is defined
     */
    private boolean isResultDefined(Real r) {
        double exp = r.getValue().doubleValue();
        Fraction expFrac = Fraction.from(exp);
        return (isIntegerValue(r.getValue()) || expFrac.getDenominator() %2 == 1);
    }

    /**
     * Checks if a BigDecimal is an integer.
     *
     * @param bd the BigDecimal we want to check
     * @return true if the BigDecimal is an integer
     */
    private boolean isIntegerValue(BigDecimal bd) {
        return bd.stripTrailingZeros().scale() <= 0;
    }

    /**
     * Computes an exponentiation with the form a**(p/q) = (a**(1/q))**p
     *
     * @param base the base
     * @param exp the exponent
     * @return the result of the exponentiation
     */
    private double computePowerRationalWithOddDenom(double base, double exp) {
        Fraction expFrac = Fraction.from(exp);
        int num = expFrac.getNumerator();
        int denom = expFrac.getDenominator();

        double root = -(Math.pow(Math.abs(base), 1.0 / denom));
        return Math.pow(root, num);
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
     * @throws ArithmeticException since the exponentiation of complex numbers is impossible
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
        double base = q1.getValue().doubleValue();
        double exp = q2.getValue().doubleValue();

        if(base < 0 && q2.getDenominator() % 2 == 0
                || q1.getValue().equals(Fraction.ZERO) && exp < 0
                || q1.getValue().equals(Fraction.ZERO) && q2.getValue().equals(Fraction.ZERO))
            return Real.nan();

        if (base < 0 && q2.getDenominator() % 2 == 1) {
            double result = Math.pow(-base, exp);

            if (q2.getNumerator() % 2 == 0) {
                return new Real(result);
            } else {
                return new Real(-result);
            }
        }

        return new Real(Math.pow(base, exp));
    }
}
