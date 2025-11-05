package com.common.utils.response;

/**
 * 공통 에러 코드 정의
 */
public enum ErrorCode {

    // 일반 에러 (1xxx)
    SUCCESS("0000", "성공"),
    UNKNOWN_ERROR("1000", "알 수 없는 오류가 발생했습니다"),
    INVALID_PARAMETER("1001", "잘못된 파라미터입니다"),
    INVALID_FORMAT("1002", "잘못된 형식입니다"),

    // 파일 관련 에러 (2xxx)
    FILE_UPLOAD_FAILED("2001", "파일 업로드에 실패했습니다"),
    FILE_SIZE_EXCEEDED("2002", "파일 크기가 제한을 초과했습니다"),
    INVALID_FILE_EXTENSION("2003", "허용되지 않는 파일 확장자입니다"),
    FILE_NOT_FOUND("2004", "파일을 찾을 수 없습니다"),

    // 데이터 관련 에러 (3xxx)
    DATA_NOT_FOUND("3001", "데이터를 찾을 수 없습니다"),
    DUPLICATE_DATA("3002", "중복된 데이터입니다"),
    EMPTY_DATA("3003", "데이터가 비어있습니다"),

    // 인증/권한 에러 (4xxx)
    UNAUTHORIZED("4001", "인증되지 않은 사용자입니다"),
    FORBIDDEN("4003", "접근 권한이 없습니다"),

    // 서버 에러 (5xxx)
    INTERNAL_SERVER_ERROR("5000", "서버 내부 오류가 발생했습니다");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
