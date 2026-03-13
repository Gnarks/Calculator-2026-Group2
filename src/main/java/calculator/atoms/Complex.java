package calculator.atoms;

import calculator.Operation;
import calculator.atoms.visitor.AtomVisitor;
import visitor.Visitor;

public class Complex implements Atom {

	// TODO blank class : to implement

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
