package com.common.utils.string;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * 문자열 처리 유틸리티 클래스
 */
public class StringUtils {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String NUMERIC = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    /**
     * 문자열이 null이거나 빈 문자열인지 확인
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 문자열이 null이거나 빈 문자열이거나 공백만 있는지 확인
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 문자열이 null이 아니고 비어있지 않은지 확인
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 문자열이 null이 아니고 공백이 아닌지 확인
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * camelCase를 snake_case로 변환
     * 예: userName -> user_name
     */
    public static String camelToSnake(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    /**
     * snake_case를 camelCase로 변환
     * 예: user_name -> userName
     */
    public static String snakeToCamel(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (char c : str.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * 이메일 마스킹
     * 예: example@email.com -> exa***@email.com
     */
    public static String maskEmail(String email) {
        if (isEmpty(email) || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String localPart = parts[0];

        if (localPart.length() <= 3) {
            return localPart.charAt(0) + "***@" + parts[1];
        }

        String visiblePart = localPart.substring(0, 3);
        return visiblePart + "***@" + parts[1];
    }

    /**
     * 전화번호 마스킹
     * 예: 010-1234-5678 -> 010-****-5678
     */
    public static String maskPhone(String phone) {
        if (isEmpty(phone)) {
            return phone;
        }
        String digitsOnly = phone.replaceAll("[^0-9]", "");

        if (digitsOnly.length() == 11) {
            return digitsOnly.substring(0, 3) + "-****-" + digitsOnly.substring(7);
        } else if (digitsOnly.length() == 10) {
            return digitsOnly.substring(0, 3) + "-***-" + digitsOnly.substring(6);
        }
        return phone;
    }

    /**
     * 랜덤 영숫자 문자열 생성
     * @param length 생성할 문자열 길이
     */
    public static String generateRandomAlphanumeric(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }

    /**
     * 랜덤 숫자 문자열 생성
     * @param length 생성할 숫자 길이
     */
    public static String generateRandomNumeric(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
        }
        return sb.toString();
    }

    /**
     * 첫 글자를 대문자로 변환
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 첫 글자를 소문자로 변환
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 문자열 반복
     * @param str 반복할 문자열
     * @param count 반복 횟수
     */
    public static String repeat(String str, int count) {
        if (str == null || count <= 0) {
            return "";
        }
        return str.repeat(count);
    }

    /**
     * 문자열 자르기 (최대 길이 제한)
     * @param str 자를 문자열
     * @param maxLength 최대 길이
     * @param suffix 자른 경우 추가할 접미사 (예: "...")
     */
    public static String truncate(String str, int maxLength, String suffix) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }
        if (suffix == null) {
            suffix = "";
        }
        return str.substring(0, maxLength - suffix.length()) + suffix;
    }
}
