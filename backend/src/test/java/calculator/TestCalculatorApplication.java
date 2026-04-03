package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import calculator.api.EvaluationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCalculatorApplication {

    @Autowired
    private EvaluationController evaluationController;

    @Test
    void contextLoads() {
        assertThat(evaluationController).isNotNull();
    }

}
