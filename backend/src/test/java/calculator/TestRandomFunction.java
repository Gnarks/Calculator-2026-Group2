package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import calculator.functions.RandomFunction;
import calculator.atoms.AtomType;
import calculator.atoms.IntegerAtom;

class TestRandomFunction {

    @Test
    void testConstructorsAndProperties() {
        IntegerAtom max = new IntegerAtom(10);
        IntegerAtom seed = new IntegerAtom(42);

        RandomFunction randintMax = RandomFunction.randomInteger(max);
        assertEquals(AtomType.INTEGER, randintMax.getType());
        assertEquals(1, randintMax.getArgs().size());
        assertFalse(randintMax.hasSeed());

        RandomFunction randintSeedMax = RandomFunction.randomInteger(seed, max);
        assertEquals(AtomType.INTEGER, randintSeedMax.getType());
        assertEquals(2, randintSeedMax.getArgs().size());
        assertTrue(randintSeedMax.hasSeed());

        RandomFunction randratMax = RandomFunction.randomRational(max);
        assertEquals(AtomType.RATIONNAL, randratMax.getType());
        assertEquals(1, randratMax.getArgs().size());
        assertFalse(randratMax.hasSeed());

        RandomFunction randratSeedMax = RandomFunction.randomRational(seed, max);
        assertEquals(AtomType.RATIONNAL, randratSeedMax.getType());
        assertEquals(2, randratSeedMax.getArgs().size());
        assertTrue(randratSeedMax.hasSeed());

        RandomFunction randreal = RandomFunction.randomReal();
        assertEquals(AtomType.REAL, randreal.getType());
        assertEquals(0, randreal.getArgs().size());
        assertFalse(randreal.hasSeed());

        RandomFunction randrealSeed = RandomFunction.randomReal(seed);
        assertEquals(AtomType.REAL, randrealSeed.getType());
        assertEquals(1, randrealSeed.getArgs().size());
        assertTrue(randrealSeed.hasSeed());

        RandomFunction randcomplex = RandomFunction.randomComplex();
        assertEquals(AtomType.COMPLEX, randcomplex.getType());
        assertEquals(0, randcomplex.getArgs().size());
        assertFalse(randcomplex.hasSeed());

        RandomFunction randcomplexSeed = RandomFunction.randomComplex(seed);
        assertEquals(AtomType.COMPLEX, randcomplexSeed.getType());
        assertEquals(1, randcomplexSeed.getArgs().size());
        assertTrue(randcomplexSeed.hasSeed());
    }

    @Test
    void testEquals() {
        IntegerAtom m1 = new IntegerAtom(10);
        IntegerAtom m2 = new IntegerAtom(10);
        IntegerAtom m3 = new IntegerAtom(20);

        RandomFunction rf1 = RandomFunction.randomInteger(m1);
        RandomFunction rf2 = RandomFunction.randomInteger(m2);
        RandomFunction rf3 = RandomFunction.randomInteger(m3);

        assertEquals(rf1, rf2);
        assertNotEquals(rf1, rf3);
        assertNotEquals(rf1, null);
        assertNotEquals(rf1, m1);
    }
}