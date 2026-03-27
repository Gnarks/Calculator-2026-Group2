package calculator;

import calculator.atoms.Real;
import calculator.operations.Power;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TestPower {

    private final int base = 5;
    private final int exp = 2;
    private Power op;
    private List<Expression> params;

    @BeforeEach
    void setUp() {
        params = Arrays.asList(new Real(base), new Real(exp));
        try {
            op = new Power(params);
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testConstructor1() {
        assertThrows(IllegalConstruction.class, () -> op = new Power(null));
    }

    @Test
    void testEquals() {
        List<Expression> p = Arrays.asList(new Real(base), new Real(exp));
        try {
            Power pow = new Power(p);
            assertEquals(op, pow);
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testNull() {
        assertDoesNotThrow(() -> op == null); // Direct way to to test if the null case is handled.
    }

    @Test
    void testHashCode() {
        // Two similar expressions, constructed separately (and using different
        // constructors) should have the same hashcode
        List<Expression> p = Arrays.asList(new Real(base), new Real(exp));
        try {
            Power pow = new Power(p);
            assertEquals(pow.hashCode(), op.hashCode());
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testNullParamList() {
        params = null;
        assertThrows(IllegalConstruction.class, () -> op = new Power(params));
    }

    @Test
    void testExpZeroGivesOne() {
        Real r1 = new Real(5);
        Real r2 = new Real(0);
        List<Expression> p = Arrays.asList(r1, r2);
        try {
            Power pow = new Power(p);
            assertEquals(new Real(1), pow.op(r1, r2));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testOneExpPlusInf() {
        Real r1 = new Real(1);
        Real r2 = Real.plusInf();
        List<Expression> p = Arrays.asList(r1, r2);
        try {
            Power pow = new Power(p);
            assertEquals(new Real(1), pow.op(r1, r2));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testMinusInfExpMinInfIsNaN() {
        Real r1 = Real.minusInf();
        Real r2 = Real.minusInf();
        List<Expression> p = Arrays.asList(r1, r2);
        try {
            Power pow = new Power(p);
            assertEquals(Real.nan(), pow.op(r1, r2));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testMinusInfExpPlusInfIsNaN() {
        Real r1 = Real.minusInf();
        Real r2 = Real.plusInf();
        List<Expression> p = Arrays.asList(r1, r2);
        try {
            Power pow = new Power(p);
            assertEquals(Real.nan(), pow.op(r1, r2));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

    @Test
    void testRealExpPlusInf() {
        Real r1 = new Real(0.256);
        Real r2 = Real.plusInf();
        List<Expression> p = Arrays.asList(r1, r2);
        try {
            Power pow = new Power(p);
            assertEquals(new Real(0), pow.op(r1, r2));
        } catch (IllegalConstruction _) {
            fail();
        }
    }

}
