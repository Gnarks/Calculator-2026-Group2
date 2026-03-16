package calculator.operations;

import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic operation "-".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * 
 * @see Operation
 * @see Plus
 * @see Times
 * @see Divides
 */
public final class Minus extends Operation {

	/**
	 * Class constructor specifying a number of Expressions to subtract.
	 *
	 * @param elist The list of Expressions to subtract
	 * @throws IllegalConstruction If an empty list of expressions if passed as
	 *                             parameter
	 * @see #Minus(List<Expression>)
	 */
	public Minus(List<Expression> elist) throws IllegalConstruction {
		super(elist);
		symbol = "-";
		neutral = 0;
	}

	/**
	 * The actual computation of the (binary) arithmetic subtraction of two integers
	 * 
	 * @param l The first integer
	 * @param r The second integer that should be subtracted from the first
	 * @return The integer that is the result of the subtraction
	 */
	public int op(int l, int r) {
		return (l - r);
	}

	/**
	 * The actual computation of the (binary) arithmetic subtraction of two Reals
	 * 
	 * @param r1 The first Real
	 * @param r2 The second Real
	 * @return The (new) Real that is the result of the subtraction
	 */
	public Real op(Real r1, Real r2) {
		return new Real(r1.getValue().add(r2.getValue().negate()));
	}

	/**
	 * The actual computation of the (binary) arithmetic subtraction of two
	 * Integers
	 * 
	 * @param i1 The first IntegerAtom
	 * @param i2 The second IntegerAtom
	 * @return The (new) IntegerAtom that is the result of the subtraction
	 */
	public IntegerAtom op(IntegerAtom i1, IntegerAtom i2) {
		return new IntegerAtom(i1.getValue() - i2.getValue());
	}

	@Override
	public Complex op(Complex c1, Complex c2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'op'");
	}

	@Override
	public Rationnal op(Rationnal q1, Rationnal q2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'op'");
	}
}
