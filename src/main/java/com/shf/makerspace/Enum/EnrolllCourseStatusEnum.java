package com.shf.makerspace.Enum;

public enum EnrolllCourseStatusEnum {
    ONGOING("Ongoing"),
    COMPLETE("Complete");

    private String value;

    EnrolllCourseStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
