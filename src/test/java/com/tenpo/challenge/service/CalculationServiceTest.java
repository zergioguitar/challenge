package com.tenpo.challenge.service;

import com.tenpo.challenge.gateway.PercentageClient;
import com.tenpo.challenge.model.CalculationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalculationServiceTest {

    private PercentageClient percentageClient;
    private CalculationService calculationService;

    @BeforeEach
    public void setup() {
        percentageClient = mock(PercentageClient.class);
        calculationService = new CalculationService();
        calculationService.percentageClient = percentageClient;
    }

    @Test
    public void testCalculate_Success() {
        when(percentageClient.getPercentage()).thenReturn(10.0);

        BigDecimal num1 = new BigDecimal("10");
        BigDecimal num2 = new BigDecimal("20");

        CalculationResponse result = calculationService.calculate(num1, num2);

        assertEquals(new BigDecimal("30"), result.getOriginalSum());
        assertEquals(10.0, result.getPercentage());
        assertEquals(new BigDecimal("33"), result.getTotal());
    }

    @Test
    public void testCalculate_WhenPercentageServiceFails_ShouldThrowException() {
        when(percentageClient.getPercentage()).thenThrow(new RuntimeException("Percentage service unavailable"));

        BigDecimal num1 = new BigDecimal("5");
        BigDecimal num2 = new BigDecimal("5");

        assertThrows(RuntimeException.class, () ->
                calculationService.calculate(num1, num2)
        );
    }
}
