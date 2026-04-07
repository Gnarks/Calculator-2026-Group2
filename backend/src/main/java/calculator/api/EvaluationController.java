package calculator.api;

import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EvaluationController {

	private final EvaluationService evaluationService;

	public EvaluationController(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@GetMapping("/ping")
	public String pong() {
		return "pong";
	}

	@PostMapping("/evaluate")
	public EvaluationResponse evaluateExpression(@RequestBody ExpressionDTO expressionDTO) {
		return evaluationService.evaluate(expressionDTO.getExpression(), expressionDTO.getPrecision());
	}

	@GetMapping("/random")
	public EvaluationResponse getRandomNumber(@RequestParam() String type, @RequestParam() int max) {
		return evaluationService.getRandomNumber(type, max);
	}

}
