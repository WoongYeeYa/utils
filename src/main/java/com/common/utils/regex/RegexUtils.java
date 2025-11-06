package com.common.utils.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexUtils - 정규식 유틸리티
 */
public class RegexUtils {

    // 자주 사용하는 정규식 패턴
    public static final class Patterns {
        // 이메일
        public static final String EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // 전화번호 (한국)
        public static final String PHONE_KR = "^(01[0-9]|02|0[3-9][0-9])-?([0-9]{3,4})-?([0-9]{4})$";

        // 전화번호 (국제)
        public static final String PHONE_INTERNATIONAL = "^\\+?[1-9]\\d{1,14}$";

        // URL
        public static final String URL = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

        // IP 주소 (IPv4)
        public static final String IP_V4 = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        // IP 주소 (IPv6)
        public static final String IP_V6 = "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";

        // 날짜 (YYYY-MM-DD)
        public static final String DATE_YYYY_MM_DD = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        // 날짜 (DD/MM/YYYY)
        public static final String DATE_DD_MM_YYYY = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        // 시간 (HH:MM)
        public static final String TIME_HH_MM = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

        // 시간 (HH:MM:SS)
        public static final String TIME_HH_MM_SS = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

        // 신용카드
        public static final String CREDIT_CARD = "^[0-9]{4}[\\s-]?[0-9]{4}[\\s-]?[0-9]{4}[\\s-]?[0-9]{4}$";

        // 우편번호 (한국)
        public static final String POSTAL_CODE_KR = "^\\d{5}$";

        // 우편번호 (미국)
        public static final String POSTAL_CODE_US = "^\\d{5}(-\\d{4})?$";

        // 주민등록번호
        public static final String RESIDENT_NUMBER = "^[0-9]{6}-?[1-4][0-9]{6}$";

        // 사업자등록번호
        public static final String BUSINESS_NUMBER = "^\\d{3}-?\\d{2}-?\\d{5}$";

        // 숫자만
        public static final String NUMERIC = "^[0-9]+$";

        // 영문만
        public static final String ALPHA = "^[a-zA-Z]+$";

        // 영문+숫자
        public static final String ALPHANUMERIC = "^[a-zA-Z0-9]+$";

        // 한글만
        public static final String KOREAN = "^[가-힣]+$";

        // HTML 태그
        public static final String HTML_TAG = "<[^>]+>";

        // 공백
        public static final String WHITESPACE = "\\s+";

        // 비밀번호 (8자 이상, 대소문자+숫자+특수문자)
        public static final String PASSWORD_STRONG = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // 16진수 색상 코드
        public static final String HEX_COLOR = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

        // MAC 주소
        public static final String MAC_ADDRESS = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

        // 파일 확장자
        public static final String FILE_EXTENSION = "\\.([a-zA-Z0-9]+)$";

        private Patterns() {
            throw new IllegalStateException("Pattern class");
        }
    }

    private RegexUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 패턴 매칭 여부 확인
     */
    public static boolean matches(String text, String regex) {
        if (text == null || regex == null) {
            return false;
        }
        return Pattern.matches(regex, text);
    }

