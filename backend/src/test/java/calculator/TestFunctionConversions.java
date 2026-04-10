package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import calculator.atoms.*;
import calculator.functions.*;

class TestFunctionConversions {

    @Test
    void testUnaryInteger() throws Exception {
        IntegerAtom i0 = new IntegerAtom(0);
        IntegerAtom i1 = new IntegerAtom(1);
        IntegerAtom i4 = new IntegerAtom(4);

        assertEquals(new IntegerAtom((int) Math.round(Math.sin(0))), new Sinus(i0).op(i0));
        assertEquals(new IntegerAtom((int) Math.round(Math.cos(0))), new Cosinus(i0).op(i0));
        assertEquals(new IntegerAtom((int) Math.round(Math.tan(0))), new Tangente(i0).op(i0));
        
        assertEquals(new IntegerAtom((int) Math.round(Math.asin(0))), new Arcsinus(i0).op(i0));
        assertEquals(new IntegerAtom((int) Math.round(Math.acos(1))), new Arccosinus(i1).op(i1));
        assertEquals(new IntegerAtom((int) Math.round(Math.atan(0))), new Arctangente(i0).op(i0));
        
        assertEquals(new IntegerAtom((int) Math.round(Math.sinh(0))), new Sinh(i0).op(i0));
        assertEquals(new IntegerAtom((int) Math.round(Math.cosh(0))), new Cosh(i0).op(i0));
        assertEquals(new IntegerAtom((int) Math.round(Math.tanh(0))), new Tanh(i0).op(i0));
        
        assertEquals(new IntegerAtom((int) Math.round(Math.log(1))), new Ln(i1).op(i1));
        assertEquals(new IntegerAtom((int) Math.round(Math.sqrt(4))), new Sqrt(i4).op(i4));
    }

    @Test
    void testUnaryRational() throws Exception {
        Rationnal r0 = new Rationnal(0, 1);
        Rationnal r1 = new Rationnal(1, 1);
        Rationnal r4 = new Rationnal(4, 1);

        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.sin(0))), new Sinus(r0).op(r0));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.cos(0))), new Cosinus(r0).op(r0));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.tan(0))), new Tangente(r0).op(r0));
        
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.asin(0))), new Arcsinus(r0).op(r0));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.acos(1))), new Arccosinus(r1).op(r1));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.atan(0))), new Arctangente(r0).op(r0));
        
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.sinh(0))), new Sinh(r0).op(r0));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.cosh(0))), new Cosh(r0).op(r0));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.tanh(0))), new Tanh(r0).op(r0));
        
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.log(1))), new Ln(r1).op(r1));
        assertEquals(new Rationnal(org.apache.commons.numbers.fraction.Fraction.from(Math.sqrt(4))), new Sqrt(r4).op(r4));
    }

    @Test
    void testUnaryComplex() throws Exception {
        Complex c0 = new Complex(0, 0);
        Complex c1 = new Complex(1, 0);
        Complex c4 = new Complex(4, 0);

        assertEquals(new Complex(c0.getValue().sin()), new Sinus(c0).op(c0));
        assertEquals(new Complex(c0.getValue().cos()), new Cosinus(c0).op(c0));
        assertEquals(new Complex(c0.getValue().tan()), new Tangente(c0).op(c0));
        
        assertEquals(new Complex(c0.getValue().asin()), new Arcsinus(c0).op(c0));
        assertEquals(new Complex(c1.getValue().acos()), new Arccosinus(c1).op(c1));
        assertEquals(new Complex(c0.getValue().atan()), new Arctangente(c0).op(c0));
        
        assertEquals(new Complex(c0.getValue().sinh()), new Sinh(c0).op(c0));
        assertEquals(new Complex(c0.getValue().cosh()), new Cosh(c0).op(c0));
        assertEquals(new Complex(c0.getValue().tanh()), new Tanh(c0).op(c0));
        
        assertEquals(new Complex(c1.getValue().log()), new Ln(c1).op(c1));
        assertEquals(new Complex(c4.getValue().sqrt()), new Sqrt(c4).op(c4));
    }

    @Test
    void testLogFunction() throws Exception {
        IntegerAtom i10 = new IntegerAtom(10);
        IntegerAtom i100 = new IntegerAtom(100);
        Log logInt = new Log(i10, i100);
        assertNotNull(logInt.op(i10, i100));

        Rationnal r10 = new Rationnal(10, 1);
        Rationnal r100 = new Rationnal(100, 1);
        Log logRat = new Log(r10, r100);
        assertNotNull(logRat.op(r10, r100));

        Complex c10 = new Complex(10, 0);
        Complex c100 = new Complex(100, 0);
        Log logComp = new Log(c10, c100);
        assertNotNull(logComp.op(c10, c100));
    }
}
