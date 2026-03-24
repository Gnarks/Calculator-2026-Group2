package calculator.antlr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.Complex;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Real;
import calculator.calculatorBaseVisitor;
import calculator.calculatorParser.AtomComplexContext;
import calculator.calculatorParser.AtomNumberContext;
import calculator.calculatorParser.CompleteInfixContext;
import calculator.calculatorParser.CompletePostfixContext;
import calculator.calculatorParser.CompletePrefixContext;
import calculator.calculatorParser.ComplexNumberContext;
import calculator.calculatorParser.ConstEulerContext;
import calculator.calculatorParser.ConstPiContext;
import calculator.calculatorParser.INAddSubContext;
import calculator.calculatorParser.INFunctionContext;
import calculator.calculatorParser.INImplicitMultContext;
import calculator.calculatorParser.INMultContext;
import calculator.calculatorParser.INParenthesisContext;
import calculator.calculatorParser.INPowContext;
import calculator.calculatorParser.INSignedAtomContext;
import calculator.calculatorParser.INTimesDivContext;
import calculator.calculatorParser.InfixFunctionCallContext;
import calculator.calculatorParser.NumberConstantContext;
import calculator.calculatorParser.NumberRealContext;
import calculator.calculatorParser.NumberScientificContext;
import calculator.calculatorParser.Post2ParamContext;
import calculator.calculatorParser.PostAtomContext;
import calculator.calculatorParser.PostFunc1ParamContext;
import calculator.calculatorParser.PostFuncContext;
import calculator.calculatorParser.PostMultContext;
import calculator.calculatorParser.PostPlus2ParamContext;
import calculator.calculatorParser.Pre2ParamContext;
import calculator.calculatorParser.PreAtomContext;
import calculator.calculatorParser.PreFunc1ParamContext;
import calculator.calculatorParser.PreFuncContext;
import calculator.calculatorParser.PreMultContext;
import calculator.calculatorParser.PrePlus2ParamContext;
import calculator.calculatorParser.RealNumberContext;
import calculator.calculatorParser.ScientificNumberContext;
import calculator.operations.Divides;
import calculator.operations.Minus;
import calculator.operations.Plus;
import calculator.operations.Times;

/**
 * Builds Expression objects from the ANTLR parse tree.
 */
public class ParserVisitor extends calculatorBaseVisitor<Expression> {

