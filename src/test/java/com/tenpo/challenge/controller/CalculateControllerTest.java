package com.tenpo.challenge.controller;

import com.tenpo.challenge.model.CalculationResponse;
import com.tenpo.challenge.service.CalculationService;
import com.tenpo.challenge.service.LoggerService;
import com.tenpo.challenge.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculateController.class)
@Import(Validation.class)
public class CalculateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculationService calculationService;

    @MockitoBean
    private LoggerService loggerService;

    @Test
    public void testCalculateEndpoint_ValidInputs() throws Exception {
        CalculationResponse response = new CalculationResponse(new BigDecimal("30"), 10.0, new BigDecimal("33"));
        Mockito.when(calculationService.calculate(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(response);

        mockMvc.perform(get("/calculate")
                        .param("num1", "10")
                        .param("num2", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalSum").value(30))
                .andExpect(jsonPath("$.percentage").value(10.0))
                .andExpect(jsonPath("$.total").value(33));
    }

    @Test
    public void testCalculateEndpoint_InvalidNegativeInput() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("num1", "-5")
                        .param("num2", "20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}