package com.example.calculator.model;

import com.example.calculator.Observable;

public interface Converter extends Observable {
    public void convert();

    public void setConvertedNumber(ConvertedNumber convertedNumber);

    public void setFrom(UnitsEnum from);

    public void setTo(UnitsEnum to);

    public ConvertedNumber getResult();

    public boolean canConvert();

    public void reset();

    public boolean hasResult();

}
