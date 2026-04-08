package calculator.api;

import calculator.Calculator;
import calculator.Expression;
import calculator.antlr.Parser;
import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import calculator.atoms.AngleMode;
import calculator.atoms.Atom;
import calculator.atoms.Real;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

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

    public void setAngleMode(AngleMode angleMode) {
        if(angleMode != null)
            Calculator.mode = angleMode;
    }

}
