package com.shf.makerspace.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.shf.makerspace.utils.StringUtils.DATE_TIME_FORMAT;

public class DateUtil {

    public static DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("[yyyy-M-d ][d/M/yyyy][d-M-yyyy][yyyy-MM-dd]");
    public static DateTimeFormatter europeanDateTimeFormatter = DateTimeFormatter.ofPattern("[yyyy-M-d HH:mm][d/M/yyyy HH:mm][d-M-yyyy HH:mm][yyyy-MM-dd HH:mm][yyyy-M-d HH:mm:ss][yyyy-MM-dd HH:mm:ss]");
    public static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void date() {
        long instance = Calendar.getInstance().getTimeInMillis();
        LocalDate date =
                Instant.ofEpochMilli(instance)
                        .atZone(ZoneOffset.UTC.normalized())
                        .toLocalDate();
        System.out.println(instance + " " + date.toEpochDay() + " " + date);
    }

    public static LocalDateTime startOfMonth(int month, int year) {
        return startOfDay(1, month, year);
    }

    public static LocalDateTime startOfMonth(String month, String year) {
        return startOfDay(1, Integer.parseInt(month), Integer.parseInt(year));
    }

    public static long startOfMonthInMilli(int month, int year) {
        return dateTimeToEpochMilli(startOfDay(1, month, year));
    }

    public static LocalDateTime startOfDay(int day, int month, int year) {
        return LocalDate.of(year, month, day).atStartOfDay();
    }

    public static BigDecimal getDateDiffInMinutes(Date date1, Date date2, TimeUnit timeUnit) {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setTime(date1);
        endDate.setTime(date2);

        long diffInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
        long convertMilliToSeconds = (diffInMillis / 1000);
        convertMilliToSeconds = convertMilliToSeconds / 60;
        return BigDecimal.valueOf(convertMilliToSeconds);
    }

