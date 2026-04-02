package calculator.atoms.visitor;

import java.math.BigDecimal;

import calculator.atoms.*;

/**
 * AtomCaster is an AtomVisitor whose goal is to cast
 * the visited atom to the specified type
 * and throws errors if this cast is impossible e.g a complex to an Integer
 *
 * @see AtomVisitor
 */
public class AtomCaster extends AtomVisitor {

	/* The type to Cast the visited Atom to */
	private AtomType type;

	/* The Result of the casting after the visit */
	private Atom result;

	/**
	 * returns the result of the cast of the visited to the specified type
	 * 
	 * @return the Result of the cast from the visited Atom to the specified type
	 */
	public Atom getResult() {
		return result;
	}

	/**
	 * Constructs an AtomCaster to cast to the specified type
	 *
	 * @param type The type to Cast the visited Atom to
	 */
	public AtomCaster(AtomType type) {
		this.type = type;
	}

	/**
	 * Visiting a Real Atom to cast it to the specified type of the visitor
	 *
	 * @param r The visited Real to be cast
	 * @throws IllegalAtomCast in case of an impossible cast
	 */
	@Override
	public void visit(Real r) {
		switch (type) {
			case REAL:
				result = r;
				break;

			case INTEGER:
				throw new IllegalAtomCast("Impossible to cast Real to Integer");

			case RATIONNAL:
				if (r.isNan() || r.isPlusInf() || r.isMinusInf()) {
					throw new IllegalAtomCast("Impossible to cast NaN or Infinity Real to Rationnal");
				}
				result = new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(r.getValue().doubleValue()));
				break;

			case COMPLEX:
				if (r.isNan()) {
					result = Complex.nan();
					break;
				}
				result = new Complex(r.getValue().doubleValue(), 0);
				break;

			default:
				result = null;
				throw new UnsupportedOperationException("Cast Not implemented");
		}
	}

	/**
	 * Visiting a Complex Atom to cast it to the specified type of the visitor
	 *
	 * @param c The visited Complex to be cast
	 * @throws IllegalAtomCast in case of an impossible cast
	 */
	@Override
	public void visit(Complex c) {
		switch (type) {
			case COMPLEX:
				result = c;
				break;

			case REAL:
			case INTEGER:
			case RATIONNAL:
				throw new IllegalAtomCast("Impossible to cast Complex to " + type);

			default:
				result = null;
				throw new UnsupportedOperationException("Cast Not implemented");
		}
	}

	/**
	 * Visiting a Rationnal Atom to cast it to the specified type of the visitor
	 *
	 * @param q The visited Rationnal to be cast
	 * @throws IllegalAtomCast in case of an impossible cast
	 */
	@Override
	public void visit(Rationnal q) {
		switch (type) {
			case RATIONNAL:
				result = q;
				break;

			case REAL:
				BigDecimal num = new BigDecimal(q.getNumerator());
				BigDecimal denom = new BigDecimal(q.getDenominator());
				result = new Real(num.divide(denom, Real.context));

				break;

			case COMPLEX:
				result = new Complex(q.getValue().doubleValue(), 0);
				break;

			case INTEGER:
				throw new IllegalAtomCast("Impossible to cast Rationnal to Integer");

			default:
				result = null;
				throw new UnsupportedOperationException("Cast Not implemented");
		}
	}

	/**
	 * Visiting a Integer Atom to cast it to the specified type of the visitor
	 *
	 * @param i The visited IntegerAtom to be cast
	 * @throws IllegalAtomCast in case of an impossible cast
	 */
	@Override
	public void visit(IntegerAtom i) {
		switch (type) {
			case INTEGER:
				result = i;
				break;

			case REAL:
				result = new Real(i.getValue());
				break;

			case RATIONNAL:
				result = new Rationnal(i.getValue(), 1);
				break;

			case COMPLEX:
				result = new Complex(i.getValue(), 0);
				break;

			default:
				result = null;
				throw new UnsupportedOperationException("Cast Not implemented");
		}
	}

}
