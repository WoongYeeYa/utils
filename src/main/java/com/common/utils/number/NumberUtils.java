package com.common.utils.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * NumberUtils - 숫자 처리 유틸리티
 */
public class NumberUtils {

    private static final Random random = new Random();
    private static final DecimalFormat COMMA_FORMAT = new DecimalFormat("#,###");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    private NumberUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 천 단위 콤마 포맷
     */
    public static String formatWithComma(long number) {
        return COMMA_FORMAT.format(number);
    }

    /**
     * 천 단위 콤마 포맷 (소수점 포함)
     */
    public static String formatWithComma(double number) {
        return COMMA_FORMAT.format(number);
    }

    /**
     * 소수점 포맷 (천 단위 콤마 + 소수점 2자리)
     */
    public static String formatDecimal(double number) {
        return DECIMAL_FORMAT.format(number);
    }

    /**
     * 사용자 정의 포맷으로 숫자 포맷팅
     */
    public static String format(double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    /**
     * 백분율 계산
     */
    public static double calculatePercentage(double value, double total) {
        if (total == 0) {
            return 0;
        }
        return (value / total) * 100;
    }

    /**
     * 백분율 포맷 (소수점 2자리)
     */
    public static String formatPercentage(double value, double total) {
        double percentage = calculatePercentage(value, total);
        return String.format("%.2f%%", percentage);
    }

    /**
     * 반올림 (소수점 자리수 지정)
     */
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale must be non-negative");
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 올림 (소수점 자리수 지정)
     */
    public static double ceil(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale must be non-negative");
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    /**
     * 내림 (소수점 자리수 지정)
     */
    public static double floor(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("Scale must be non-negative");
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(scale, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    /**
     * 숫자가 범위 내에 있는지 확인
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * 숫자가 범위 내에 있는지 확인 (double)
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * 최소값과 최대값 사이로 값을 제한
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * 최소값과 최대값 사이로 값을 제한 (double)
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * 랜덤 정수 생성 (min ~ max)
     */
    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 랜덤 실수 생성 (min ~ max)
     */
    public static double randomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        return min + (max - min) * random.nextDouble();
    }

    /**
     * 두 숫자가 거의 같은지 확인 (오차 범위 내)
     */
    public static boolean approximatelyEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }

    /**
     * 두 숫자가 거의 같은지 확인 (기본 오차 범위: 0.0001)
     */
    public static boolean approximatelyEqual(double a, double b) {
        return approximatelyEqual(a, b, 0.0001);
    }

    /**
     * 평균 계산
     */
    public static double average(int... numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        long sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return (double) sum / numbers.length;
    }

    /**
     * 평균 계산 (double)
     */
    public static double average(double... numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.length;
    }

    /**
     * 최대공약수 (GCD)
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * 최소공배수 (LCM)
     */
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs(a * b) / gcd(a, b);
    }

    /**
     * 소수인지 확인
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 짝수인지 확인
     */
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * 홀수인지 확인
     */
    public static boolean isOdd(int number) {
        return number % 2 != 0;
    }

    /**
     * 양수인지 확인
     */
    public static boolean isPositive(double number) {
        return number > 0;
    }

    /**
     * 음수인지 확인
     */
    public static boolean isNegative(double number) {
        return number < 0;
    }

    /**
     * 0인지 확인 (오차 범위 고려)
     */
    public static boolean isZero(double number) {
        return approximatelyEqual(number, 0);
    }

    /**
     * 문자열을 정수로 안전하게 변환
     */
    public static Integer toInteger(String str, Integer defaultValue) {
        if (str == null || str.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 Long으로 안전하게 변환
     */
    public static Long toLong(String str, Long defaultValue) {
        if (str == null || str.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 문자열을 Double로 안전하게 변환
     */
    public static Double toDouble(String str, Double defaultValue) {
        if (str == null || str.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 바이트를 사람이 읽기 쉬운 형식으로 변환
     */
    public static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.2f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * 숫자를 로마 숫자로 변환
     */
    public static String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999");
        }

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[number / 1000] +
                hundreds[(number % 1000) / 100] +
                tens[(number % 100) / 10] +
                ones[number % 10];
    }
}
