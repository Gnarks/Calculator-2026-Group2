package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.beans.Transient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.antlr.Parser;
import calculator.atoms.IntegerAtom;
import calculator.operations.*;

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
    }

    @Test
    void testPrefixFunctions() {
        assertInstanceOf(Cosinus.class, parser.parse("cos 1")); // PreFunc1Param
        assertInstanceOf(Sinus.class, parser.parse("sin(2)")); // PreFunc
        assertInstanceOf(Tangente.class, parser.parse("tan 3"));
    }

    @Test
    void testPostfixFunctions() {
        assertInstanceOf(Cosinus.class, parser.parse("1 cos")); // PostFunc1Param
        assertInstanceOf(Sinus.class, parser.parse("(2) sin")); // PostFunc
        assertInstanceOf(Tangente.class, parser.parse("3 tan"));
    }

    @Test
    void testParseFunctions() {
        assertInstanceOf(Sqrt.class, parser.parse("sqrt(4)"));
        assertInstanceOf(Ln.class, parser.parse("ln(2)"));
        assertInstanceOf(Log.class, parser.parse("log(2, 10)"));
        assertInstanceOf(Power.class, parser.parse("2**3"));
    }
}