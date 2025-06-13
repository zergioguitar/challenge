package com.tenpo.challenge.validation;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Validation {
    public void validateBigDecimal(BigDecimal bigdecimal) {
        if(bigdecimal==null){
            throw new IllegalArgumentException("parameter provided can't be null");
        }
        if(BigDecimal.ZERO.compareTo(bigdecimal) >= 0){
            throw new IllegalArgumentException("parameter must be greater than zero");
        }
    }
}
