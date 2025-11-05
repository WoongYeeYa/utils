package com.common.utils.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 페이지네이션 응답 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    /**
     * 데이터 리스트
     */
    private List<T> content;

    /**
     * 현재 페이지 번호 (1부터 시작)
     */
    private int currentPage;

    /**
     * 페이지 크기
     */
    private int pageSize;

    /**
     * 전체 요소 개수
     */
    private long totalElements;

    /**
     * 전체 페이지 개수
     */
    private int totalPages;

    /**
     * 첫 페이지 여부
     */
    private boolean first;

    /**
     * 마지막 페이지 여부
     */
    private boolean last;

    /**
     * 비어있는 페이지 여부
     */
    private boolean empty;

    /**
     * 정적 팩토리 메서드
     */
    public static <T> PageResponse<T> of(List<T> content, int currentPage, int pageSize, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        return PageResponse.<T>builder()
            .content(content)
            .currentPage(currentPage)
            .pageSize(pageSize)
            .totalElements(totalElements)
            .totalPages(totalPages)
            .first(currentPage == 1)
            .last(currentPage >= totalPages)
            .empty(content == null || content.isEmpty())
            .build();
    }
}
