package calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("calculator.api")
public class CalculatorApplication {

	/**
	 * The application's entry point
	 *
	 */
	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

}
