package com.common.utils.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 날짜/시간 처리 유틸리티 클래스
 */
public class DateUtils {

    // 자주 사용되는 날짜 포맷
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_DATETIME_COMPACT = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
    public static final String PATTERN_KOREAN_DATE = "yyyy년 MM월 dd일";
    public static final String PATTERN_KOREAN_DATETIME = "yyyy년 MM월 dd일 HH시 mm분 ss초";

    /**
     * 현재 날짜/시간을 지정된 포맷으로 반환
     */
    public static String getCurrentDateTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 현재 날짜를 지정된 포맷으로 반환
     */
    public static String getCurrentDate(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 현재 시간을 지정된 포맷으로 반환
     */
    public static String getCurrentTime(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 날짜 문자열 포맷 변환
     * @param dateStr 원본 날짜 문자열
     * @param fromPattern 원본 포맷
     * @param toPattern 변환할 포맷
     */
    public static String convertDateFormat(String dateStr, String fromPattern, String toPattern) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(fromPattern));
            return dateTime.format(DateTimeFormatter.ofPattern(toPattern));
        } catch (Exception e) {
            try {
                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(fromPattern));
                return date.format(DateTimeFormatter.ofPattern(toPattern));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Invalid date format: " + dateStr);
            }
        }
    }

    /**
     * 두 날짜 사이의 일수 계산
     */
    public static long getDaysBetween(String startDate, String endDate, String pattern) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(pattern));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(pattern));
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 특정 날짜까지 남은 일수 계산 (D-day)
     */
    public static long getDaysUntil(String targetDate, String pattern) {
        LocalDate target = LocalDate.parse(targetDate, DateTimeFormatter.ofPattern(pattern));
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(today, target);
    }

    /**
     * 날짜에 일수 추가
     * @param dateStr 기준 날짜
     * @param days 추가할 일수 (음수 가능)
     * @param pattern 날짜 포맷
     */
    public static String addDays(String dateStr, long days, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return date.plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 날짜에 월 추가
     * @param dateStr 기준 날짜
     * @param months 추가할 월수 (음수 가능)
     * @param pattern 날짜 포맷
     */
    public static String addMonths(String dateStr, long months, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return date.plusMonths(months).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 날짜에 년 추가
     * @param dateStr 기준 날짜
     * @param years 추가할 년수 (음수 가능)
     * @param pattern 날짜 포맷
     */
    public static String addYears(String dateStr, long years, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return date.plusYears(years).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 타임존 변환
     * @param dateTimeStr 날짜/시간 문자열
     * @param pattern 날짜 포맷
     * @param fromZone 원본 타임존 (예: "Asia/Seoul")
     * @param toZone 변환할 타임존 (예: "America/New_York")
     */
    public static String convertTimeZone(String dateTimeStr, String pattern, String fromZone, String toZone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);

        ZonedDateTime fromZonedDateTime = localDateTime.atZone(ZoneId.of(fromZone));
        ZonedDateTime toZonedDateTime = fromZonedDateTime.withZoneSameInstant(ZoneId.of(toZone));

        return toZonedDateTime.format(formatter);
    }

    /**
     * 현재 타임스탬프 반환 (밀리초)
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 타임스탬프를 날짜 문자열로 변환
     */
    public static String timestampToString(long timestamp, String pattern) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        );
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 날짜 문자열을 타임스탬프로 변환
     */
    public static long stringToTimestamp(String dateStr, String pattern) {
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 두 시간 사이의 시간 차이 계산 (시간 단위)
     */
    public static long getHoursBetween(String startDateTime, String endDateTime, String pattern) {
        LocalDateTime start = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern(pattern));
        LocalDateTime end = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern(pattern));
        return ChronoUnit.HOURS.between(start, end);
    }

    /**
     * 두 시간 사이의 시간 차이 계산 (분 단위)
     */
    public static long getMinutesBetween(String startDateTime, String endDateTime, String pattern) {
        LocalDateTime start = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern(pattern));
        LocalDateTime end = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern(pattern));
        return ChronoUnit.MINUTES.between(start, end);
    }

    /**
     * 해당 날짜가 오늘인지 확인
     */
    public static boolean isToday(String dateStr, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return date.equals(LocalDate.now());
    }

    /**
     * 해당 날짜가 주말인지 확인
     */
    public static boolean isWeekend(String dateStr, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * 월의 첫째 날 구하기
     */
    public static String getFirstDayOfMonth(String dateStr, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return date.withDayOfMonth(1).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 월의 마지막 날 구하기
     */
    public static String getLastDayOfMonth(String dateStr, String pattern) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return date.withDayOfMonth(date.lengthOfMonth()).format(DateTimeFormatter.ofPattern(pattern));
    }
}
