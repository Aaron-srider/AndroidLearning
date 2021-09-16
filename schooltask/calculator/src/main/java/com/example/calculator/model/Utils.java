package com.example.calculator.model;

import java.math.BigDecimal;

public class Utils {
    public static BigDecimal conversion(BigDecimal value, UnitsEnum original, UnitsEnum need) {
        return conversion(value, original, need, 4);
    }

    /**
     * @param value    原始数值
     * @param original 原始单位
     * @param need     转换的单位
     * @param scale    小数点位数
     * @return
     */
    public static BigDecimal conversion(BigDecimal value, UnitsEnum original, UnitsEnum need, int scale) {
        if (original == UnitsEnum.UN_KNOWN || need == UnitsEnum.UN_KNOWN) {
            throw new IllegalArgumentException("存在不支持的单位参数");
        }
        if (original.getCategory() != need.getCategory()) {
            throw new IllegalArgumentException("转换单位不统一!" + original.getCategory().getName()+ "不能转换为" + need.getCategory().getName());
        }
        return value == null ? new BigDecimal("0") : value.multiply(need.getRate()).divide(original.getRate(), scale, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 默认保留两位小数,四舍五入
     *
     * @param value    原始数值
     * @param original 原始单位
     * @param need     转换的单位
     * @return
     */
    public static BigDecimal conversion(BigDecimal value, String original, String need) {
        return conversion(value, getUnitEnum(original), getUnitEnum(need));
    }

    public static UnitsEnum getUnitEnum(String unit) {
        if (unit!=null && !unit.equals("")) {
            for (UnitsEnum unitEnum : UnitsEnum.values()) {
                for (String possibleName : unitEnum.getPossibleNames()) {
                    if (possibleName.equals(unit.toLowerCase())) {
                        return unitEnum;
                    }
                }
            }
        }
        return UnitsEnum.UN_KNOWN;
    }
}
