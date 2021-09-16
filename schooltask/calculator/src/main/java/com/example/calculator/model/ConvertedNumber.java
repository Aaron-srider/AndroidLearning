package com.example.calculator.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ConvertedNumber {
    BigDecimal value;
    UnitsEnum unit;

    public ConvertedNumber() {
    }

    public ConvertedNumber(BigDecimal value, UnitsEnum unit) {
        this.value = value;
        this.unit = unit;
    }
}
