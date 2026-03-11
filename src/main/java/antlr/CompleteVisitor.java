// Generated from calculator/calculator.g4 by ANTLR 4.13.2
package antlr;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import calculator.Expression;
import calculator.MyNumber;
import calculator.calculatorParser;
import calculator.calculatorVisitor;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link calculatorVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class CompleteVisitor extends AbstractParseTreeVisitor<Expression> implements calculatorVisitor<Expression> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code parser.
	 * 
	</p>
	 */
	@Override
	public Expression visitComplete(calculatorParser.CompleteContext ctx) {
		Expression res = visit(ctx.getChild(0)); // to avoid the visiting of the EOF tag
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitExpressionPRE(calculatorParser.ExpressionPREContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitExpressionPOST(calculatorParser.ExpressionPOSTContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitOperator(calculatorParser.OperatorContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitExpressionIN(calculatorParser.ExpressionINContext ctx) {
		Expression res = visitChildren(ctx);
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitMultExp(calculatorParser.MultExpContext ctx) {
		Expression res = visitChildren(ctx);
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitPowExp(calculatorParser.PowExpContext ctx) {
		Expression res = visitChildren(ctx);
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitAtomIN(calculatorParser.AtomINContext ctx) {
		Expression res = visitChildren(ctx);
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitAtom(calculatorParser.AtomContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitNumber(calculatorParser.NumberContext ctx) {
		Expression res = visitChildren(ctx);
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitScientific(calculatorParser.ScientificContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitReal(calculatorParser.RealContext ctx) {
		MyNumber result = new MyNumber(0);
		if (ctx.getChildCount() == 1) {// case of an integer
			result = new MyNumber(Integer.parseInt(ctx.getChild(0).getText()));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitComplex(calculatorParser.ComplexContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitConstant(calculatorParser.ConstantContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitFunctionIN(calculatorParser.FunctionINContext ctx) {
		return visitChildren(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override
	public Expression visitFuncname(calculatorParser.FuncnameContext ctx) {
		return visitChildren(ctx);
	}
}
