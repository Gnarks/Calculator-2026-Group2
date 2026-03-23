package calculator.operations;

import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic sum operation "+".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * 
 * @see Operation
 * @see Minus
 * @see Times
 * @see Divides
 */
public final class Plus extends Operation {

	/**
	 * Class constructor specifying a number of Expressions to add.
	 *
	 * @param elist The list of Expressions to add
	 * @throws IllegalConstruction If an empty list of expressions if passed as
	 *                             parameter
	 * @see #Plus(List<Expression>)
	 */
	public Plus(List<Expression> elist) throws IllegalConstruction {
		super(elist);
		symbol = "+";
		neutral = 0;
	}

	/**
	 * The actual computation of the (binary) arithmetic addition of two Reals
	 * 
	 * @param r1 The first Real
	 * @param r2 The second Real
	 * @return The (new) Real that is the result of the addition
	 */
	public Real op(Real r1, Real r2) {
		if ((r1.isPlusInf() && r2.isMinusInf())
				|| (r1.isMinusInf() && r2.isPlusInf())
				|| r1.isNan() || r2.isNan()) {
			return Real.nan(); // inf-inf = NaN or NaN +x = NaN
		}
		if (r1.isMinusInf() || r2.isMinusInf()) { // x - inf = -inf
			return Real.minusInf();
		}
		if (r1.isPlusInf() || r2.isPlusInf()) { // x + inf = +inf
			return Real.plusInf();
		}

		return new Real(r1.getValue().add(r2.getValue()));
	}

	/**
	 * The actual computation of the (binary) arithmetic addition of two Integers
	 * 
	 * @param i1 The first IntegerAtom
	 * @param i2 The second IntegerAtom
	 * @return The (new) IntegerAtom that is the result of the addition
	 */
	public IntegerAtom op(IntegerAtom i1, IntegerAtom i2) {
		return new IntegerAtom(i1.getValue() + i2.getValue());
	}

	@Override
	public Complex op(Complex c1, Complex c2) {
		org.apache.commons.numbers.complex.Complex result = c1.getValue().add(c2.getValue());
		return new Complex(result);
	}

	@Override
	public Rationnal op(Rationnal q1, Rationnal q2) {
		org.apache.commons.numbers.fraction.Fraction result = q1.getValue().add(q2.getValue());
		return new Rationnal(result);
	}
}
