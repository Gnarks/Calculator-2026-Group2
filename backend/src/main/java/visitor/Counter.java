package visitor;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import calculator.functions.*;

/**
 * Counter is a concrete visitor that walks through an arithmetic expression
 * to tally the number of atomic values, operations, and the maximum nesting depth.
 */
public class Counter extends Visitor {

	/** Tracks the total number of operations encountered. */
	private int nbOps = 0;
	/** Tracks the total number of atomic numbers encountered. */
	private int nbNbs = 0;
	/** Stores the maximum nesting depth of the expression tree. */
	private int maxDepth = 0;
	/** Maintains the current depth during tree traversal. */
	private int currentDepth = 0;

	/**
	 * Default constructor.
	 */
	public Counter() {
	}

	/**
	 * Visits a Real number. Increments the number count and updates max depth.
	 * 
	 * @param r The Real number to visit

	 */
	@Override
	public void visit(Real r) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

	/**
	 * Visits an IntegerAtom. Increments the number count and updates max depth.
	 * 
	 * @param i The IntegerAtom to visit

	 */
	@Override
	public void visit(IntegerAtom i) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

	/**
	 * Visits a Complex number. Increments the number count and updates max depth.
	 * 
	 * @param c The Complex number to visit

	 */
	@Override
	public void visit(Complex c) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

	/**
	 * Visits a Rationnal number. Increments the number count and updates max depth.
	 * 
	 * @param q The Rationnal number to visit

	 */
	@Override
	public void visit(Rationnal q) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

	/**
	 * Visit an operation: increments the operation count, then recursively
	 * visits all child expressions to accumulate counts.
	 *
	 * @param o The operation being visited
	 */
	@Override
	public void visit(Operation o) {
		nbOps++;
		currentDepth++;
		for (Expression a : o.getArgs()) {
			a.accept(this);
		}
		currentDepth--;
	}

	/**
	 * Visits a unary function. Counts it as an operation and increments current depth.
	 *
	 * @param o The unary function to visit

	 */
	@Override
	public void visit(UnaryFunction o) {
		nbOps++;
		currentDepth++;
		o.getArg().accept(this);
		currentDepth--;
	}

	/**
	 * Visits a binary function. Counts it as an operation and increments current depth.
	 *
	 * @param f The binary function to visit

	 */
	@Override
	public void visit(BinaryFunction f) {
		nbOps++;
		currentDepth++;
		f.getFirstArg().accept(this);
		f.getSecondArg().accept(this);
		currentDepth--;
	}

	/**
	 * Visits a {@code RandomFunction} node to count operations and depth.
	 * Random functions themselves count as an operation (+1 to nbOps). Each argument expression is visited and contributes.
	 * 
	 * @param f the {@code RandomFunction} node to visit
	 */
	@Override
	public void visit(RandomFunction f) {
		nbOps++;
		currentDepth++;
		for (Expression a : f.getArgs()) {
			a.accept(this);
		}
		currentDepth--;
	}

	/**
	 * Gets the number of operations.
	 * 
	 * @return The number of operations in the expression
	 */
	public int getNbOps() {
		return nbOps;
	}

	/**
	 * Gets the number of numbers.
	 * 
	 * @return The number of numbers in the expression
	 */
	public int getNbNbs() {
		return nbNbs;
	}

	/**
	 * Gets the depth.
	 * 
	 * @return The depth (0 for a number, 1 for a simple operation, etc.)
	 */
	public int getDepth() {
		return maxDepth;
	}

}