    static public Date setTimeZeroForSelectedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 00);
        return cal.getTime();
    }

    public static long startOfDayInMilli(int day, int month, int year) {
        return dateTimeToEpochMilli(startOfDay(day, month, year));
    }

    public static long startOfDayInMilli(String day, String month, String year) {
        return dateTimeToEpochMilli(startOfDay(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year)));
    }

    public static long dateTimeToEpochMilli(LocalDateTime dateTime) {
        return ZonedDateTime.of(dateTime, ZoneOffset.UTC.normalized()).toInstant().toEpochMilli();
    }

    public static LocalDateTime endOfMonth(int month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);
        return LocalDate.of(year, month, date.lengthOfMonth()).atTime(LocalTime.MAX);
    }

    public static long endOfMonthInMilli(int month, int year) {
        return dateTimeToEpochMilli(endOfMonth(month, year));
    }

    public static LocalDateTime endOfDay(int day, int month, int year) {
        return LocalDate.of(year, month, day).atTime(LocalTime.MAX);
    }

    public static long endOfDayInMilli(int day, int month, int year) {
        return dateTimeToEpochMilli(endOfDay(day, month, year));
    }

    public static LocalDateTime dateTimeFromMilli(long millisec) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millisec), ZoneOffset.UTC.normalized());
    }

    public static long now() {
        return Instant.now().toEpochMilli();
    }

    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date);
    }

    public static LocalDate convertStringToLocalDate(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(date, formatter);
    }

    public static LocalDateTime convertStringToLocalDateTime(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, formatter);
    }

    public static LocalDateTime convertLocalDateTimeToUtc(LocalDateTime dateTime) {
        ZonedDateTime ldtZoned = dateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }

    public static LocalDateTime convertStringDateToUtc(String date, String format) {
        return convertLocalDateTimeToUtc(convertStringToLocalDateTime(date, format));
    }

    public static String convertLocalDateTimeToString(LocalDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

    }

    public static LocalDateTime convertLocalDateTimeToUtcWithZone(LocalDateTime dateTime, String zone) {
        ZonedDateTime ldtZoned = dateTime.atZone(ZoneId.of(zone));
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZoned.toLocalDateTime();
    }

    public static LocalDateTime convertStringLocalDateTimeToUtcWithZone(String date, String format, String zone) {
        return convertLocalDateTimeToUtcWithZone(convertStringToLocalDateTime(date, format), zone);
    }

    public static LocalDateTime getStartOfDay(LocalDateTime dateTime) {

        return dateTime.toLocalDate().atTime(00, 00, 00);
    }

    public static LocalDateTime getEndOfDay(LocalDateTime dateTime) {

        return dateTime.toLocalDate().atTime(23, 59, 59);
    }

    public static String convertLocalDateToString(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);

    }

    public static List<LocalDate> generateDatesBetweenDates(LocalDate startDate, LocalDate endDate) {

        List<LocalDate> totalDays = startDate.datesUntil(endDate)
                .collect(Collectors.toList());
        totalDays.add(endDate);
        return totalDays;
    }

    public static Date parseDate(String date) {
        return DateUtil.parseDate(LocalDate.parse(date, europeanDateFormatter), false);
    }

    public static Date parseDate(String date, Boolean addDate) {
        return DateUtil.parseDate(LocalDate.parse(date, europeanDateFormatter), addDate);
    }

    public static Date parseDate(LocalDate localDate, boolean addDays) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime dateWithTime = LocalDateTime.of(localDate, midnight);
        if (addDays) {
            dateWithTime = dateWithTime.plusDays(1);
        }
        return Date.from(dateWithTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    public static Date getTodayDateInUtil(String locale) { // "America/Los_Angeles"
        ZonedDateTime timeZonedDateTime = ZonedDateTime.now(ZoneId.of(locale));
        Instant instant = timeZonedDateTime.toInstant();
        instant.truncatedTo(ChronoUnit.SECONDS);
        return toJavaUtilDateFromZonedDateTime(timeZonedDateTime);
    }

    public static Date toJavaUtilDateFromZonedDateTime(ZonedDateTime zdt) {
        Instant instant = zdt.toInstant();
        // Data-loss, going from nanosecond resolution to milliseconds.
        return Date.from(instant);
    }

    public static String toStringWithDefaultFormatter(LocalDate date) {
        return date.format(europeanDateFormatter);
    }

    public static String toStringWithDefaultFormatter(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static String toStringWithDefaultFormatter(LocalDateTime date) {
        return date.format(europeanDateTimeFormatter);
    }

    public static Date parseDateTime(String date) {
        return parseDateTime(LocalDateTime.parse(date, europeanDateTimeFormatter));
    }

    public static Date parseDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    public static SimpleDateFormat defaultDateFormat() {
        return DateUtil.friendlyDateFormat();
    }

    public static SimpleDateFormat friendlyDateFormat() {
        return new SimpleDateFormat("yyyy-M-d");
    }

    public static String format(Date aDate, SimpleDateFormat aFormat) {
        if (aDate == null || aFormat == null) {
            return "";
        }
        synchronized (aFormat) {
            return aFormat.format(aDate);
        }
    }

    public static Date getCurrentDateTimeBy(String whichZone) {
        ZonedDateTime timeZone = ZonedDateTime.now(ZoneId.of(whichZone));
        return Timestamp.valueOf(timeZone.toLocalDateTime());

    }

    public static Date formatTimeToDate(String time) {
        try {
            return dateTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Integer getMonth(String date, String zone) {
        LocalDateTime formatedDate = null;
        formatedDate = convertStringLocalDateTimeToUtcWithZone(StringUtils.formatString(date, "M/dd/yyyy hh:mm"), DATE_TIME_FORMAT, zone);
        return formatedDate.getMonthValue();
    }

    public static Integer getYear(String date, String zone) {
        LocalDateTime formatedDate = null;
        formatedDate = convertStringLocalDateTimeToUtcWithZone(StringUtils.formatString(date, "M/dd/yyyy hh:mm"), DATE_TIME_FORMAT, zone);
        return formatedDate.getYear();
    }

    public static Boolean isSystemTimeInUTCFormat() {
        String utc = LocalDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        if (utc.equalsIgnoreCase(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))) {
            return true;
        }
        return false;
    }

    public static String getCurrentTimeZone() {
        ZoneId zoneId = ZoneId.systemDefault();
        return zoneId.getId();
    }
}
