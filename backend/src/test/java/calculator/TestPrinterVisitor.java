package calculator;

import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.functions.Log;
import calculator.functions.RandomFunction;
import calculator.functions.Sinus;
import org.apache.commons.numbers.fraction.Fraction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import visitor.Printer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestPrinterVisitor {

    private Printer printer;

    @BeforeEach
    void setUp() {
        printer =  new Printer();
    }

    @Test
    void testVisitUnaryFunction_INFIX() {
        try {
            Sinus sinus = new Sinus(new IntegerAtom(180));
            printer.visit(sinus);
            assertEquals("sin(180)", printer.getResult());
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testVisitUnaryFunction_POSTFIX() {
        printer = new Printer(Notation.POSTFIX);
        try {
            Sinus sinus = new Sinus(new IntegerAtom(180));
            printer.visit(sinus);
            assertEquals("(180)sin", printer.getResult());
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testVisitBinaryFunction_INFIX() {
        try {
            Log log = new Log(new IntegerAtom(2), new IntegerAtom(8));
            printer.visit(log);
            assertEquals("log(2, 8)", printer.getResult());
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testVisitBinaryFunction_POSTFIX() {
        printer = new Printer(Notation.POSTFIX);
        try {
            Log log = new Log(new IntegerAtom(2), new IntegerAtom(8));
            printer.visit(log);
            assertEquals("(2, 8)log", printer.getResult());
        } catch (IllegalConstruction e) {
            fail();
        }
    }

    @Test
    void testVisitRationalNumber_denomIsOne() {
        Rationnal q = new Rationnal(Fraction.of(5, 1));
        printer.visit(q);
        assertEquals("5", printer.getResult());
    }

    @Test
    void testVisitRationalNumber_PREFIX() {
        printer = new Printer(Notation.PREFIX);
        Rationnal q = new Rationnal(Fraction.of(5, 6));
        printer.visit(q);
        assertEquals("/ 5 6", printer.getResult());
    }

    @Test
    void testVisitRationalNumber_INFIX() {
        Rationnal q = new Rationnal(Fraction.of(5, 6));
        printer.visit(q);
        assertEquals("5/6", printer.getResult());
    }

    @Test
    void testVisitRationalNumber_POSTFIX() {
        printer = new Printer(Notation.POSTFIX);
        Rationnal q = new Rationnal(Fraction.of(5, 6));
        printer.visit(q);
        assertEquals("5 6 /", printer.getResult());
    }

    @Test
    void testVisitRandomFunctions_INFIX() {
        printer.visit(RandomFunction.randomInteger(new IntegerAtom(100)));
        assertEquals("randint(100)", printer.getResult());
        printer = new Printer();
        printer.visit(RandomFunction.randomRational(new IntegerAtom(100)));
        assertEquals("randrat(100)", printer.getResult());
        printer = new Printer();
        printer.visit(RandomFunction.randomReal());
        assertEquals("randreal()", printer.getResult());
        printer = new Printer();
        printer.visit(RandomFunction.randomComplex());
        assertEquals("randcomplex()", printer.getResult());
    }

    @Test
    void testVisitRandomFunctions_POSTFIX() {
        printer = new Printer(Notation.POSTFIX);
        printer.visit(RandomFunction.randomInteger(new IntegerAtom(100)));
        assertEquals("(100)randint", printer.getResult());
        printer = new Printer(Notation.POSTFIX);
        printer.visit(RandomFunction.randomRational(new IntegerAtom(100)));
        assertEquals("(100)randrat", printer.getResult());
        printer = new Printer(Notation.POSTFIX);
        printer.visit(RandomFunction.randomReal());
        assertEquals("()randreal", printer.getResult());
        printer = new Printer(Notation.POSTFIX);
        printer.visit(RandomFunction.randomComplex());
        assertEquals("()randcomplex", printer.getResult());
    }

}
