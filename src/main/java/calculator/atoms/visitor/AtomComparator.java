package calculator.atoms.visitor;

import calculator.atoms.*;

public class AtomComparator extends AtomVisitor {

	public AtomType highestType;

	public AtomType getResult() {
		return highestType;
	}

	@Override
	public void visit(Real r) {
		// only change the highestType if the actual one is the rationnal
		if (highestType == AtomType.RATIONNAL || highestType == null) {
			highestType = AtomType.REAL;
		}

	}

	@Override
	public void visit(Complex r) {

		// sets the highestType to Complex as it is the highestType in regards to
		// Rationnals and Reals
		highestType = AtomType.COMPLEX;

	}

	@Override
	public void visit(Rationnal r) {
		// changes the highestType to Rationnal if it isn't set yet
		if (highestType == null) {
			highestType = AtomType.RATIONNAL;
		}
	}

}
