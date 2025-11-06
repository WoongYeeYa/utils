package com.common.utils.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * MoneyUtils - 금액 처리 유틸리티
 */
public class MoneyUtils {

    // 통화 심볼
    public static final Map<String, String> CURRENCY_SYMBOLS = new HashMap<>();

    static {
        CURRENCY_SYMBOLS.put("KRW", "₩");
        CURRENCY_SYMBOLS.put("USD", "$");
        CURRENCY_SYMBOLS.put("EUR", "€");
        CURRENCY_SYMBOLS.put("JPY", "¥");
        CURRENCY_SYMBOLS.put("GBP", "£");
        CURRENCY_SYMBOLS.put("CNY", "¥");
        CURRENCY_SYMBOLS.put("AUD", "A$");
        CURRENCY_SYMBOLS.put("CAD", "C$");
        CURRENCY_SYMBOLS.put("CHF", "Fr");
        CURRENCY_SYMBOLS.put("INR", "₹");
    }

    // 기본 환율 (예시, 실제로는 API에서 가져와야 함)
    private static final Map<String, BigDecimal> EXCHANGE_RATES = new HashMap<>();

    static {
        EXCHANGE_RATES.put("USD", new BigDecimal("1.0"));
        EXCHANGE_RATES.put("KRW", new BigDecimal("1300"));
        EXCHANGE_RATES.put("EUR", new BigDecimal("0.92"));
        EXCHANGE_RATES.put("JPY", new BigDecimal("149"));
        EXCHANGE_RATES.put("GBP", new BigDecimal("0.79"));
        EXCHANGE_RATES.put("CNY", new BigDecimal("7.24"));
    }

    private MoneyUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 금액 포맷팅 (한국 원화)
     */
    public static String formatKRW(long amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(amount) + "원";
    }

    /**
     * 금액 포맷팅 (한국 원화, BigDecimal)
     */
    public static String formatKRW(BigDecimal amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(amount) + "원";
    }

