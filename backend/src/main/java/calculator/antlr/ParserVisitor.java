package calculator.antlr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import calculator.Expression;
import calculator.IllegalConstruction;
import calculator.atoms.*;
import calculator.calculatorBaseVisitor;
import calculator.calculatorParser.*;
import calculator.functions.*;
import calculator.operations.*;

/**
 * Builds Expression objects from the ANTLR parse tree.
 */
public class ParserVisitor extends calculatorBaseVisitor<Expression> {

    /**
     * Create an Expression object corresponding to the given operator and arguments.
     */
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
                case "**":
                    return new Power(args);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + opString);
            }
        } catch (IllegalConstruction e) {
            throw new RuntimeException(e);
        }
    }

    private Expression buildFunction(String funcName, List<Expression> args) {
        if (args.size() == 1) {
            Expression arg = args.get(0);
            try {
                switch (funcName) {
                    case "cos":
                        return new Cosinus(arg);
                    case "sin":
                        return new Sinus(arg);
                    case "tan":
                        return new Tangente(arg);
                    case "acos":
                        return new Arccosinus(arg);
                    case "asin":
                        return new Arcsinus(arg);
                    case "atan":
                        return new Arctangente(arg);
                    case "cosh":
                        return new Cosh(arg);
                    case "sinh":
                        return new Sinh(arg);
                    case "tanh":
                        return new Tanh(arg);
                    case "sqrt":
                        return new Sqrt(arg);
                    case "ln":
                        return new Ln(arg);
                    default:
                        throw new UnsupportedOperationException("Function not implemented yet: " + funcName);
                }
            } catch (IllegalConstruction e) {
                throw new RuntimeException(e);
            }
        }

        if ("log".equals(funcName) && args.size() == 2) {
            try {
                return new Log(args.get(0), args.get(1));
            } catch (IllegalConstruction e) {
                throw new RuntimeException(e);
            }
        }

        throw new UnsupportedOperationException("Invalid number of arguments for function " + funcName + ": " + args.size());
    }

    /**
     * Rule: complete : expressionIN EOF #CompleteInfix
     */
    @Override
    public Expression visitCompleteInfix(CompleteInfixContext ctx) {
        return visit(ctx.expressionIN());
    }

    /**
     * Rule: complete : expressionPOST EOF #CompletePostfix
     */
    @Override
    public Expression visitCompletePostfix(CompletePostfixContext ctx) {
        return visit(ctx.expressionPOST());
    }

    /**
     * Rule: complete : expressionPRE EOF #CompletePrefix
     */
    @Override
    public Expression visitCompletePrefix(CompletePrefixContext ctx) {
        return visit(ctx.expressionPRE());
    }

    /**
     * Rule: expressionPRE : operator expressionPRE expressionPRE #Pre2Param
     */
    @Override
    public Expression visitPre2Param(Pre2ParamContext ctx) {
        List<Expression> args = new ArrayList<>();
        args.add(visit(ctx.expressionPRE(0)));
        args.add(visit(ctx.expressionPRE(1)));
        return buildOperation(ctx.operator().getText(), args);
    }

    /**
     * Rule: expressionPRE : operator LPAR expressionPRE expressionPRE+ RPAR
     *     | operator LPAR expressionPRE (COMMA expressionPRE)+ RPAR
     *     #PrePlus2Param
     */
    @Override
    public Expression visitPrePlus2Param(PrePlus2ParamContext ctx) {
        return buildOperation(ctx.operator().getText(), visitPreArgs(ctx.expressionPRE()));
    }

    /**
     * Rule: expressionPRE : LPAR expressionPRE expressionPRE+ RPAR
     *     | LPAR expressionPRE (COMMA expressionPRE)+ RPAR
     *     #PreMult
     */
    @Override
    public Expression visitPreMult(PreMultContext ctx) {
        return buildOperation("*", visitPreArgs(ctx.expressionPRE()));
    }

    /**
     * Rule: expressionPRE : funcname LPAR expressionPRE+ RPAR
     *     | funcname LPAR expressionPRE (COMMA expressionPRE)* RPAR
     *     #PreFunc
     */
    @Override
    public Expression visitPreFunc(PreFuncContext ctx) {
        return buildFunction(ctx.funcname().getText(), visitPreArgs(ctx.expressionPRE()));
    }

    /**
     * Rule: expressionPRE : atom #PreAtom
     */
    @Override
    public Expression visitPreAtom(PreAtomContext ctx) {
        return visit(ctx.atom());
    }

    /**
     * Rule: expressionPRE : funcname expressionPRE #PreFunc1Param
     */
    @Override
    public Expression visitPreFunc1Param(PreFunc1ParamContext ctx) {
        return buildFunction(ctx.funcname().getText(), List.of(visit(ctx.expressionPRE())));
    }

    /**
     * Rule: expressionPOST : expressionPOST expressionPOST operator #Post2Param
     */
    @Override
    public Expression visitPost2Param(Post2ParamContext ctx) {
        List<Expression> args = new ArrayList<>();
        args.add(visit(ctx.expressionPOST(0)));
        args.add(visit(ctx.expressionPOST(1)));
        return buildOperation(ctx.operator().getText(), args);
    }

    /**
     * Rule: expressionPOST : LPAR expressionPOST expressionPOST+ RPAR operator
     *     | LPAR expressionPOST (COMMA expressionPOST)+ RPAR operator
     *     #PostPlus2Param
     */
    @Override
    public Expression visitPostPlus2Param(PostPlus2ParamContext ctx) {
        return buildOperation(ctx.operator().getText(), visitPostArgs(ctx.expressionPOST()));
    }

    /**
     * Rule: expressionPOST : LPAR expressionPOST expressionPOST+ RPAR
     *     | LPAR expressionPOST (COMMA expressionPOST)+ RPAR
     *     #PostMult
     */
    @Override
    public Expression visitPostMult(PostMultContext ctx) {
        return buildOperation("*", visitPostArgs(ctx.expressionPOST()));
    }

    /**
     * Rule: expressionPOST : LPAR expressionPOST+ RPAR funcname
     *     | LPAR expressionPOST (COMMA expressionPOST)* RPAR funcname
     *     #PostFunc
     */
    @Override
    public Expression visitPostFunc(PostFuncContext ctx) {
        return buildFunction(ctx.funcname().getText(), visitPostArgs(ctx.expressionPOST()));
    }

    /**
     * Rule: expressionPOST : atom #PostAtom
     */
    @Override
    public Expression visitPostAtom(PostAtomContext ctx) {
        return visit(ctx.atom());
    }

    /**
     * Rule: expressionPOST : expressionPOST funcname #PostFunc1Param
     */
    @Override
    public Expression visitPostFunc1Param(PostFunc1ParamContext ctx) {
        return buildFunction(ctx.funcname().getText(), List.of(visit(ctx.expressionPOST())));
    }

    /**
     * Rule: expressionIN : multExp ((PLUS | MINUS) multExp)* #INAddSub
     */
    @Override
    public Expression visitINAddSub(INAddSubContext ctx) {
        Expression result = visit(ctx.multExp(0));
        for (int i = 1; i < ctx.multExp().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();  // operator is the odd child between multExp
            List<Expression> args = List.of(result, visit(ctx.multExp(i)));
            result = buildOperation(op, args);
        }
        return result;
    }

    /**
     * Rule: multExp : powExp ((TIMES | DIV) powExp)* #INTimesDiv
     */
    @Override
    public Expression visitINTimesDiv(INTimesDivContext ctx) {
        Expression result = visit(ctx.powExp(0));
        for (int i = 1; i < ctx.powExp().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();  // operator is the odd child between powExp
            List<Expression> args = List.of(result, visit(ctx.powExp(i)));
            result = buildOperation(op, args);
        }
        return result;
    }

    /**
     * Rule: multExp : powExp (LPAR powExp RPAR)* #INMult
     */
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

    /**
     * Rule: powExp : atomIN (POW atomIN)* #INPow
     */
    @Override
    public Expression visitINPow(INPowContext ctx) {
        int atomCount = ctx.atomIN().size();
        if (atomCount == 1) {
            return visit(ctx.atomIN(0));
        }

        // Right-associative folding: a**b**c = a**(b**c).
        Expression result = visit(ctx.atomIN(atomCount - 1));
        for (int i = atomCount - 2; i >= 0; i--) {
            result = buildOperation("**", List.of(visit(ctx.atomIN(i)), result));
        }
        return result;
    }

    /**
     * Rule: atomIN : (PLUS | MINUS)* atom (constant)* #INSignedAtom
     */
    @Override
    public Expression visitINSignedAtom(INSignedAtomContext ctx) {
        List<Expression> factors = new ArrayList<>();

        factors.add(visit(ctx.atom()));
        for (int i = 0; i < ctx.constant().size(); i++) {
            factors.add(visit(ctx.constant(i)));
        }

        Expression expression;
        if (factors.size() == 1) {
            expression = factors.get(0);
        } else {
            expression = buildOperation("*", factors);
        }

        // Keep each '-' from the input as an explicit unary minus equivalent: 0 - expr.
        for (int i = 0; i < ctx.MINUS().size(); i++) {
            expression = buildOperation("-", List.of(new IntegerAtom(0), expression));
        }

        return expression;
    }

    /**
     * Rule: atomIN : (LPAR expressionIN RPAR)? atom (constant)* (LPAR expressionIN RPAR)? #INImplicitMult
     */
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

    /**
     * Rule: atomIN : (LPAR expressionIN RPAR) #INParenthesis
     */
    @Override
    public Expression visitINParenthesis(INParenthesisContext ctx) {
        return visit(ctx.expressionIN());
    }

    /**
     * Rule: atomIN : functionIN #INFunction
     */
    @Override
    public Expression visitINFunction(INFunctionContext ctx) {
        return visit(ctx.functionIN());
    }

    /**
     * Rule: atom : complex #AtomComplex
     */
    @Override
    public Expression visitAtomComplex(AtomComplexContext ctx) {
        return visit(ctx.complex());
    }

    /**
     * Rule: atom : number #AtomNumber
     */
    @Override
    public Expression visitAtomNumber(AtomNumberContext ctx) {
        return visit(ctx.number());
    }

    /**
     * Rule: number : constant #NumberConstant
     */
    @Override
    public Expression visitNumberConstant(NumberConstantContext ctx) {
        return visit(ctx.constant());
    }

    /**
     * Rule: number : real #NumberReal
     */
    @Override
    public Expression visitNumberReal(NumberRealContext ctx) {
        return visit(ctx.real());
    }

    /**
     * Rule: number : scientific #NumberScientific
     */
    @Override
    public Expression visitNumberScientific(NumberScientificContext ctx) {
        return visit(ctx.scientific());
    }

    /**
     * Rule: scientific : (real | INT) E (PLUS | MINUS)? INT #ScientificNumber
     */
    @Override
    public Expression visitScientificNumber(ScientificNumberContext ctx) {
        return new Real(new BigDecimal(ctx.getText()));
    }

    /**
     * Rule: real : INT (DOT INT)? #RealNumber
     */
    @Override
    public Expression visitRealNumber(RealNumberContext ctx) {
        if (ctx.DOT() == null) {
            return new IntegerAtom(Integer.parseInt(ctx.INT(0).getText()));
        }
        return new Real(new BigDecimal(ctx.getText()));
    }

    /**
     * Rule: complex : number? (constant)* I #ComplexNumber
     */
    @Override
    public Expression visitComplexNumber(ComplexNumberContext ctx) {
        List<Expression> factors = new ArrayList<>();

        if (ctx.number() != null) {
            factors.add(visit(ctx.number()));
        }

        for (int i = 0; i < ctx.constant().size(); i++) {
            factors.add(visit(ctx.constant(i)));
        }

        factors.add(new Complex(0.0, 1.0));

        if (factors.size() == 1) {
            return factors.get(0);
        }
        return buildOperation("*", factors);
    }

    /**
     * Rule: constant : PI #ConstPi
     */
    @Override
    public Expression visitConstPi(ConstPiContext ctx) {
        return new Real(Math.PI);  // MAY BE CHANGE THIS BY IMPLEMENTING A PI CONSTANT IN THE CALCULATOR AND BE ABLE TO EVALUATE IT THE EVALATOR
    }

    /**
     * Rule: constant : EULER #ConstEuler
     */
    @Override
    public Expression visitConstEuler(ConstEulerContext ctx) {
        return new Real(Math.E); // SAME THAN ABOVE
    }

    /**
     * Rule: functionIN : funcname LPAR expressionIN (COMMA expressionIN)* RPAR #InfixFunctionCall
     */
    @Override
    public Expression visitInfixFunctionCall(InfixFunctionCallContext ctx) {
        List<Expression> args = new ArrayList<>();
        for (calculator.calculatorParser.ExpressionINContext expCtx : ctx.expressionIN()) {
            args.add(visit(expCtx));
        }
        return buildFunction(ctx.funcname().getText(), args);
    }

    /**
     * Visit a list of sub-expressions prefixed by an operator.
     */
    private List<Expression> visitPreArgs(List<? extends calculator.calculatorParser.ExpressionPREContext> contexts) {
        List<Expression> args = new ArrayList<>();
        for (int i = 0; i < contexts.size(); i++) {
            args.add(visit(contexts.get(i)));
        }
        return args;
    }

    /**
     * Visit a list of sub-expressions postfixeed by an operator.
     */
    private List<Expression> visitPostArgs(List<? extends calculator.calculatorParser.ExpressionPOSTContext> contexts) {
        List<Expression> args = new ArrayList<>();
        for (int i = 0; i < contexts.size(); i++) {
            args.add(visit(contexts.get(i)));
        }
        return args;
    }

}