    /**
     * 패턴 매칭 여부 확인 (대소문자 무시)
     */
    public static boolean matchesIgnoreCase(String text, String regex) {
        if (text == null || regex == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(text).matches();
    }

    /**
     * 패턴과 일치하는 첫 번째 문자열 찾기
     */
    public static String find(String text, String regex) {
        if (text == null || regex == null) {
            return null;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    /**
     * 패턴과 일치하는 모든 문자열 찾기
     */
    public static List<String> findAll(String text, String regex) {
        List<String> matches = new ArrayList<>();

        if (text == null || regex == null) {
            return matches;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }

    /**
     * 패턴과 일치하는 그룹 추출
     */
    public static List<String> extractGroups(String text, String regex) {
        List<String> groups = new ArrayList<>();

        if (text == null || regex == null) {
            return groups;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                groups.add(matcher.group(i));
            }
        }

        return groups;
    }

    /**
     * 패턴으로 문자열 치환
     */
    public static String replace(String text, String regex, String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceAll(regex, replacement);
    }

    /**
     * 패턴으로 첫 번째 매칭만 치환
     */
    public static String replaceFirst(String text, String regex, String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceFirst(regex, replacement);
    }

    /**
     * 패턴으로 문자열 분할
     */
    public static String[] split(String text, String regex) {
        if (text == null || regex == null) {
            return new String[0];
        }
        return text.split(regex);
    }

    /**
     * 패턴 매칭 횟수 카운트
     */
    public static int countMatches(String text, String regex) {
        if (text == null || regex == null) {
            return 0;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    /**
     * 이메일 형식 확인
     */
    public static boolean isEmail(String text) {
        return matches(text, Patterns.EMAIL);
    }

    /**
     * 전화번호 형식 확인 (한국)
     */
    public static boolean isPhoneNumber(String text) {
        return matches(text, Patterns.PHONE_KR);
    }

    /**
     * URL 형식 확인
     */
    public static boolean isUrl(String text) {
        return matches(text, Patterns.URL);
    }

    /**
     * IP 주소 형식 확인 (IPv4)
     */
    public static boolean isIpAddress(String text) {
        return matches(text, Patterns.IP_V4);
    }

    /**
     * 날짜 형식 확인 (YYYY-MM-DD)
     */
    public static boolean isDate(String text) {
        return matches(text, Patterns.DATE_YYYY_MM_DD);
    }

    /**
     * 숫자만 포함되어 있는지 확인
     */
    public static boolean isNumeric(String text) {
        return matches(text, Patterns.NUMERIC);
    }

    /**
     * 영문만 포함되어 있는지 확인
     */
    public static boolean isAlpha(String text) {
        return matches(text, Patterns.ALPHA);
    }

    /**
     * 영문+숫자만 포함되어 있는지 확인
     */
    public static boolean isAlphanumeric(String text) {
        return matches(text, Patterns.ALPHANUMERIC);
    }

    /**
     * 한글만 포함되어 있는지 확인
     */
    public static boolean isKorean(String text) {
        return matches(text, Patterns.KOREAN);
    }

    /**
     * HTML 태그 제거
     */
    public static String removeHtmlTags(String text) {
        return replace(text, Patterns.HTML_TAG, "");
    }

    /**
     * 공백 제거
     */
    public static String removeWhitespace(String text) {
        return replace(text, Patterns.WHITESPACE, "");
    }

    /**
     * 공백을 하나의 공백으로 변환
     */
    public static String normalizeWhitespace(String text) {
        return replace(text, Patterns.WHITESPACE, " ").trim();
    }

    /**
     * 숫자만 추출
     */
    public static String extractNumbers(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[^0-9]", "");
    }

    /**
     * 영문만 추출
     */
    public static String extractAlphabets(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[^a-zA-Z]", "");
    }

    /**
     * 한글만 추출
     */
    public static String extractKorean(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[^가-힣]", "");
    }

    /**
     * 이메일 주소 추출
     */
    public static List<String> extractEmails(String text) {
        return findAll(text, Patterns.EMAIL);
    }

    /**
     * URL 추출
     */
    public static List<String> extractUrls(String text) {
        return findAll(text, Patterns.URL);
    }

    /**
     * 전화번호 추출
     */
    public static List<String> extractPhoneNumbers(String text) {
        return findAll(text, Patterns.PHONE_KR);
    }

    /**
     * 패턴이 유효한 정규식인지 확인
     */
    public static boolean isValidPattern(String regex) {
        if (regex == null) {
            return false;
        }
        try {
            Pattern.compile(regex);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 정규식 특수문자 이스케이프
     */
    public static String escape(String text) {
        if (text == null) {
            return null;
        }
        return Pattern.quote(text);
    }

    /**
     * 문자열에서 특정 패턴의 위치 찾기
     */
    public static int indexOf(String text, String regex) {
        if (text == null || regex == null) {
            return -1;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.start();
        }

        return -1;
    }

    /**
     * 문자열에서 특정 패턴의 마지막 위치 찾기
     */
    public static int lastIndexOf(String text, String regex) {
        if (text == null || regex == null) {
            return -1;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        int lastIndex = -1;
        while (matcher.find()) {
            lastIndex = matcher.start();
        }

        return lastIndex;
    }
}
