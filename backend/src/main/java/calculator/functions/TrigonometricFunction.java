package calculator.functions;

import calculator.Expression;
import calculator.IllegalConstruction;

/**
 * Represents the trigonometric functions sin, cos and tan.
 *
 */
public abstract class TrigonometricFunction extends UnaryFunction {

    /**
     * To construct a unary operation with an expression as argument
     *
     * @param arg The expression passed as argument to the arithmetic operation
     * @throws IllegalConstruction Exception thrown if a null argument is passed
     */
    protected TrigonometricFunction(Expression arg) throws IllegalConstruction {
        super(arg);
    }

}
