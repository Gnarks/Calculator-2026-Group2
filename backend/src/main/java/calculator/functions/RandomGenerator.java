package calculator.functions;
import calculator.atoms.*;

import java.util.Random;

/**
 * A pseudorandom generator for all supported number domains (Integer, Rational, Real, Complex).
 * For testing purposes, it is possible to give a random seed so the generator can be tested deterministically.
 */
public class RandomGenerator {

    private final Random random;

    /**
     * Creates a new random generator.
     */
    public RandomGenerator() {
        this.random = new Random();
    }

    /**
     * Creates a new random generator with a specific seed for deterministic testing.
     * @param seed the initial seed
     */
    public RandomGenerator(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Sets the seed of this random generator.
     * @param seed the new seed
     */
    public void setSeed(long seed) {
        this.random.setSeed(seed);
    }

    /**
     * Provides a random integer between 0 and a given integer provided as input (inclusive).
     * @param max the maximum integer value (inclusive if max > 0). 
     * @return a random IntegerAtom
     */
    public IntegerAtom generateInteger(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Max must be positive");
        }
        if (max == 0) {
            return new IntegerAtom(0);
        }
        return new IntegerAtom(random.nextInt(max + 1));
    }

    /**
     * Provides a random rational a/b with a and b random integer between 0 and a given integer provided as input (b cannot be 0).
     * @param max the maximum integer value for a and b
     * @return a random Rationnal
     */
    public Rationnal generateRational(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("Max must be strictly positive to ensure b is not 0");
        }
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max) + 1; // [1, max]
        return new Rationnal(a, b);
    }

    /**
     * Provides a real value between 0 and 1.
     * @return a random Real
     */
    public Real generateReal() {
        return new Real(random.nextDouble());
    }

    /**
     * Provides a value a + i b where a and b are real values between 0 and 1.
     * @return a random Complex
     */
    public Complex generateComplex() {
        return new Complex(random.nextDouble(), random.nextDouble());
    }
}