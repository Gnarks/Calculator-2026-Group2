package calculator.operations;

import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.*;
import org.apache.commons.numbers.fraction.Fraction;

public final class Power extends Operation {

    public Power(List<Expression> elist) throws IllegalConstruction {
        super(elist);
        symbol = "^";
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

        if (r1.isNan() || r2.isNan() || base < 0)
            return Real.nan();

        if(base == 1)
            return new Real(1);

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
            if(0 < base && base < 1)
                return new Real(0);
            return Real.plusInf();
        }

        if(r2.isMinusInf()) {
            if(0 < base && base < 1)
                return Real.plusInf();
            return new Real(0);
        }

        return new Real(Math.pow(base, exp));
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
     * The actual exponentiation of two complex number
     *
     * @param c1 the base
     * @param c2 the exponent
     * @return the result of the exponentiation
     */
    @Override
    public Complex op(Complex c1, Complex c2) {
        org.apache.commons.numbers.complex.Complex result = c1.getValue().pow(c2.getValue());
        return new Complex(result);
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

        if(base < 0 && q2.getDenominator() % 2 == 0 || q1.getValue().equals(Fraction.ZERO) && exp < 0)
            return Real.nan();

        if(q1.getValue().equals(Fraction.ZERO) && q2.getValue().equals(Fraction.ZERO))
            return new Real(1);

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
