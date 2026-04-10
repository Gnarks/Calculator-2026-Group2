package calculator.api;

import calculator.api.dto.EvaluationResponse;
import calculator.api.dto.ExpressionDTO;
import calculator.atoms.AngleMode;
import calculator.atoms.AtomType;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for the calculator API.
 * Provides endpoints to evaluate mathematical expressions,
 * manage settings such as the angle mode, and generate random numbers.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EvaluationController {

        private final EvaluationService evaluationService;

        /**
         * Constructs a new EvaluationController with the given service.
         *
         * @param evaluationService the service handling the core calculation logic
         */
        public EvaluationController(EvaluationService evaluationService) {
                this.evaluationService = evaluationService;
        }

        /**
         * Simple health check endpoint.
         *
         * @return a plain string "pong" indicating the API is up and running
         */
        @GetMapping("/ping")
        public String pong() {
                return "pong";
        }

        /**
         * Evaluates an arithmetic expression provided in the request body.
         *
         * @param expressionDTO the Data Transfer Object containing the expression string and precision
         * @return an EvaluationResponse object with the evaluation result or error message
         */
        @PostMapping("/evaluate")
        public EvaluationResponse evaluateExpression(@RequestBody ExpressionDTO expressionDTO) {
                return evaluationService.evaluate(expressionDTO.getExpression(), expressionDTO.getPrecision());
        }

        /**
         * Updates the global angle mode used for trigonometric operations.
         *
         * @param angleMode the new angle mode to apply (e.g., RADIAN or DEGREE)
         */
        @PostMapping("/setAngleMode")
        public void setAngleMode(@RequestBody AngleMode angleMode) {
                evaluationService.setAngleMode(angleMode);
        }
    
        /**
         * Retrieves a random number generated according to the specified parameters.
         *
         * @param type the structural type of the random atom (e.g., Integer, Real)
         * @param max  the upper limit for the random number generation
         * @param seed an optional seed value for deterministic behavior (can be null)
         * @return an EvaluationResponse containing the generated number in string format
         */
}
