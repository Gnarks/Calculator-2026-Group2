package calculator.api;

import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import calculator.atoms.AngleMode;
import calculator.atoms.AtomType;
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

	@PostMapping("/setAngleMode")
	public void setAngleMode(@RequestBody AngleMode angleMode) {
		evaluationService.setAngleMode(angleMode);
    
	@GetMapping("/random")
	public EvaluationResponse getRandomNumber(@RequestParam() AtomType type, @RequestParam() int max,
											  @RequestParam(required = false) Long seed) {
		return evaluationService.getRandomNumber(type, max, seed);
	}

}
