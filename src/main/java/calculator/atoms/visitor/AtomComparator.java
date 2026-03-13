package calculator.atoms.visitor;

import calculator.atoms.*;

public class AtomComparator extends AtomVisitor {

	private AtomType highestType = AtomType.INTEGER;

	public AtomType getResult() {
		return highestType;
	}

	@Override
	public void visit(Real r) {
		// only change the highestType if the actual one is the rationnal
		if (highestType == AtomType.RATIONNAL || highestType == AtomType.INTEGER) {
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
		if (highestType == AtomType.INTEGER) {
			highestType = AtomType.RATIONNAL;
		}
	}

	@Override
	public void visit(IntegerAtom i) {
	}

}
