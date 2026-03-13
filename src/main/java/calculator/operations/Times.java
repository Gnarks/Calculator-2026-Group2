package calculator.operations;

import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;

/**
 * This class represents the arithmetic multiplication operation "*".
 * The class extends an abstract superclass Operation.
 * Other subclasses of Operation represent other arithmetic operations.
 * 
 * @see Operation
 * @see Minus
 * @see Plus
 * @see Divides
 */
public final class Times extends Operation {
	/**
	 * Class constructor specifying a number of Expressions to multiply.
	 *
	 * @param elist The list of Expressions to multiply
	 * @throws IllegalConstruction If an empty list of expressions if passed as
	 *                             parameter
	 * @see #Times(List<Expression>)
	 */
	public Times(List<Expression> elist) throws IllegalConstruction {
		super(elist);
		symbol = "*";
		neutral = 1;
	}

	/**
	 * The actual computation of the (binary) arithmetic multiplication of two Reals
	 * 
	 * @param r1 The first Real
	 * @param r2 The second Real
	 * @return The (new) Real that is the result of the multiplication
	 */
	public Real op(Real r1, Real r2) {
		return new Real(r1.getValue().multiply(r2.getValue()));
	}

	@Override
	public Real op(Complex c1, Complex c2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'op'");
	}

	@Override
	public Real op(Rationnal q1, Rationnal q2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'op'");
	}
}
