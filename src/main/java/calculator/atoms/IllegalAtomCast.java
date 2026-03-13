package calculator.atoms;

/**
 * Exception that will be used when an a cast for one
 * Atom to another isn't possible
 * e.g. complex to integer cast
 */
public class IllegalAtomCast extends RuntimeException {

	/**
	 * Default constructor.
	 */
	public IllegalAtomCast(String s) {
		super(s);
	}
}
