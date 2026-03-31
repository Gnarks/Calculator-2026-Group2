package calculator.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EvaluationController {

    @PostMapping("/evaluate")
    public EvaluationResponse evaluateExpression(@RequestBody ExpressionDTO expression) {
        String expr = expression.getExpression();
        return new EvaluationResponse(1, "");
    }

}
