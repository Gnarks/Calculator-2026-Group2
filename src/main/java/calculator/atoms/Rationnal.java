package calculator.atoms;

import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import visitor.Visitor;

/**
 * TODO Rationnal number to implement
 */
public class Rationnal implements Atom {

	// TODO blank class : to implement

	/**
	 * TODO Rationnal Constructor
	 */
	public Rationnal() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented constructor");
	}

	@Override
	public Real apply(Operation o, Atom a) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'accept'");
	}

	@Override
	public void accept(AtomVisitor v) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'accept'");
	}

	@Override
	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'accept'");
	}
}
