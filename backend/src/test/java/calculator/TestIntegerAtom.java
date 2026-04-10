package calculator;

import calculator.atoms.IntegerAtom;
import calculator.atoms.Real;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntegerAtom {

    @Test
    void testToRadian() {
        IntegerAtom i = new IntegerAtom(90);
        Real converted = (Real) i.toRadian();
        assertEquals(Math.PI/2, converted.getValue().doubleValue());
    }

}
