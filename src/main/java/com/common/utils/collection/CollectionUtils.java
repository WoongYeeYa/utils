package com.common.utils.collection;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 컬렉션 처리 유틸리티 클래스
 */
public class CollectionUtils {

    /**
     * 컬렉션이 null이거나 비어있는지 확인
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 컬렉션이 null이 아니고 비어있지 않은지 확인
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Map이 null이거나 비어있는지 확인
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Map이 null이 아니고 비어있지 않은지 확인
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 리스트를 지정된 크기로 분할 (페이징)
     * @param list 원본 리스트
     * @param size 분할 크기
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (isEmpty(list) || size <= 0) {
            return Collections.emptyList();
        }

        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    /**
     * 리스트에서 중복 제거
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    /**
     * 두 컬렉션의 교집합 반환
     */
    public static <T> List<T> intersection(Collection<T> collection1, Collection<T> collection2) {
        if (isEmpty(collection1) || isEmpty(collection2)) {
            return new ArrayList<>();
        }

        Set<T> set = new HashSet<>(collection1);
        return collection2.stream()
            .filter(set::contains)
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * 두 컬렉션의 합집합 반환
     */
    public static <T> List<T> union(Collection<T> collection1, Collection<T> collection2) {
        Set<T> union = new HashSet<>();
        if (isNotEmpty(collection1)) {
            union.addAll(collection1);
        }
        if (isNotEmpty(collection2)) {
            union.addAll(collection2);
        }
        return new ArrayList<>(union);
    }

    /**
     * 첫 번째 컬렉션에서 두 번째 컬렉션의 요소를 제거 (차집합)
     */
    public static <T> List<T> subtract(Collection<T> collection1, Collection<T> collection2) {
        if (isEmpty(collection1)) {
            return new ArrayList<>();
        }
        if (isEmpty(collection2)) {
            return new ArrayList<>(collection1);
        }

        Set<T> set = new HashSet<>(collection2);
        return collection1.stream()
            .filter(item -> !set.contains(item))
            .collect(Collectors.toList());
    }

    /**
     * 컬렉션의 첫 번째 요소 반환 (null-safe)
     */
    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    /**
     * 리스트의 마지막 요소 반환 (null-safe)
     */
    public static <T> T getLast(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * 리스트 역순 정렬
     */
    public static <T> List<T> reverse(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> reversed = new ArrayList<>(list);
        Collections.reverse(reversed);
        return reversed;
    }

    /**
     * 리스트를 특정 속성으로 그룹화
     */
    public static <T, K> Map<K, List<T>> groupBy(List<T> list, Function<T, K> keyExtractor) {
        if (isEmpty(list)) {
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.groupingBy(keyExtractor));
    }

    /**
     * 리스트를 Map으로 변환
     */
    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        if (isEmpty(list)) {
            return new HashMap<>();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1));
    }

    /**
     * 컬렉션의 null 요소 제거
     */
    public static <T> List<T> removeNulls(Collection<T> collection) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 리스트 셔플 (무작위 섞기)
     */
    public static <T> List<T> shuffle(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        List<T> shuffled = new ArrayList<>(list);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    /**
     * 안전한 subList (인덱스 범위 검증)
     */
    public static <T> List<T> safeSubList(List<T> list, int fromIndex, int toIndex) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }

        int size = list.size();
        int safeFrom = Math.max(0, Math.min(fromIndex, size));
        int safeTo = Math.max(safeFrom, Math.min(toIndex, size));

        return new ArrayList<>(list.subList(safeFrom, safeTo));
    }

    /**
     * 페이지네이션을 위한 subList
     * @param list 원본 리스트
     * @param page 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 크기
     */
    public static <T> List<T> paginate(List<T> list, int page, int pageSize) {
        if (isEmpty(list) || page < 1 || pageSize < 1) {
            return new ArrayList<>();
        }

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, list.size());

        if (fromIndex >= list.size()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(list.subList(fromIndex, toIndex));
    }

    /**
     * 두 컬렉션이 동일한 요소를 포함하는지 확인 (순서 무시)
     */
    public static <T> boolean isEqualCollection(Collection<T> col1, Collection<T> col2) {
        if (col1 == col2) {
            return true;
        }
        if (col1 == null || col2 == null || col1.size() != col2.size()) {
            return false;
        }

        Map<T, Integer> elementCounts = new HashMap<>();
        for (T element : col1) {
            elementCounts.put(element, elementCounts.getOrDefault(element, 0) + 1);
        }

        for (T element : col2) {
            Integer count = elementCounts.get(element);
            if (count == null || count == 0) {
                return false;
            }
            elementCounts.put(element, count - 1);
        }

        return true;
    }

    /**
     * 컬렉션 크기 반환 (null-safe)
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * Map 크기 반환 (null-safe)
     */
    public static int size(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }
}