    private Expression buildOperation(String opString, List<Expression> args) {
        try {
            switch (opString) {
                case "+":
                    return new Plus(args);
                case "-":
                    return new Minus(args);
                case "*":
                    return new Times(args);
                case "/":
                    return new Divides(args);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + opString);
            }
        } catch (IllegalConstruction e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Expression visitCompleteInfix(CompleteInfixContext ctx) {
        return visit(ctx.expressionIN());
    }

    @Override
    public Expression visitCompletePostfix(CompletePostfixContext ctx) {
        return visit(ctx.expressionPOST());
    }

    @Override
    public Expression visitCompletePrefix(CompletePrefixContext ctx) {
        return visit(ctx.expressionPRE());
    }

    @Override
    public Expression visitPre2Param(Pre2ParamContext ctx) {
        List<Expression> args = new ArrayList<>();
        args.add(visit(ctx.expressionPRE(0)));
        args.add(visit(ctx.expressionPRE(1)));
        return buildOperation(ctx.operator().getText(), args);
    }

    @Override
    public Expression visitPrePlus2Param(PrePlus2ParamContext ctx) {
        return buildOperation(ctx.operator().getText(), visitPreArgs(ctx.expressionPRE())); // ctx.expressionPRE() returns a list of all expressionPRE children, so we can visit them all to get the arguments for the operation
    }

    @Override
    public Expression visitPreMult(PreMultContext ctx) {
        return buildOperation("*", visitPreArgs(ctx.expressionPRE()));
    }

    @Override
    public Expression visitPreFunc(PreFuncContext ctx) {
        throw new UnsupportedOperationException("Prefix functions are not implemented yet: " + ctx.funcname().getText());
    }

    @Override
    public Expression visitPreAtom(PreAtomContext ctx) {
        return visit(ctx.atom());
    }

    @Override
    public Expression visitPreFunc1Param(PreFunc1ParamContext ctx) {
        throw new UnsupportedOperationException("Prefix functions are not implemented yet: " + ctx.funcname().getText());
    }

    @Override
    public Expression visitPost2Param(Post2ParamContext ctx) {
        List<Expression> args = new ArrayList<>();
        args.add(visit(ctx.expressionPOST(0)));
        args.add(visit(ctx.expressionPOST(1)));
        return buildOperation(ctx.operator().getText(), args);
    }

    @Override
    public Expression visitPostPlus2Param(PostPlus2ParamContext ctx) {
        return buildOperation(ctx.operator().getText(), visitPostArgs(ctx.expressionPOST()));
    }

    @Override
    public Expression visitPostMult(PostMultContext ctx) {
        return buildOperation("*", visitPostArgs(ctx.expressionPOST()));
    }

    @Override
    public Expression visitPostFunc(PostFuncContext ctx) {
        throw new UnsupportedOperationException("Postfix functions are not implemented yet: " + ctx.funcname().getText());
    }

    @Override
    public Expression visitPostAtom(PostAtomContext ctx) {
        return visit(ctx.atom());
    }

    @Override
    public Expression visitPostFunc1Param(PostFunc1ParamContext ctx) {
        throw new UnsupportedOperationException("Postfix functions are not implemented yet: " + ctx.funcname().getText());
    }

    @Override
    public Expression visitINAddSub(INAddSubContext ctx) {
        Expression result = visit(ctx.multExp(0));
        for (int i = 1; i < ctx.multExp().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            List<Expression> args = List.of(result, visit(ctx.multExp(i)));
            result = buildOperation(op, args);
        }
        return result;
    }

    @Override
    public Expression visitINTimesDiv(INTimesDivContext ctx) {
        Expression result = visit(ctx.powExp(0));
        for (int i = 1; i < ctx.powExp().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            List<Expression> args = List.of(result, visit(ctx.powExp(i)));
            result = buildOperation(op, args);
        }
        return result;
    }

    @Override
    public Expression visitINMult(INMultContext ctx) {
        List<Expression> factors = new ArrayList<>();
        for (int i = 0; i < ctx.powExp().size(); i++) {
            factors.add(visit(ctx.powExp(i)));
        }
        if (factors.size() == 1) {
            return factors.get(0);
        }
        return buildOperation("*", factors);
    }

    @Override
    public Expression visitINPow(INPowContext ctx) {
        if (ctx.atomIN().size() == 1) {
            return visit(ctx.atomIN(0));
        }
        throw new UnsupportedOperationException("Power operator is not implemented yet: " + ctx.getText());
    }

    @Override
    public Expression visitINSignedAtom(INSignedAtomContext ctx) {
        List<Expression> factors = new ArrayList<>();

        if (ctx.MINUS().size() % 2 != 0) {
            factors.add(new IntegerAtom(-1));
        }

        factors.add(visit(ctx.atom()));
        for (int i = 0; i < ctx.constant().size(); i++) {
            factors.add(visit(ctx.constant(i)));
        }

        if (factors.size() == 1) {
            return factors.get(0);
        }
        return buildOperation("*", factors);
    }

    @Override
    public Expression visitINImplicitMult(INImplicitMultContext ctx) {
        List<Expression> factors = new ArrayList<>();

        int expIdx = 0;
        if (!ctx.expressionIN().isEmpty() && "(".equals(ctx.getChild(0).getText())) {
            factors.add(visit(ctx.expressionIN(expIdx++)));
        }

        factors.add(visit(ctx.atom()));

        for (int i = 0; i < ctx.constant().size(); i++) {
            factors.add(visit(ctx.constant(i)));
        }

        while (expIdx < ctx.expressionIN().size()) {
            factors.add(visit(ctx.expressionIN(expIdx++)));
        }

        if (factors.size() == 1) {
            return factors.get(0);
        }
        return buildOperation("*", factors);
    }

    @Override
    public Expression visitINParenthesis(INParenthesisContext ctx) {
        return visit(ctx.expressionIN());
    }

    @Override
    public Expression visitINFunction(INFunctionContext ctx) {
        return visit(ctx.functionIN());
    }

    @Override
    public Expression visitAtomComplex(AtomComplexContext ctx) {
        return visit(ctx.complex());
    }

    @Override
    public Expression visitAtomNumber(AtomNumberContext ctx) {
        return visit(ctx.number());
    }

    @Override
    public Expression visitNumberConstant(NumberConstantContext ctx) {
        return visit(ctx.constant());
    }

    @Override
    public Expression visitNumberReal(NumberRealContext ctx) {
        return visit(ctx.real());
    }

    @Override
    public Expression visitNumberScientific(NumberScientificContext ctx) {
        return visit(ctx.scientific());
    }

    @Override
    public Expression visitScientificNumber(ScientificNumberContext ctx) {
        return new Real(new BigDecimal(ctx.getText()));
    }

    @Override
    public Expression visitRealNumber(RealNumberContext ctx) {
        if (ctx.DOT() == null) {
            return new IntegerAtom(Integer.parseInt(ctx.INT(0).getText()));
        }
        return new Real(new BigDecimal(ctx.getText()));
    }

    @Override
    public Expression visitComplexNumber(ComplexNumberContext ctx) {
        double imaginary = 1.0;

        if (ctx.number() != null) {
            imaginary = toDouble(visit(ctx.number()));
        }

        for (int i = 0; i < ctx.constant().size(); i++) {
            imaginary *= toDouble(visit(ctx.constant(i)));
        }

        return new Complex(0.0, imaginary);
    }

    @Override
    public Expression visitConstPi(ConstPiContext ctx) {
        return new Real(Math.PI);
    }

    @Override
    public Expression visitConstEuler(ConstEulerContext ctx) {
        return new Real(Math.E);
    }

    @Override
    public Expression visitInfixFunctionCall(InfixFunctionCallContext ctx) {
        throw new UnsupportedOperationException("Infix functions are not implemented yet: " + ctx.funcname().getText());
    }

    private List<Expression> visitPreArgs(List<? extends calculator.calculatorParser.ExpressionPREContext> contexts) {
        List<Expression> args = new ArrayList<>();
        for (int i = 0; i < contexts.size(); i++) {
            args.add(visit(contexts.get(i)));
        }
        return args;
    }

    private List<Expression> visitPostArgs(List<? extends calculator.calculatorParser.ExpressionPOSTContext> contexts) {
        List<Expression> args = new ArrayList<>();
        for (int i = 0; i < contexts.size(); i++) {
            args.add(visit(contexts.get(i)));
        }
        return args;
    }

    private double toDouble(Expression expression) {
        if (expression instanceof IntegerAtom integerAtom) {
            return integerAtom.getValue();
        }
        if (expression instanceof Real real) {
            return real.getValue().doubleValue();
        }
        throw new IllegalArgumentException("Expected numeric atom, got: " + expression.getClass().getSimpleName());
    }
}
