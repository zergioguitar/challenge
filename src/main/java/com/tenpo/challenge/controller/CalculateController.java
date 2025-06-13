package com.tenpo.challenge.controller;

import com.tenpo.challenge.model.CalculationResponse;
import com.tenpo.challenge.service.CalculationService;
import com.tenpo.challenge.validation.Validation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/calculate", produces = {"application/json"})
public class CalculateController {
    private final CalculationService calculationService;
    private final Validation validation;

    public CalculateController(CalculationService calculationService,
                               Validation validation) {
        this.calculationService = calculationService;
        this.validation = validation;
    }

    @GetMapping
    public CalculationResponse calculate(
            @RequestParam("num1") BigDecimal num1,
            @RequestParam("num2") BigDecimal num2) {
        validation.validateBigDecimal(num1);
        validation.validateBigDecimal(num2);
        return calculationService.calculate(num1, num2);
    }
}
