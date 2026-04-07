package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.IntegerAtom;
import calculator.functions.RandomFunction;
import calculator.atoms.Atom;
import calculator.atoms.Complex;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import visitor.Evaluator;
import visitor.Printer;
import visitor.Counter;

class TestRandomEvaluator {

    @Test
    void testEvaluatorInteger() {
        IntegerAtom max = new IntegerAtom(100);
        IntegerAtom seed = new IntegerAtom(42);

        RandomFunction randintMax = RandomFunction.randomInteger(max);
        Evaluator eval = new Evaluator();
        randintMax.accept(eval);
        Atom res1 = eval.getResult();
        assertTrue(res1 instanceof IntegerAtom);

        RandomFunction randintSeed = RandomFunction.randomInteger(seed, max);
        randintSeed.accept(eval);
        Atom res2 = eval.getResult();
        assertTrue(res2 instanceof IntegerAtom);
        assertTrue(((IntegerAtom) res2).getValue() >= 0 && ((IntegerAtom) res2).getValue() <= 100);
    }

    @Test
    void testEvaluatorRational() {
        IntegerAtom max = new IntegerAtom(100);
        IntegerAtom seed = new IntegerAtom(42);

        RandomFunction randratMax = RandomFunction.randomRational(max);
        Evaluator eval = new Evaluator();
        randratMax.accept(eval);
        Atom res1 = eval.getResult();
        assertTrue(res1 instanceof Rationnal);

        RandomFunction randratSeed = RandomFunction.randomRational(seed, max);
        randratSeed.accept(eval);
        Atom res2 = eval.getResult();
        assertTrue(res2 instanceof Rationnal);
    }

    @Test
    void testEvaluatorReal() {
        IntegerAtom seed = new IntegerAtom(42);

        RandomFunction randreal = RandomFunction.randomReal();
        Evaluator eval = new Evaluator();
        randreal.accept(eval);
        Atom res1 = eval.getResult();
        assertTrue(res1 instanceof Real);

        RandomFunction randrealSeed = RandomFunction.randomReal(seed);
        randrealSeed.accept(eval);
        Atom res2 = eval.getResult();
        assertTrue(res2 instanceof Real);
    }

    @Test
    void testEvaluatorComplex() {
        IntegerAtom seed = new IntegerAtom(42);

        RandomFunction randcomplex = RandomFunction.randomComplex();
        Evaluator eval = new Evaluator();
        randcomplex.accept(eval);
        Atom res1 = eval.getResult();
        assertTrue(res1 instanceof Complex);

        RandomFunction randcomplexSeed = RandomFunction.randomComplex(seed);
        randcomplexSeed.accept(eval);
        Atom res2 = eval.getResult();
        assertTrue(res2 instanceof Complex);
    }

    @Test
    void testPrinter() {
        IntegerAtom max = new IntegerAtom(100);
        IntegerAtom seed = new IntegerAtom(42);
        
        RandomFunction randintSeed = RandomFunction.randomInteger(seed, max);
        Printer prefixPrinter = new Printer(Notation.PREFIX);
        randintSeed.accept(prefixPrinter);
        assertEquals("randint(42, 100)", prefixPrinter.getResult());

        Printer infixPrinter = new Printer(Notation.INFIX);
        randintSeed.accept(infixPrinter);
        assertEquals("randint(42, 100)", infixPrinter.getResult());

        Printer postfixPrinter = new Printer(Notation.POSTFIX);
        randintSeed.accept(postfixPrinter);
        assertEquals("(42, 100)randint", postfixPrinter.getResult());
    }

    @Test
    void testCounter() {
        IntegerAtom max = new IntegerAtom(100);
        IntegerAtom seed = new IntegerAtom(42);

        RandomFunction randintSeed = RandomFunction.randomInteger(seed, max);
        Counter counter = new Counter();
        randintSeed.accept(counter);
        assertEquals(1, counter.getNbOps());
        assertEquals(2, counter.getNbNbs());
    }

    @Test
    void testEvaluatorThrowsExceptionOnNonIntegerArgs() {
        Real badArg = new Real(5.0);
        RandomFunction badFunc = RandomFunction.randomInteger(badArg);
        Evaluator eval = new Evaluator();
        assertThrows(UnsupportedOperationException.class, () -> {
            badFunc.accept(eval);
        });
    }
}