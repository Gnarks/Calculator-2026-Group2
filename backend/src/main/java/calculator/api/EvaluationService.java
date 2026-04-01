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
        if(args == null || args.isBlank())
            return new EvaluationResponse(0, "");
        try {
            Parser parser = new Parser();
            Expression expression = parser.parse(args);
            Calculator calculator = new Calculator();
            Atom result = calculator.eval(expression);
            return new EvaluationResponse(1, result.toString());
        } catch (Exception _) {
            return new EvaluationResponse(0, "");
        }
    }

}
