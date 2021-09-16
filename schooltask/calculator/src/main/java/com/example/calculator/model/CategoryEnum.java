package com.example.calculator.model;

public enum CategoryEnum {

        LENGTH("length", UnitsEnum.LG_M, "长度"),
        VOLUME("volume", UnitsEnum.VO_L, "体积"),
        NUMSYS("numberSystem", UnitsEnum.NS_D, "进制");

        private String name;
        private UnitsEnum base;
        private String description;

        CategoryEnum(String name, UnitsEnum base, String description) {
            this.name = name;
            this.base = base;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public UnitsEnum getBase() {
            return base;
        }

        public String getDescription() {
            return description;
        }
    }