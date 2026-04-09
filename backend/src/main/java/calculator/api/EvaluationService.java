package calculator.api;

import calculator.Calculator;
import calculator.Expression;
import calculator.antlr.Parser;
import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import calculator.atoms.AngleMode;
import calculator.atoms.Atom;
import calculator.atoms.AtomType;
import calculator.atoms.Real;
import calculator.functions.RandomFunction;
import calculator.functions.RandomGenerator;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    /**
     * Evaluates an expression with a given precision
     *
     * @param args the expression to evaluate
     * @param precision the numbers of digits after the comma
     * @return a structure containing the result if the evaluation and a code of success
     */
    public EvaluationResponse evaluate(String args, int precision) {
        if(args == null || args.isBlank() || precision < 0)
            return new EvaluationResponse(0, "");
        try {
            Parser parser = new Parser();
            Expression expression = parser.parse(args);
            Calculator calculator = new Calculator();
            Real.scale = precision;
            Atom result = calculator.eval(expression);
            return new EvaluationResponse(1, result.toString());
        } catch (Exception _) {
            return new EvaluationResponse(0, "");
        }
    }

    /**
     * Changes the calculator's angles representation.
     *
     * @param angleMode the desired representation
     */
    public void setAngleMode(AngleMode angleMode) {
        if(angleMode != null)
            Calculator.mode = angleMode;
    }
      
    /**
     * Generates a random number of the given type, with a maximum value in some cases.
     * The maximum value is used when generating an integer or a rational number.
     *
     * @param type the type of number to generate.
     * @param max the maximum value possible to generate
     * @return a structure containing a string representation of the random number and a success code
     */
    public EvaluationResponse getRandomNumber(AtomType type, int max, Long seed) {
        if(max <= 0)
            return new EvaluationResponse(0, "");
        String random;
        RandomGenerator generator = (seed == null) ? new RandomGenerator() : new RandomGenerator(seed);
        switch (type) {
            case INTEGER -> random = generator.generateInteger(max).toString();
            case RATIONNAL -> random = generator.generateRational(max).toString();
            case REAL -> random = generator.generateReal().toString();
            case COMPLEX -> random = generator.generateComplex().toString();
            default -> {
                return new EvaluationResponse(0, "");
            }
        }
        return new EvaluationResponse(1, random);
    }

}
