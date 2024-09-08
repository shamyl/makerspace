package com.shf.makerspace.Enum;

public enum TimePeriodEnum {
    DAYS_15("15 Days"),
    MONTH_1("1 Month"),
    MONTH_2("2 Month"),
    MONTH_3("3 Month"),
    MONTH_4("4 Month"),
    MONTH_5("5 Month"),
    MONTH_6("6 Month"),
    MONTH_7("7 Month"),
    MONTH_8("8 Month"),
    MONTH_9("9 Month"),
    MONTH_10("10 Month"),
    MONTH_11("11 Month"),
    MONTH_12("12 Month"),
    YEAR_1("1 Year");

    private String value;

    TimePeriodEnum(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
