package calculator;

import calculator.api.EvaluationController;
import calculator.api.EvaluationService;
import calculator.api.dto.EvaluationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EvaluationController.class)
public class TestEvaluationController {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EvaluationService evaluationService;

    @Test
    void testResponseFromService() throws Exception {
        EvaluationResponse response = new EvaluationResponse(1, "25");
        when(evaluationService.evaluate("5*5", 64)).thenReturn(response);
        mockMvc.perform(post("/api/evaluate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"expression\":\"5*5\", \"precision\":64}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("25"))
                .andExpect(jsonPath("$.success").value("1"));
    }

}
