package calculator.atoms;

/**
 * Enumeration of possible atom (number) types
 */
public enum AtomType {
	/**
	 * Complex number, e.g. 3i, 5, 5+3ei
	 */
	COMPLEX,

	/**
	 * Real number, e.g. 4.555, 5E-10, 5
	 */
	REAL,

	/**
	 * Rationnal number, e.g. 1/3, 5, 99/1010
	 */
	RATIONNAL
}
