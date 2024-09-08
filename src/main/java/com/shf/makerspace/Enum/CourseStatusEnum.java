package com.shf.makerspace.Enum;

public enum CourseStatusEnum {
    AVAILABLE("Available"),
    COMING_SOON("Coming Soon");

    private String value;

    CourseStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
