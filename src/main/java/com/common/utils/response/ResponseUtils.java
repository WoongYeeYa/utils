package com.common.utils.response;

import java.util.List;

/**
 * API 응답 생성 유틸리티 클래스
 */
public class ResponseUtils {

    /**
     * 성공 응답 생성 (데이터 포함)
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .message("Success")
            .data(data)
            .build();
    }

    /**
     * 성공 응답 생성 (메시지 포함)
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .message(message)
            .data(data)
            .build();
    }

    /**
     * 성공 응답 생성 (데이터 없음)
     */
    public static <T> ApiResponse<T> success() {
        return ApiResponse.<T>builder()
            .success(true)
            .message("Success")
            .build();
    }

    /**
     * 실패 응답 생성 (메시지만)
     */
    public static <T> ApiResponse<T> fail(String message) {
        return ApiResponse.<T>builder()
            .success(false)
            .message(message)
            .build();
    }

    /**
     * 실패 응답 생성 (에러 코드 포함)
     */
    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
            .success(false)
            .message(errorCode.getMessage())
            .errorCode(errorCode.getCode())
            .build();
    }

    /**
     * 실패 응답 생성 (에러 코드와 커스텀 메시지)
     */
    public static <T> ApiResponse<T> fail(ErrorCode errorCode, String customMessage) {
        return ApiResponse.<T>builder()
            .success(false)
            .message(customMessage)
            .errorCode(errorCode.getCode())
            .build();
    }

    /**
     * 페이지네이션 응답 생성
     */
    public static <T> ApiResponse<PageResponse<T>> pageSuccess(List<T> content, int currentPage, int pageSize, long totalElements) {
        PageResponse<T> pageResponse = PageResponse.of(content, currentPage, pageSize, totalElements);
        return success(pageResponse);
    }

    /**
     * 페이지네이션 응답 생성 (메시지 포함)
     */
    public static <T> ApiResponse<PageResponse<T>> pageSuccess(String message, List<T> content, int currentPage, int pageSize, long totalElements) {
        PageResponse<T> pageResponse = PageResponse.of(content, currentPage, pageSize, totalElements);
        return success(message, pageResponse);
    }

    /**
     * 빈 페이지 응답 생성
     */
    public static <T> ApiResponse<PageResponse<T>> emptyPage(int currentPage, int pageSize) {
        PageResponse<T> pageResponse = PageResponse.of(List.of(), currentPage, pageSize, 0);
        return success(pageResponse);
    }
}
