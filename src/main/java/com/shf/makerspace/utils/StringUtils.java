/*
 * This is the exclusive property of Travelex Global Business Payments Limited and Travelex Global Business Payments Inc.
 * Copyright (c) 2011 Travelex Global Business Payments Limited and Travelex Global Business Payments Inc
 * All rights reserved.
 */
package com.shf.makerspace.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Taimour
 * Date: 11/02/21
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {
    private StringUtils() {
    }

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Truncate the string to given length. if string is null or empty then original value is returned.
     *
     * @param value     string to be truncated.
     * @param maxLength max length.
     * @return truncated string.
     */
    public static String truncate(String value, int maxLength) {
        if (!isNullOrEmpty(value) && value.length() > 0) {
            return value.length() > maxLength ? value.substring(0, maxLength) : value;
        }
        return value;
    }

    public static String truncateWithDots(String value, int maxLength) {
        if (!isNullOrEmpty(value) && value.length() > 0) {
            return value.length() > maxLength ? value.substring(0, maxLength).concat("...") : value;
        }
        return value;
    }

    /**
     * Checks given item is null or empty.
     *
     * @param searchItem string to check
     * @return true/false
     */
    public static boolean isSearchItemNullOrEmpty(String searchItem) {
        return searchItem == null || searchItem.isEmpty() || "-1".equals(searchItem);
    }

    /**
     * Utility mehtod to check if the string is a number or not.
     *
     * @param value String value
     * @return boolean
     */
    public static boolean isLong(String value) {
        try {
            if (value == null) {
                return false;
            }
            Long.parseLong(value.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Converts String to the List delimited by the parameter.
     *
     * @param str       the str
     * @param delimiter the delimiter
     * @return the list
     */
    public static List<String> convertStringToList(String str, String delimiter) {
        List<String> items = new ArrayList<>();
        if (!isNullOrEmpty(str) && !str.isEmpty()) {
            items = Arrays.asList(str.split("\\s*" + delimiter + "\\s*"));
        }
        return items;
    }

    /**
     * Check if string is null or empty.
     *
     * @param value the value
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Check if the object is null or empty.
     *
     * @param value the value
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(Object value) {
        return value == null || value.toString().trim().isEmpty();
    }

    /**
     * Evaluate the BigDecimal and return the empty string.
     *
     * @param value BigDecimal instance
     * @return String value of the big decimal or empty string
     */
    public static String getBigDecimalStringValue(BigDecimal value) {
        return null != value ? value.toString() : "";
    }

    /**
     * Validate string length.
     *
     * @param value  String value
     * @param length maximum allowed length.
     * @return true if length is less than equal to the defined length.
     */
    public static boolean tooLong(String value, int length) {
        return value.trim().length() > length;
    }

    /**
     * Check if string is null or empty.
     *
     * @param value  the value
     * @param length the length
     * @return true, if is too long
     */
    public static boolean isTooLong(String value, int length) {
        return value.trim().length() > length;
    }

    /**
     * Generate the comma separated list.
     *
     * @param stringCollection collection of strings
     * @return comma separated concatenation of input strings
     */
    public static String getCommaSeparatedString(Collection<String> stringCollection) {
        StringBuilder result = new StringBuilder();
        for (String string : stringCollection) {
            result.append(string);
            result.append(", ");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 2) : "";
    }

    public static String getJsonString(String name, String value) {
        return String.format("{\"%s\":\"%s\"}", name, value);
    }

    public static String firstCharToLower(String item) {
        return item.replaceFirst(item.substring(0, 1), item.substring(0, 1).toLowerCase());
    }

    public static String firstCharToUpper(String item) {
        return item.replaceFirst(item.substring(0, 1), item.substring(0, 1).toUpperCase());
    }

    public static String fromTableToLabel(String string) {
        final StringBuffer buff = new StringBuffer(firstCharToUpper(string));
        Character.toUpperCase(buff.charAt(0));
        for (int i = 1; i < buff.length(); i++) {
            if (Character.isUpperCase(buff.charAt(i)))
                buff.insert(i++, " ");
        }
        return buff.toString();
    }

    public static String getInnerProperty(String prop) {
        String naked = "";
        if (prop.contains("(")) {
            final int index = prop.indexOf("(");
            naked = prop.substring(index + 1, prop.length() - 1);
            return naked;
        }
        return prop;
    }

    public static void printResult(List<Map<String, Object>> result) {
        for (final Map<String, Object> map : result) {
            System.out.println(map.toString());
        }
    }

    public static boolean isEmpty(String string) {
        if (string == null)
            return true;
        else return string.trim().equals("");

    }

    public static boolean isEmpty(Integer string) {
        if (string == null)
            return true;
        else if (string == 0)
            return true;
        else
            return false;

    }

    public static boolean isNotEmpty(String string) {
        return !(isEmpty(string));
    }

    public static boolean isNotEmptyAndGreaterThanZero(Integer string) {
        return !(isEmpty(string));
    }

    public static boolean notBlank(String string) {
        return string != null && !string.isEmpty() && !string.equalsIgnoreCase("null");
    }

    public static String formatString(String str, String format) {
        SimpleDateFormat fromUser = new SimpleDateFormat(format);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reformattedStr = "";
        try {

            reformattedStr = myFormat.format(fromUser.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }
}