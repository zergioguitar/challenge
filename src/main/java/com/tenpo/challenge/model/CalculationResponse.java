package com.tenpo.challenge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationResponse {
    private BigDecimal originalSum;
    private double percentage;
    private BigDecimal total;

    public CalculationResponse(BigDecimal originalSum, double percentage, BigDecimal total) {
        this.originalSum = originalSum.setScale(0, RoundingMode.UP);
        this.percentage = percentage;
        this.total = total.setScale(0, RoundingMode.UP);
    }

    public BigDecimal getOriginalSum() {
        return originalSum;
    }

    public double getPercentage() {
        return percentage;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setOriginalSum(BigDecimal originalSum) {
        this.originalSum = originalSum;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CalculationResponse{" +
                "originalSum=" + originalSum +
                ", percentage=" + percentage +
                ", total=" + total +
                '}';
    }
}

