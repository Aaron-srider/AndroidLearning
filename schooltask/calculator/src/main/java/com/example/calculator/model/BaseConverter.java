package com.example.calculator.model;

import static com.example.calculator.model.CategoryEnum.NUMSYS;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

import com.example.calculator.Observer;

import lombok.Data;

@Data
public class BaseConverter implements Converter {

    public static Integer HAS_RESULT = 1;
    public static Integer NOT_INIT = 2;

    Integer status = NOT_INIT;

    Observer observer;

    ConvertedNumber convertedNumber;

    UnitsEnum from;

    UnitsEnum to;

    ConvertedNumber result;

    public BaseConverter() {
    }

    public BaseConverter(Observer observer) {
        this.observer = observer;
    }

    public void convert() {
        if (getConvertedNumber().getUnit().getCategory().equals(NUMSYS)) {
            String value = convertedNumber.getValue().toString();
            int i = Integer.parseInt(value);
            if (to.equals(UnitsEnum.NS_D)) {
                result = new ConvertedNumber(new BigDecimal(i), UnitsEnum.NS_D);
                status = HAS_RESULT;
                notifyObserver();
            } else if (to.equals(UnitsEnum.NS_O)) {
                result = new ConvertedNumber(new BigDecimal(Integer.toOctalString(i)), UnitsEnum.NS_O);
                status = HAS_RESULT;
                notifyObserver();
            } else if (to.equals(UnitsEnum.NS_B)) {
                result = new ConvertedNumber(new BigDecimal(Integer.toBinaryString(i)), UnitsEnum.NS_B);
                status = HAS_RESULT;
                notifyObserver();
            }
        } else {
            BigDecimal conversion = Utils.conversion(convertedNumber.value, from, to);
            result = new ConvertedNumber();
            result.setUnit(to);
            result.setValue(conversion);
            status = HAS_RESULT;
            notifyObserver();
        }

    }

    public void setConvertedNumber(ConvertedNumber convertedNumber) {

        this.convertedNumber = convertedNumber;

    }

    public void setFrom(UnitsEnum from) {

        this.from = from;

    }

    public void setTo(UnitsEnum to) {

        this.to = to;

    }

    public ConvertedNumber getResult() {

        return result;

    }

    @Override
    public boolean canConvert() {
        if (from != null && to != null && convertedNumber != null) {
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        from = null;
        to = null;
        convertedNumber = null;
    }

    @Override
    public boolean hasResult() {
        return status.equals(HAS_RESULT);
    }

    public static void main(String[] args) {
        Converter baseConverter = new BaseConverter();

        baseConverter.setFrom(Utils.getUnitEnum("米"));
        baseConverter.setTo(Utils.getUnitEnum("毫米"));


        ConvertedNumber convertedNumber = new ConvertedNumber(new BigDecimal(0.00003), Utils.getUnitEnum("米"));
        baseConverter.setConvertedNumber(convertedNumber);

        baseConverter.convert();

        System.out.println();

        System.out.println(baseConverter.getResult().getValue().stripTrailingZeros().toPlainString());


        baseConverter.setFrom(Utils.getUnitEnum("立方厘米"));
        baseConverter.setTo(Utils.getUnitEnum("立方米"));


        convertedNumber = new ConvertedNumber(new BigDecimal(12), Utils.getUnitEnum("立方厘米"));
        baseConverter.setConvertedNumber(convertedNumber);

        baseConverter.convert();


        System.out.println(new BigDecimal(0.001).stripTrailingZeros().toPlainString());


        System.out.println(baseConverter.getResult());

        try {
            BigDecimal bigDecimal = new BigDecimal("100.00");
            BigDecimal bigDecimal2 = new BigDecimal("3");
            System.out.println(bigDecimal.divide(bigDecimal2));
        } catch (ArithmeticException ex) {
            BigDecimal bigDecimal = new BigDecimal("100.00");
            BigDecimal bigDecimal2 = new BigDecimal("3");
            System.out.println(bigDecimal.divide(bigDecimal2, 6, ROUND_HALF_EVEN));
        }


    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObserver() {
        observer.update(this);
    }
}
