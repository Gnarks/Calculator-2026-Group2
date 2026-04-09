package calculator.atoms.visitor;

import calculator.atoms.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TestAtomCaster {

    @Test
    void testRealCasting() {
        Real real = new Real(new BigDecimal("3.14"));

        AtomCaster realCaster = new AtomCaster(AtomType.REAL);
        realCaster.visit(real);
        assertSame(real, realCaster.getResult());

        AtomCaster intCaster = new AtomCaster(AtomType.INTEGER);
        assertThrows(IllegalAtomCast.class, () -> intCaster.visit(real));

        AtomCaster ratCaster = new AtomCaster(AtomType.RATIONNAL);
        ratCaster.visit(real);
        assertTrue(ratCaster.getResult() instanceof Rationnal);
        assertEquals(3.14, ((Rationnal) ratCaster.getResult()).getValue().doubleValue(), 0.001);

        AtomCaster compCaster = new AtomCaster(AtomType.COMPLEX);
        compCaster.visit(real);
        assertTrue(compCaster.getResult() instanceof Complex);
        assertEquals(3.14, ((Complex) compCaster.getResult()).getValue().getReal(), 0.001);
        assertEquals(0, ((Complex) compCaster.getResult()).getValue().getImaginary(), 0.001);
    }

    @Test
    void testRealCastingSpecialValues() {
        Real nanReal = Real.nan();
        Real infReal = Real.plusInf();
        Real minInfReal = Real.minusInf();

        AtomCaster ratCaster = new AtomCaster(AtomType.RATIONNAL);
        assertThrows(IllegalAtomCast.class, () -> ratCaster.visit(nanReal));
        assertThrows(IllegalAtomCast.class, () -> ratCaster.visit(infReal));
        assertThrows(IllegalAtomCast.class, () -> ratCaster.visit(minInfReal));

        AtomCaster compCaster = new AtomCaster(AtomType.COMPLEX);
        compCaster.visit(nanReal);
        assertTrue(((Complex) compCaster.getResult()).getValue().getReal() != ((Complex) compCaster.getResult()).getValue().getReal()); // NaN Check
    }

    @Test
    void testComplexCasting() {
        Complex complex = new Complex(2, 3);

        AtomCaster compCaster = new AtomCaster(AtomType.COMPLEX);
        compCaster.visit(complex);
        assertSame(complex, compCaster.getResult());

        AtomCaster realCaster = new AtomCaster(AtomType.REAL);
        assertThrows(IllegalAtomCast.class, () -> realCaster.visit(complex));

        AtomCaster intCaster = new AtomCaster(AtomType.INTEGER);
        assertThrows(IllegalAtomCast.class, () -> intCaster.visit(complex));

        AtomCaster ratCaster = new AtomCaster(AtomType.RATIONNAL);
        assertThrows(IllegalAtomCast.class, () -> ratCaster.visit(complex));
    }

    @Test
    void testRationnalCasting() {
        Rationnal rationnal = new Rationnal(1, 2);

        AtomCaster ratCaster = new AtomCaster(AtomType.RATIONNAL);
        ratCaster.visit(rationnal);
        assertSame(rationnal, ratCaster.getResult());

        AtomCaster realCaster = new AtomCaster(AtomType.REAL);
        realCaster.visit(rationnal);
        assertTrue(realCaster.getResult() instanceof Real);
        assertEquals(0.5, ((Real) realCaster.getResult()).getValue().doubleValue(), 0.001);

        AtomCaster compCaster = new AtomCaster(AtomType.COMPLEX);
        compCaster.visit(rationnal);
        assertTrue(compCaster.getResult() instanceof Complex);
        assertEquals(0.5, ((Complex) compCaster.getResult()).getValue().getReal(), 0);
        assertEquals(0, ((Complex) compCaster.getResult()).getValue().getImaginary(), 0);

        AtomCaster intCaster = new AtomCaster(AtomType.INTEGER);
        assertThrows(IllegalAtomCast.class, () -> intCaster.visit(rationnal));
    }

    @Test
    void testIntegerCasting() {
        IntegerAtom integer = new IntegerAtom(5);

        AtomCaster intCaster = new AtomCaster(AtomType.INTEGER);
        intCaster.visit(integer);
        assertSame(integer, intCaster.getResult());

        AtomCaster realCaster = new AtomCaster(AtomType.REAL);
        realCaster.visit(integer);
        assertTrue(realCaster.getResult() instanceof Real);
        assertEquals(5.0, ((Real) realCaster.getResult()).getValue().doubleValue(), 0.001);

        AtomCaster ratCaster = new AtomCaster(AtomType.RATIONNAL);
        ratCaster.visit(integer);
        assertTrue(ratCaster.getResult() instanceof Rationnal);
        assertEquals(5, ((Rationnal) ratCaster.getResult()).getNumerator());
        assertEquals(1, ((Rationnal) ratCaster.getResult()).getDenominator());

        AtomCaster compCaster = new AtomCaster(AtomType.COMPLEX);
        compCaster.visit(integer);
        assertTrue(compCaster.getResult() instanceof Complex);
        assertEquals(5, ((Complex) compCaster.getResult()).getValue().getReal(), 0);
        assertEquals(0, ((Complex) compCaster.getResult()).getValue().getImaginary(), 0);
    }
}
