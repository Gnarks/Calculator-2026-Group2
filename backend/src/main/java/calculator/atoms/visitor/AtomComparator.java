package calculator.atoms.visitor;

import calculator.atoms.*;

/**
 * AtomComparator is an AtomVisitor whose goal is to give the largest set of number
 * between all visited types
 * e.g. between an Integer, Real and Complex, the Complex type is the largest
 * as both the Integer and Real are included in Complexes
 *
 * @see AtomVisitor
 */
public class AtomComparator extends AtomVisitor {

	/* The highestType represents the most englobing type visited */
	/* Set by default to Integer (the least englobing) */
	private AtomType highestType = AtomType.INTEGER;

	/**
	 * returns the most englobing type visited
	 *
	 * @return The most englobing type visited
	 */
	public AtomType getResult() {
		return highestType;
	}

	/**
	 * Constructor of the AtomComparator visitor
	 * 
	 */
	public AtomComparator() {
	}

	/**
	 * Visits a Real and changes the result if it was a Rationnal or Integer
	 * 
	 * @param r the Real to be visited
	 */
	@Override
	public void visit(Real r) {
		// only change the highestType if the actual one is the rationnal
		if (highestType == AtomType.RATIONNAL || highestType == AtomType.INTEGER) {
			highestType = AtomType.REAL;
		}

	}

	/**
	 * Visits a Complex and changes the result to complex as any
	 * Number can be converted to a complex
	 * 
	 * @param c the Complex to be visited
	 */
	@Override
	public void visit(Complex c) {

		// sets the highestType to Complex as it is the highestType in regards to
		// Rationnals and Reals
		highestType = AtomType.COMPLEX;

	}

	/**
	 * Visits a Rationnal and changes the result only if it was an integer
	 * 
	 * @param q the Rationnal to be visited
	 */
	@Override
	public void visit(Rationnal q) {
		if (highestType == AtomType.INTEGER) {
			highestType = AtomType.RATIONNAL;
		}
	}

	/**
	 * Visits a Integer and doesn't change the result as
	 * the integer type is the smallest set
	 * 
	 * @param i the Integer to be visited
	 */
	@Override
	public void visit(IntegerAtom i) {
	}

}
