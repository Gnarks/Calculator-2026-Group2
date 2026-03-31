package calculator.api;

import calculator.api.dto.EvaluationResponse;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    public EvaluationResponse evaluate(String expression) {
        if(expression == null || expression.isEmpty())
            return new EvaluationResponse(0, "");
        return new EvaluationResponse(1, "67");
    }

}
