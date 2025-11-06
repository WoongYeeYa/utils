package com.common.utils.validation;

import java.util.regex.Pattern;

/**
 * ValidationUtils - 유효성 검증 유틸리티
 */
public class ValidationUtils {

    // 이메일 정규식
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    // 전화번호 정규식 (010-1234-5678, 01012345678, 02-123-4567 등)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(01[0-9]|02|0[3-9][0-9])-?([0-9]{3,4})-?([0-9]{4})$"
    );

    // URL 정규식
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            Pattern.CASE_INSENSITIVE
    );

    // 신용카드 정규식 (기본적인 형식만)
    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(
            "^[0-9]{4}[\\s-]?[0-9]{4}[\\s-]?[0-9]{4}[\\s-]?[0-9]{4}$"
    );

    // 주민등록번호 정규식
    private static final Pattern RESIDENT_NUMBER_PATTERN = Pattern.compile(
            "^[0-9]{6}-?[1-4][0-9]{6}$"
    );

    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 이메일 형식 검증
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * 전화번호 형식 검증
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * URL 형식 검증
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return URL_PATTERN.matcher(url.trim()).matches();
    }

    /**
     * 신용카드 번호 형식 검증 (Luhn 알고리즘)
     */
    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return false;
        }

        String cleaned = cardNumber.replaceAll("[\\s-]", "");

        if (!CREDIT_CARD_PATTERN.matcher(cardNumber).matches()) {
            return false;
        }

        return luhnCheck(cleaned);
    }

    /**
     * Luhn 알고리즘으로 신용카드 검증
     */
    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    /**
     * 주민등록번호 형식 검증
     */
    public static boolean isValidResidentNumber(String residentNumber) {
        if (residentNumber == null || residentNumber.trim().isEmpty()) {
            return false;
        }

        String cleaned = residentNumber.replaceAll("-", "");

        if (!RESIDENT_NUMBER_PATTERN.matcher(residentNumber).matches()) {
            return false;
        }

        // 검증 알고리즘
        int[] weights = {2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5};
        int sum = 0;

        for (int i = 0; i < 12; i++) {
            sum += Integer.parseInt(cleaned.substring(i, i + 1)) * weights[i];
        }

        int checkDigit = (11 - (sum % 11)) % 10;
        int lastDigit = Integer.parseInt(cleaned.substring(12, 13));

        return checkDigit == lastDigit;
    }

    /**
     * 비밀번호 강도 체크
     *
     * @param password 비밀번호
     * @return 0: 매우 약함, 1: 약함, 2: 보통, 3: 강함, 4: 매우 강함
     */
    public static int checkPasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int strength = 0;

        // 길이 체크
        if (password.length() >= 8) strength++;
        if (password.length() >= 12) strength++;

        // 소문자 포함
        if (password.matches(".*[a-z].*")) strength++;

        // 대문자 포함
        if (password.matches(".*[A-Z].*")) strength++;

        // 숫자 포함
        if (password.matches(".*[0-9].*")) strength++;

        // 특수문자 포함
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) strength++;

        // 최대 5점을 4점 스케일로 변환
        return Math.min(strength * 4 / 6, 4);
    }

    /**
     * 비밀번호 강도 텍스트 반환
     */
    public static String getPasswordStrengthText(String password) {
        int strength = checkPasswordStrength(password);
        return switch (strength) {
            case 0 -> "매우 약함";
            case 1 -> "약함";
            case 2 -> "보통";
            case 3 -> "강함";
            case 4 -> "매우 강함";
            default -> "알 수 없음";
        };
    }

    /**
     * 숫자로만 구성되어 있는지 확인
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[0-9]+$");
    }

    /**
     * 알파벳으로만 구성되어 있는지 확인
     */
    public static boolean isAlpha(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[a-zA-Z]+$");
    }

    /**
     * 알파벳과 숫자로만 구성되어 있는지 확인
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[a-zA-Z0-9]+$");
    }

    /**
     * IP 주소 형식 검증 (IPv4)
     */
    public static boolean isValidIpAddress(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            return false;
        }

        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * 한글로만 구성되어 있는지 확인
     */
    public static boolean isKorean(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[가-힣]+$");
    }

    /**
     * 영문으로만 구성되어 있는지 확인
     */
    public static boolean isEnglish(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[a-zA-Z\\s]+$");
    }

    /**
     * 사업자등록번호 형식 검증
     */
    public static boolean isValidBusinessNumber(String businessNumber) {
        if (businessNumber == null || businessNumber.trim().isEmpty()) {
            return false;
        }

        String cleaned = businessNumber.replaceAll("-", "");

        if (!cleaned.matches("^[0-9]{10}$")) {
            return false;
        }

        int[] weights = {1, 3, 7, 1, 3, 7, 1, 3, 5};
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(cleaned.substring(i, i + 1)) * weights[i];
        }

        sum += (Integer.parseInt(cleaned.substring(8, 9)) * 5) / 10;
        int checkDigit = (10 - (sum % 10)) % 10;
        int lastDigit = Integer.parseInt(cleaned.substring(9, 10));

        return checkDigit == lastDigit;
    }
}
