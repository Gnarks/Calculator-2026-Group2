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
     * @return The number of operations in the expression
     */
    public int getNbOps() { return nbOps; }

    /**
     * @return The number of numbers in the expression
     */
    public int getNbNbs() { return nbNbs; }

    /**
     * @return The depth (0 for a number, 1 for a simple operation, etc.)
     */
    public int getDepth() { return maxDepth; }
}
