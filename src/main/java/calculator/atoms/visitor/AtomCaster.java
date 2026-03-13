package calculator.atoms.visitor;

import calculator.atoms.*;

public class AtomCaster extends AtomVisitor {

	private AtomType type;

	private Atom result;

	public Atom getResult() {
		return result;
	}

	public AtomCaster(AtomType type) {
		this.type = type;
	}

	@Override
	public void visit(Real r) {
		switch (type) {
			case REAL:
				result = r;
				break;

			default:
				result = null;
				throw new UnsupportedOperationException("Cast Not implemented");
		}
	}

	@Override
	public void visit(Complex c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(Rationnal q) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

}
