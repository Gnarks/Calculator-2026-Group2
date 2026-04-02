package visitor;

import calculator.Expression;
import calculator.operations.Operation;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import calculator.functions.*;

/**
 * Counter is a visitor that counts the numbers, operations,
 * and depth in an arithmetic expression.
 */
public class Counter extends Visitor {

	private int nbOps = 0;
	private int nbNbs = 0;
	private int maxDepth = 0;
	private int currentDepth = 0;

	/**
	 * Default constructor.
	 */
	public Counter() {
	}

	@Override
	public void visit(Real r) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

	@Override
	public void visit(IntegerAtom i) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

	@Override
	public void visit(Complex c) {
		nbNbs += 1;
		maxDepth = Math.max(maxDepth, currentDepth);
	}

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

	@Override
	public void visit(UnaryFunction o) {
		nbOps++;
		currentDepth++;
		o.getArg().accept(this);
		currentDepth--;
	}

	@Override
	public void visit(BinaryFunction f) {
		nbOps++;
		currentDepth++;
		f.getFirstArg().accept(this);
		f.getSecondArg().accept(this);
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
