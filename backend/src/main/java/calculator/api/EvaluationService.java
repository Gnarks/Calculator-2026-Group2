package calculator.api;

import calculator.Calculator;
import calculator.Expression;
import calculator.antlr.Parser;
import calculator.api.dto.EvaluationResponse;
import calculator.atoms.Atom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    public EvaluationResponse evaluate(String args) {
        if(args == null || args.isEmpty())
            return new EvaluationResponse(0, "");
        Parser parser = new Parser();
        Expression expression = parser.parse(args);
        Calculator calculator = new Calculator();
        Atom result;
        try {
            result = calculator.eval(expression);
        } catch (ArithmeticException _) {
            return new EvaluationResponse(0, "");
        }
        return new EvaluationResponse(1, result.toString());
    }

}
