package calculator.operations;

import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

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
    public Real op(Real r1, Real r2) {
        double base = r1.getValue().doubleValue();
        double exp = r2.getValue().doubleValue();

        if (r1.isNan() || r2.isNan() || base < 0)
            return Real.nan();

        if(r2.isPlusInf()) {
            if(r1.isMinusInf() || 0 < base && base < 1)
                return new Real(0);
            if(base == 1)
                return new Real(1);
            return Real.plusInf();
        }

        if(r2.isMinusInf()) {
            if(0 < base && base < 1)
                return Real.plusInf();
            if(base == 1)
                return new Real(1);
            return new Real(0);
        }

        if(exp < 0 && base == 0)
            return Real.plusInf();

        return new Real(Math.pow(base, exp));
    }

    /**
     * The actual exponentiation of two Integers.
     *
     * @param i1 the base
     * @param i2 the exponent
     * @return the result of the operation
     */
    public IntegerAtom op(IntegerAtom i1, IntegerAtom i2) {
        int result;
        if(i2.getValue() < 0) {
            if(i1.getValue() == 0) {
                throw new ArithmeticException("Undefined result");
            }
            // todo : change this
            throw new ArithmeticException("Result not an integer");
        }
        try {
           result = Math.powExact(i1.getValue(), i2.getValue());
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Overflow");
        }
        return new IntegerAtom(result);
    }

    @Override
    public Complex op(Complex c1, Complex c2) {
        org.apache.commons.numbers.complex.Complex result = c1.getValue().pow(c2.getValue());
        return new Complex(result);
    }

    @Override
    public Rationnal op(Rationnal q1, Rationnal q2) {
        return new Rationnal(6);
    }
}
