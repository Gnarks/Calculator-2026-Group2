package calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import calculator.atoms.IntegerAtom;
import calculator.atoms.Real;
import calculator.functions.Log;
import calculator.functions.Sinus;

public class TestIntegerAtom {

    @Test
    void testToRadian() {
        IntegerAtom i = new IntegerAtom(90);
        Real converted = (Real) i.toRadian();
        assertEquals(Math.PI/2, converted.getValue().doubleValue());
    }

    @Test
    void applyUnaryFunctionOnInteger() {
        IntegerAtom base = new IntegerAtom(2);
        IntegerAtom i = new IntegerAtom(8);
        try {
            Log log = new Log(base, i);
            assertEquals(new Real(3), base.apply(log, i));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void applyBinaryFunctionOnInteger() {
        IntegerAtom i = new IntegerAtom(90);
        try {
            Sinus sinus = new Sinus(i);
            assertEquals(new IntegerAtom(1), i.apply(sinus));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

}
