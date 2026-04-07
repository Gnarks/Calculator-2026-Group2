package calculator.api;

import calculator.Calculator;
import calculator.Expression;
import calculator.antlr.Parser;
import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import calculator.atoms.Atom;
import calculator.atoms.AtomType;
import calculator.atoms.Real;
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
     * Generates a random number of the given type
     *
     * @param type the type of number to generate
     * @return a structure containing a string representation of the random number and a success code
     */
    public EvaluationResponse getRandomNumber(String type) {
        AtomType atomType;
        try {
            atomType = AtomType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new EvaluationResponse(0, "");
        }
        String random;
        switch (atomType) {
            case INTEGER -> random = "";
            case RATIONNAL -> random = "";
            case REAL -> random = "";
            case COMPLEX -> random = "";
            default -> {
                return new EvaluationResponse(0, "");
            }
        }
        return new EvaluationResponse(1, random);
    }

}
