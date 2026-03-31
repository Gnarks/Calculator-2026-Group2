package calculator.api;

import calculator.Calculator;
import calculator.Expression;
import calculator.antlr.Parser;
import calculator.api.dto.EvaluationResponse;
import calculator.atoms.Atom;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    public EvaluationResponse evaluate(String expression) {
        if(expression == null || expression.isEmpty())
            return new EvaluationResponse(0, "");
        Parser parser = new Parser();
        Expression e = parser.parse(expression);
        Calculator c = new Calculator();
        Atom result = c.eval(e);
        return new EvaluationResponse(1, result.toString());
    }

}
