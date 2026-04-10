package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import calculator.functions.RandomGenerator;
import calculator.atoms.IntegerAtom;
import calculator.atoms.Rationnal;
import calculator.atoms.Real;
import calculator.atoms.Complex;

class TestRandomGenerator {

    @Test
    void testIntegerDeterministic() {
        RandomGenerator rg1 = new RandomGenerator(42);
        RandomGenerator rg2 = new RandomGenerator(42);

        for (int i = 0; i < 100; i++) {
            IntegerAtom val1 = rg1.generateInteger(100);
            IntegerAtom val2 = rg2.generateInteger(100);
            assertEquals(val1, val2);
            assertTrue(val1.getValue() >= 0 && val1.getValue() <= 100);
        }
        
    }

    @Test
    void testRationalDeterministic() {
        RandomGenerator rg1 = new RandomGenerator(System.currentTimeMillis());
        rg1.setSeed(42);
        RandomGenerator rg2 = new RandomGenerator(42);

        for (int i = 0; i < 100; i++) {
            Rationnal val1 = rg1.generateRational(50);
            Rationnal val2 = rg2.generateRational(50);
            assertEquals(val1, val2);
        }

        RandomGenerator rg3 = new RandomGenerator(42);
        Rationnal val3 = rg3.generateRational(1);
        assertTrue(val3.equals(new Rationnal(0, 1)) || val3.equals(new Rationnal(1, 1)), 
            "Value should be 0/1 or 1/1");
    }

    @Test
    void testRealDeterministic() {
        RandomGenerator rg1 = new RandomGenerator(12345);
        RandomGenerator rg2 = new RandomGenerator(12345);

        for (int i = 0; i < 100; i++) {
            Real val1 = rg1.generateReal();
            Real val2 = rg2.generateReal();
            assertEquals(val1, val2);
        }
    }

    @Test
    void testComplexDeterministic() {
        RandomGenerator rg1 = new RandomGenerator(999);
        RandomGenerator rg2 = new RandomGenerator(999);

        for (int i = 0; i < 100; i++) {
            Complex val1 = rg1.generateComplex();
            Complex val2 = rg2.generateComplex();
            assertEquals(val1, val2);
        }
    }
}