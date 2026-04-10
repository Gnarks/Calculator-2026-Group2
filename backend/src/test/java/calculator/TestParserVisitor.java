package calculator;

import calculator.atoms.*;
import io.cucumber.java.lv.Un;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.antlr.Parser;
import calculator.operations.*;
import calculator.functions.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TestParserVisitor {

    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void testPrePlus2ParamVisitsAllArguments() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("+(1,2,3,4)"));

        assertInstanceOf(Plus.class, parsed);
        assertEquals(4, parsed.getArgs().size());
        assertEquals(new IntegerAtom(1), parsed.getArgs().get(0));
        assertEquals(new IntegerAtom(2), parsed.getArgs().get(1));
        assertEquals(new IntegerAtom(3), parsed.getArgs().get(2));
        assertEquals(new IntegerAtom(4), parsed.getArgs().get(3));
    }

    @Test
    void testPreMultVisitsAllArguments() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("(1,2,3,4)"));

        assertInstanceOf(Times.class, parsed);
        assertEquals(4, parsed.getArgs().size());
    }

    @Test
    void testPostPlus2ParamVisitsAllArguments() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("(1,2,3,4)+"));

        assertInstanceOf(Plus.class, parsed);
        assertEquals(4, parsed.getArgs().size());
        assertEquals(new IntegerAtom(1), parsed.getArgs().get(0));
        assertEquals(new IntegerAtom(2), parsed.getArgs().get(1));
        assertEquals(new IntegerAtom(3), parsed.getArgs().get(2));
        assertEquals(new IntegerAtom(4), parsed.getArgs().get(3));
    }

    @Test
    void testPostMultVisitsAllArguments() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("(1,2,3,4)"));

        assertInstanceOf(Times.class, parsed);
        assertEquals(4, parsed.getArgs().size());
    }

    @Test
    void testInfixAddSubBuildsLeftAssociativeTree() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("1-2-3"));

        assertInstanceOf(Minus.class, parsed);
        assertEquals(2, parsed.getArgs().size());
        assertInstanceOf(Minus.class, parsed.getArgs().get(0));
        assertEquals(new IntegerAtom(3), parsed.getArgs().get(1));
    }

    @Test
    void testInfixImplicitMultiplicationVisitsAllFactors() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("(2)3pi(4)"));

        assertInstanceOf(Times.class, parsed);
        assertEquals(4, parsed.getArgs().size());
    }

    @Test
    void testInfixFunctions() {
        assertInstanceOf(Cosinus.class, parser.parse("cos(1)"));
        assertInstanceOf(Sinus.class, parser.parse("sin(2)"));
        assertInstanceOf(Tangente.class, parser.parse("tan(3)"));
        assertInstanceOf(Arccosinus.class, parser.parse("acos(1)"));
        assertInstanceOf(Arcsinus.class, parser.parse("asin(1)"));
        assertInstanceOf(Arctangente.class, parser.parse("atan(4)"));
        assertInstanceOf(Cosh.class, parser.parse("cosh(1)"));
        assertInstanceOf(Sinh.class, parser.parse("sinh(2)"));
        assertInstanceOf(Tanh.class, parser.parse("tanh(3)"));
    }

    @Test
    void testPrefixFunctions() {
        assertInstanceOf(Cosinus.class, parser.parse("cos 1")); // PreFunc1Param
        assertInstanceOf(Sinus.class, parser.parse("sin(2)")); // PreFunc
        assertInstanceOf(Tangente.class, parser.parse("tan 3"));
        assertInstanceOf(Cosh.class, parser.parse("cosh 1"));
        assertInstanceOf(Sinh.class, parser.parse("sinh(2)"));
        assertInstanceOf(Tanh.class, parser.parse("tanh 3"));
    }

    @Test
    void testPostfixFunctions() {
        assertInstanceOf(Cosinus.class, parser.parse("1 cos")); // PostFunc1Param
        assertInstanceOf(Sinus.class, parser.parse("(2) sin")); // PostFunc
        assertInstanceOf(Tangente.class, parser.parse("3 tan"));
        assertInstanceOf(Cosh.class, parser.parse("1 cosh"));
        assertInstanceOf(Sinh.class, parser.parse("(2) sinh"));
        assertInstanceOf(Tanh.class, parser.parse("3 tanh"));
    }

    @Test
    void testParseFunctions() {
        assertInstanceOf(Sqrt.class, parser.parse("sqrt(4)"));
        assertInstanceOf(Ln.class, parser.parse("ln(2)"));
        assertInstanceOf(Log.class, parser.parse("log(2, 10)"));
        assertInstanceOf(Power.class, parser.parse("2**3"));
    }

    @Test
    void testDifferentiateRationalAndDivision() {
        assertInstanceOf(Rationnal.class, parser.parse("1/6"));
        assertInstanceOf(Divides.class, parser.parse("1//6"));
        Rationnal q = (Rationnal) parser.parse("1/6");
        assertEquals(1, q.getNumerator());
        assertEquals(6, q.getDenominator());
    }

    @Test
    void testParseMultPreAndPostOp() {
        testMultExpression("5 6 + 3 2 + +");
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("5 6 + 3 2 + +"));
        assertInstanceOf(Plus.class, parsed);
        assertInstanceOf(Plus.class, parsed.getArgs().get(0));
        assertInstanceOf(Plus.class, parsed.getArgs().get(1));
    }

    void testMultExpression(String args) {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse(args));
        assertInstanceOf(Plus.class, parsed);
        assertInstanceOf(Plus.class, parsed.getArgs().get(0));
        assertInstanceOf(Plus.class, parsed.getArgs().get(1));
    }

    @Test
    void testParsePreFunc() {
        UnaryFunction parsed = assertInstanceOf(UnaryFunction.class, parser.parse("sin(+ 5 6)"));
        assertInstanceOf(Sinus.class, parsed);
        assertInstanceOf(Plus.class, parsed.arg);
    }

    @Test
    void testRandFunctionsEmptyParams() {
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("randint()"));
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("randrat()"));
        RandomFunction randomFunctionReal = assertInstanceOf(RandomFunction.class, parser.parse("randreal()"));
        assertEquals(AtomType.REAL, randomFunctionReal.getType());
        RandomFunction randomFunctionComplex = assertInstanceOf(RandomFunction.class,
                parser.parse("randcomplex()"));
        assertEquals(AtomType.COMPLEX, randomFunctionComplex.getType());
    }

    @Test
    void testRandFunctionsOneParam() {
        RandomFunction randomFunctionInt = assertInstanceOf(RandomFunction.class, parser.parse("randint(100)"));
        assertEquals(AtomType.INTEGER, randomFunctionInt.getType());
        RandomFunction randomFunctionRat = assertInstanceOf(RandomFunction.class, parser.parse("randrat(100)"));
        assertEquals(AtomType.RATIONNAL, randomFunctionRat.getType());
        RandomFunction randomFunctionReal = assertInstanceOf(RandomFunction.class, parser.parse("randreal(100)"));
        assertEquals(AtomType.REAL, randomFunctionReal.getType());
        RandomFunction randomFunctionComplex = assertInstanceOf(RandomFunction.class,
                parser.parse("randcomplex(100)"));
        assertEquals(AtomType.COMPLEX, randomFunctionComplex.getType());
    }

    @Test
    void testRandFunctionsTwoParam() {
        RandomFunction randomFunctionInt = assertInstanceOf(RandomFunction.class, parser.parse("randint(100, 565)"));
        assertEquals(AtomType.INTEGER, randomFunctionInt.getType());
        RandomFunction randomFunctionRat = assertInstanceOf(RandomFunction.class, parser.parse("randrat(100, 565)"));
        assertEquals(AtomType.RATIONNAL, randomFunctionRat.getType());
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("randreal(100, 565)"));
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("randcomplex(100, 565)"));
    }

    @Test
    void testInvalidParamsNumberFunction() {
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("log(6)"));
        assertThrows(UnsupportedOperationException.class, () -> parser.parse("sin(6, 5)"));
    }

    @Test
    void testMultExp() {
        Operation parsed = assertInstanceOf(Operation.class, parser.parse("2(3)(4**2)"));
        assertInstanceOf(Times.class, parsed);
    }

    @Test
    void testSignedAtom_withConstantsAndMultipleMinus() {
        Expression parsed = parser.parse("--5pi");
        Operation outerMinus = assertInstanceOf(Operation.class, parsed);
        assertInstanceOf(Minus.class, outerMinus);
        assertEquals(2, outerMinus.getArgs().size());
        assertInstanceOf(IntegerAtom.class, outerMinus.getArgs().get(0));
        Operation innerMinus = assertInstanceOf(Operation.class, outerMinus.getArgs().get(1));
        assertInstanceOf(Minus.class, innerMinus);
        assertEquals(2, innerMinus.getArgs().size());
        assertInstanceOf(IntegerAtom.class, innerMinus.getArgs().get(0));
        Operation mult = assertInstanceOf(Operation.class, innerMinus.getArgs().get(1));
        assertInstanceOf(Times.class, mult);
        assertEquals(2, mult.getArgs().size());
        assertInstanceOf(IntegerAtom.class, mult.getArgs().get(0));
    }

    @Test
    void testParseSimplestComplex() {
        Expression parsed = parser.parse("i");
        Complex complex = assertInstanceOf(Complex.class, parsed);
        assertEquals(0.0, complex.getValue().getReal());
        assertEquals(1.0, complex.getValue().getImaginary());
    }

    @Test
    void testParseComplex() {
        Expression parsed = parser.parse("2pii");
        Operation mult = assertInstanceOf(Operation.class, parsed);
        assertInstanceOf(Times.class, mult);
        assertEquals(3, mult.getArgs().size());
    }

    @Test
    void testParseReal() {
        Expression parsed = parser.parse("2.565");
        assertInstanceOf(Real.class, parsed);
    }

}