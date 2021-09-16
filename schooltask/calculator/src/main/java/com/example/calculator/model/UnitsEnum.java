package com.example.calculator.model;

import java.math.BigDecimal;

public enum UnitsEnum {

    LG_M(CategoryEnum.LENGTH, "m", new String[]{"m", "米"}, new BigDecimal("1"), "米"),
    LG_CM(CategoryEnum.LENGTH, "cm", new String[]{"cm", "厘米"}, new BigDecimal("100"), "厘米"),
    LG_MM(CategoryEnum.LENGTH, "mm", new String[]{"mm", "毫米"}, new BigDecimal("1000"), "毫米"),

    VO_CM(CategoryEnum.VOLUME, "cm*cm*cm", new String[]{"立方厘米", "毫升"}, new BigDecimal("1000000"), "立方厘米"),
    VO_L(CategoryEnum.VOLUME, "dm*dm*dm", new String[]{"立方分米", "升"}, new BigDecimal("1000"), "升"),
    VO_M(CategoryEnum.VOLUME, "m*m*m", new String[]{"立方米"}, new BigDecimal("1"), "立方米"),

    NS_B(CategoryEnum.NUMSYS, "2", new String[]{"二进制"}, new BigDecimal("0"), "二进制"),
    NS_O(CategoryEnum.NUMSYS, "8", new String[]{"八进制"}, new BigDecimal("0"), "八进制"),
    NS_D(CategoryEnum.NUMSYS, "10", new String[]{"十进制"}, new BigDecimal("0"), "十进制"),



    UN_KNOWN(null, "未知", null, new BigDecimal("0"), "未知");

    private CategoryEnum category;
    private String units;
    private String[] possibleNames;
    private BigDecimal rate;
    private String description;

    UnitsEnum(CategoryEnum category, String units, String[] possibleNames, BigDecimal rate, String description) {
        this.category = category;
        this.units = units;
        this.possibleNames = possibleNames;
        this.rate = rate;
        this.description = description;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public String getUnits() {
        return units;
    }

    public String[] getPossibleNames() {
        return possibleNames;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

}