package visitor;

import calculator.Expression;
import calculator.MyNumber;
import calculator.Operation;

/**
 * Counter is a  visitor that counts the numbers, operations,
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

    /**
     * Visit a number: increments the number count and tracks the current depth.
     *
     * @param n The number being visited
     */
    @Override
    public void visit(MyNumber n) {
        nbNbs++;
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
     * Gets the number of operations.
     * @return The number of operations in the expression
     */
    public int getNbOps() { return nbOps; }

    /**
     * Gets the number of numbers.
     * @return The number of numbers in the expression
     */
    public int getNbNbs() { return nbNbs; }

    /**
     * Gets the depth.
     * @return The depth (0 for a number, 1 for a simple operation, etc.)
     */
    public int getDepth() { return maxDepth; }
}