    /**
     * 금액 포맷팅 (통화 코드 지정)
     */
    public static String format(BigDecimal amount, String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setCurrency(currency);
            return formatter.format(amount);
        } catch (Exception e) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String symbol = CURRENCY_SYMBOLS.getOrDefault(currencyCode, currencyCode);
            return symbol + df.format(amount);
        }
    }

    /**
     * 금액 포맷팅 (로케일 지정)
     */
    public static String format(BigDecimal amount, Locale locale) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(amount);
    }

    /**
     * 환율 계산
     */
    public static BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }

        BigDecimal fromRate = EXCHANGE_RATES.getOrDefault(fromCurrency, BigDecimal.ONE);
        BigDecimal toRate = EXCHANGE_RATES.getOrDefault(toCurrency, BigDecimal.ONE);

        // USD를 기준으로 변환
        BigDecimal usdAmount = amount.divide(fromRate, 4, RoundingMode.HALF_UP);
        return usdAmount.multiply(toRate).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 환율 설정
     */
    public static void setExchangeRate(String currencyCode, BigDecimal rate) {
        EXCHANGE_RATES.put(currencyCode, rate);
    }

    /**
     * 환율 조회
     */
    public static BigDecimal getExchangeRate(String currencyCode) {
        return EXCHANGE_RATES.getOrDefault(currencyCode, BigDecimal.ONE);
    }

    /**
     * 금액 더하기 (정밀도 유지)
     */
    public static BigDecimal add(BigDecimal amount1, BigDecimal amount2) {
        if (amount1 == null) amount1 = BigDecimal.ZERO;
        if (amount2 == null) amount2 = BigDecimal.ZERO;
        return amount1.add(amount2);
    }

    /**
     * 금액 빼기 (정밀도 유지)
     */
    public static BigDecimal subtract(BigDecimal amount1, BigDecimal amount2) {
        if (amount1 == null) amount1 = BigDecimal.ZERO;
        if (amount2 == null) amount2 = BigDecimal.ZERO;
        return amount1.subtract(amount2);
    }

    /**
     * 금액 곱하기 (정밀도 유지)
     */
    public static BigDecimal multiply(BigDecimal amount, BigDecimal multiplier) {
        if (amount == null) amount = BigDecimal.ZERO;
        if (multiplier == null) multiplier = BigDecimal.ONE;
        return amount.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 금액 나누기 (정밀도 유지)
     */
    public static BigDecimal divide(BigDecimal amount, BigDecimal divisor) {
        if (amount == null) amount = BigDecimal.ZERO;
        if (divisor == null || divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Divisor cannot be zero");
        }
        return amount.divide(divisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * 할인 금액 계산
     */
    public static BigDecimal calculateDiscount(BigDecimal originalPrice, int discountPercent) {
        if (originalPrice == null) return BigDecimal.ZERO;
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        BigDecimal discount = originalPrice.multiply(new BigDecimal(discountPercent))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return originalPrice.subtract(discount);
    }

    /**
     * 할인율 계산
     */
    public static int calculateDiscountRate(BigDecimal originalPrice, BigDecimal discountedPrice) {
        if (originalPrice == null || discountedPrice == null) return 0;
        if (originalPrice.compareTo(BigDecimal.ZERO) == 0) return 0;

        BigDecimal discount = originalPrice.subtract(discountedPrice);
        BigDecimal rate = discount.divide(originalPrice, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));

        return rate.intValue();
    }

    /**
     * 부가세 계산 (10%)
     */
    public static BigDecimal calculateVAT(BigDecimal amount) {
        return calculateVAT(amount, 10);
    }

    /**
     * 부가세 계산 (세율 지정)
     */
    public static BigDecimal calculateVAT(BigDecimal amount, int vatPercent) {
        if (amount == null) return BigDecimal.ZERO;
        if (vatPercent < 0 || vatPercent > 100) {
            throw new IllegalArgumentException("VAT percent must be between 0 and 100");
        }

        return amount.multiply(new BigDecimal(vatPercent))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * 부가세 포함 금액 계산
     */
    public static BigDecimal addVAT(BigDecimal amount) {
        return addVAT(amount, 10);
    }

    /**
     * 부가세 포함 금액 계산 (세율 지정)
     */
    public static BigDecimal addVAT(BigDecimal amount, int vatPercent) {
        if (amount == null) return BigDecimal.ZERO;
        BigDecimal vat = calculateVAT(amount, vatPercent);
        return amount.add(vat);
    }

    /**
     * 부가세 제외 금액 계산 (부가세 포함 금액에서 원가 계산)
     */
    public static BigDecimal removeVAT(BigDecimal amountWithVAT) {
        return removeVAT(amountWithVAT, 10);
    }

    /**
     * 부가세 제외 금액 계산 (세율 지정)
     */
    public static BigDecimal removeVAT(BigDecimal amountWithVAT, int vatPercent) {
        if (amountWithVAT == null) return BigDecimal.ZERO;
        BigDecimal divisor = new BigDecimal("100").add(new BigDecimal(vatPercent))
                .divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        return amountWithVAT.divide(divisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * 팁 계산
     */
    public static BigDecimal calculateTip(BigDecimal amount, int tipPercent) {
        if (amount == null) return BigDecimal.ZERO;
        if (tipPercent < 0) {
            throw new IllegalArgumentException("Tip percent must be non-negative");
        }

        return amount.multiply(new BigDecimal(tipPercent))
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * 총 금액 계산 (팁 포함)
     */
    public static BigDecimal addTip(BigDecimal amount, int tipPercent) {
        if (amount == null) return BigDecimal.ZERO;
        BigDecimal tip = calculateTip(amount, tipPercent);
        return amount.add(tip);
    }

    /**
     * 금액을 N명이 나눠서 계산
     */
    public static BigDecimal splitAmount(BigDecimal amount, int numberOfPeople) {
        if (amount == null) return BigDecimal.ZERO;
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be positive");
        }

        return amount.divide(new BigDecimal(numberOfPeople), 2, RoundingMode.HALF_UP);
    }

    /**
     * 원 단위 반올림
     */
    public static BigDecimal roundToWon(BigDecimal amount) {
        if (amount == null) return BigDecimal.ZERO;
        return amount.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 10원 단위 반올림
     */
    public static BigDecimal roundToTenWon(BigDecimal amount) {
        if (amount == null) return BigDecimal.ZERO;
        return amount.divide(new BigDecimal("10"), 0, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("10"));
    }

    /**
     * 100원 단위 반올림
     */
    public static BigDecimal roundToHundredWon(BigDecimal amount) {
        if (amount == null) return BigDecimal.ZERO;
        return amount.divide(new BigDecimal("100"), 0, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    /**
     * 1000원 단위 반올림
     */
    public static BigDecimal roundToThousandWon(BigDecimal amount) {
        if (amount == null) return BigDecimal.ZERO;
        return amount.divide(new BigDecimal("1000"), 0, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("1000"));
    }

    /**
     * 금액 비교 (같은지)
     */
    public static boolean equals(BigDecimal amount1, BigDecimal amount2) {
        if (amount1 == null && amount2 == null) return true;
        if (amount1 == null || amount2 == null) return false;
        return amount1.compareTo(amount2) == 0;
    }

    /**
     * 금액 비교 (amount1 > amount2)
     */
    public static boolean greaterThan(BigDecimal amount1, BigDecimal amount2) {
        if (amount1 == null) amount1 = BigDecimal.ZERO;
        if (amount2 == null) amount2 = BigDecimal.ZERO;
        return amount1.compareTo(amount2) > 0;
    }

    /**
     * 금액 비교 (amount1 < amount2)
     */
    public static boolean lessThan(BigDecimal amount1, BigDecimal amount2) {
        if (amount1 == null) amount1 = BigDecimal.ZERO;
        if (amount2 == null) amount2 = BigDecimal.ZERO;
        return amount1.compareTo(amount2) < 0;
    }

    /**
     * 양수인지 확인
     */
    public static boolean isPositive(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 음수인지 확인
     */
    public static boolean isNegative(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 0인지 확인
     */
    public static boolean isZero(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 문자열을 BigDecimal로 변환
     */
    public static BigDecimal parse(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        // 통화 기호 및 콤마 제거
        String cleaned = amount.replaceAll("[^0-9.-]", "");

        try {
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 최대값 찾기
     */
    public static BigDecimal max(BigDecimal... amounts) {
        if (amounts == null || amounts.length == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal max = amounts[0];
        for (BigDecimal amount : amounts) {
            if (amount != null && amount.compareTo(max) > 0) {
                max = amount;
            }
        }

        return max;
    }

    /**
     * 최소값 찾기
     */
    public static BigDecimal min(BigDecimal... amounts) {
        if (amounts == null || amounts.length == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal min = amounts[0];
        for (BigDecimal amount : amounts) {
            if (amount != null && amount.compareTo(min) < 0) {
                min = amount;
            }
        }

        return min;
    }

    /**
     * 합계 계산
     */
    public static BigDecimal sum(BigDecimal... amounts) {
        if (amounts == null || amounts.length == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal amount : amounts) {
            if (amount != null) {
                sum = sum.add(amount);
            }
        }

        return sum;
    }

    /**
     * 평균 계산
     */
    public static BigDecimal average(BigDecimal... amounts) {
        if (amounts == null || amounts.length == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = sum(amounts);
        return sum.divide(new BigDecimal(amounts.length), 2, RoundingMode.HALF_UP);
    }
}
