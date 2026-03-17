package calculator.atoms;

import calculator.operations.Operation;
import calculator.atoms.visitor.AtomVisitor;
import visitor.Visitor;

/**
 * Complex Number type is used to represent complex e.g. 3 + 2i
 */
public class Complex implements Atom {

	// TODO blank class : to implement

	/**
	 * TODO Complex constructor
	 * 
	 */
	public Complex() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented constructor");
	}
	// temporarily to pass the mvn tests
	public Complex(double real, double imaginary) { }

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
