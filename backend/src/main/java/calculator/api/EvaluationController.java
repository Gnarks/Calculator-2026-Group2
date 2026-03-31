package calculator.api;

import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/evaluate")
    public EvaluationResponse evaluateExpression(@RequestBody ExpressionDTO expression) {
        String expr = expression.getExpression();
        return evaluationService.evaluate(expr);
    }

}
