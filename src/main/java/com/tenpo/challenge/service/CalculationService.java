package com.tenpo.challenge.service;

import com.tenpo.challenge.gateway.PercentageClient;
import com.tenpo.challenge.model.CalculationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculationService {
    @Autowired
    PercentageClient percentageClient;

    public CalculationResponse calculate(BigDecimal num1, BigDecimal num2) {
        double percentage = percentageClient.getPercentage();
        BigDecimal sum = num1.add(num2);
        BigDecimal total = sum.add(sum.multiply(BigDecimal.valueOf(percentage/100)));
        return new CalculationResponse(sum, percentage,total);
    }
}
