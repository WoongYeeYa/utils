package com.common.utils.id;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * IdUtils - ID 생성 유틸리티
 */
public class IdUtils {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random random = new SecureRandom();
    private static final AtomicLong sequence = new AtomicLong(0);

    // Snowflake ID 설정
    private static final long EPOCH = 1640995200000L; // 2022-01-01 00:00:00 UTC
    private static final long MACHINE_ID_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_ID_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    private static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;

    private static long machineId = 1L; // 기본 머신 ID
    private static long lastTimestamp = -1L;
    private static long snowflakeSequence = 0L;

    private IdUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * UUID 생성 (랜덤)
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * UUID 생성 (하이픈 제거)
     */
    public static String generateUuidWithoutHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 짧은 UUID 생성 (Base62 인코딩, 22자)
     */
    public static String generateShortUuid() {
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();

        return base62Encode(mostSigBits) + base62Encode(leastSigBits);
    }

    /**
     * Base62 인코딩
     */
    private static String base62Encode(long value) {
        StringBuilder sb = new StringBuilder();
        boolean negative = value < 0;
        value = Math.abs(value);

        if (value == 0) {
            return "0";
        }

        while (value > 0) {
            int remainder = (int) (value % 62);
            sb.append(BASE62.charAt(remainder));
            value /= 62;
        }

        if (negative) {
            sb.append('-');
        }

        return sb.reverse().toString();
    }

    /**
     * Snowflake ID 생성 (분산 환경에서 고유한 ID)
     */
    public static synchronized long generateSnowflakeId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (timestamp == lastTimestamp) {
            snowflakeSequence = (snowflakeSequence + 1) & MAX_SEQUENCE;
            if (snowflakeSequence == 0) {
                // 시퀀스가 넘치면 다음 밀리초까지 대기
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            snowflakeSequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT) |
                (machineId << MACHINE_ID_SHIFT) |
                snowflakeSequence;
    }

    /**
     * Snowflake ID를 문자열로 생성
     */
    public static String generateSnowflakeIdString() {
        return String.valueOf(generateSnowflakeId());
    }

    /**
     * 머신 ID 설정 (0 ~ 1023)
     */
    public static void setMachineId(long id) {
        if (id < 0 || id > MAX_MACHINE_ID) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + MAX_MACHINE_ID);
        }
        machineId = id;
    }

    /**
     * 다음 밀리초까지 대기
     */
    private static long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 순차 ID 생성 (Long)
     */
    public static long generateSequentialId() {
        return sequence.incrementAndGet();
    }

    /**
     * 순차 ID를 문자열로 생성
     */
    public static String generateSequentialIdString() {
        return String.valueOf(generateSequentialId());
    }

    /**
     * 순차 ID를 패딩된 문자열로 생성
     */
    public static String generateSequentialIdWithPadding(int length) {
        long id = generateSequentialId();
        return String.format("%0" + length + "d", id);
    }

    /**
     * 타임스탬프 기반 ID 생성
     */
    public static long generateTimestampId() {
        return System.currentTimeMillis();
    }

    /**
     * 타임스탬프 기반 ID를 문자열로 생성
     */
    public static String generateTimestampIdString() {
        return String.valueOf(generateTimestampId());
    }

    /**
     * 랜덤 알파뉴메릭 ID 생성
     */
    public static String generateRandomId(int length) {
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
     * 랜덤 숫자 ID 생성
     */
    public static String generateRandomNumericId(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 짧은 URL용 ID 생성 (Base62, 지정된 길이)
     */
    public static String generateShortUrlId(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }

    /**
     * Nano ID 스타일 ID 생성 (URL-safe, 21자)
     */
    public static String generateNanoId() {
        return generateNanoId(21);
    }

    /**
     * Nano ID 스타일 ID 생성 (URL-safe, 지정된 길이)
     */
    public static String generateNanoId(int length) {
        String urlSafeChars = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(urlSafeChars.charAt(random.nextInt(urlSafeChars.length())));
        }
        return sb.toString();
    }

    /**
     * ULID (Universally Unique Lexicographically Sortable Identifier) 생성
     * 타임스탬프(10자) + 랜덤(16자) = 26자
     */
    public static String generateUlid() {
        long timestamp = Instant.now().toEpochMilli();
        String timestampPart = base32Encode(timestamp, 10);
        String randomPart = generateRandomBase32(16);
        return timestampPart + randomPart;
    }

    /**
     * Base32 인코딩 (Crockford's Base32)
     */
    private static String base32Encode(long value, int length) {
        String base32 = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (value & 0x1F);
            sb.insert(0, base32.charAt(index));
            value >>>= 5;
        }

        return sb.toString();
    }

    /**
     * 랜덤 Base32 문자열 생성
     */
    private static String generateRandomBase32(int length) {
        String base32 = "0123456789ABCDEFGHJKMNPQRSTVWXYZ";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(base32.charAt(random.nextInt(base32.length())));
        }

        return sb.toString();
    }

    /**
     * ObjectId 스타일 ID 생성 (MongoDB ObjectId와 유사, 24자 16진수)
     */
    public static String generateObjectId() {
        long timestamp = System.currentTimeMillis() / 1000;
        int machineIdentifier = random.nextInt();
        short processIdentifier = (short) random.nextInt(65536);
        int counter = random.nextInt();

        return String.format("%08x%08x%04x%06x",
                timestamp,
                machineIdentifier,
                processIdentifier,
                counter & 0xFFFFFF);
    }

    /**
     * 접두사가 있는 ID 생성
     */
    public static String generateIdWithPrefix(String prefix, int length) {
        if (prefix == null) {
            prefix = "";
        }
        return prefix + generateRandomId(length);
    }

    /**
     * 접두사와 타임스탬프가 있는 ID 생성
     */
    public static String generateIdWithPrefixAndTimestamp(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        return prefix + System.currentTimeMillis() + "_" + generateRandomId(8);
    }
}
