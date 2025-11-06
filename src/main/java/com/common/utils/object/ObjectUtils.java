package com.common.utils.object;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * ObjectUtils - 객체 처리 유틸리티
 */
public class ObjectUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ObjectUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * null 체크
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * null이 아닌지 체크
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * 여러 객체 중 하나라도 null인지 체크
     */
    public static boolean isAnyNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 모든 객체가 null인지 체크
     */
    public static boolean isAllNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object obj : objects) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 모든 객체가 null이 아닌지 체크
     */
    public static boolean isAllNotNull(Object... objects) {
        if (objects == null) {
            return false;
        }
        for (Object obj : objects) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * null이면 기본값 반환
     */
    public static <T> T defaultIfNull(T obj, T defaultValue) {
        return obj != null ? obj : defaultValue;
    }

    /**
     * 첫 번째로 null이 아닌 객체 반환
     */
    @SafeVarargs
    public static <T> T firstNonNull(T... objects) {
        if (objects == null) {
            return null;
        }
        for (T obj : objects) {
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Deep Copy (직렬화 방식)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepCopy(T obj) {
        if (obj == null) {
            return null;
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to deep copy object", e);
        }
    }

    /**
     * Deep Copy (JSON 방식)
     */
    public static <T> T deepCopyViaJson(T obj, Class<T> clazz) {
        if (obj == null) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(obj);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deep copy object via JSON", e);
        }
    }

    /**
     * 객체를 Map으로 변환
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return new HashMap<>();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> map = objectMapper.convertValue(obj, Map.class);
        return map;
    }

    /**
     * Map을 객체로 변환
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * 객체의 모든 필드를 Map으로 변환 (Reflection 사용)
     */
    public static Map<String, Object> getAllFields(Object obj) {
        if (obj == null) {
            return new HashMap<>();
        }

        Map<String, Object> fieldsMap = new LinkedHashMap<>();
        Class<?> clazz = obj.getClass();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    fieldsMap.put(field.getName(), field.get(obj));
                } catch (IllegalAccessException e) {
                    // 접근할 수 없는 필드는 스킵
                }
            }
            clazz = clazz.getSuperclass();
        }

        return fieldsMap;
    }

    /**
     * 객체의 특정 필드 값 가져오기
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 객체의 특정 필드 값 설정하기
     */
    public static boolean setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null || fieldName == null) {
            return false;
        }

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 두 객체가 같은지 비교 (equals 사용)
     */
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    /**
     * 두 객체의 내용이 같은지 비교 (JSON 변환 후 비교)
     */
    public static boolean deepEquals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }

        try {
            String json1 = objectMapper.writeValueAsString(obj1);
            String json2 = objectMapper.writeValueAsString(obj2);
            return json1.equals(json2);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 객체를 문자열로 변환 (toString)
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString();
    }

    /**
     * 객체를 JSON 문자열로 변환
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * 객체를 예쁘게 포맷된 JSON 문자열로 변환
     */
    public static String toPrettyJsonString(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to pretty JSON", e);
        }
    }

    /**
     * 객체가 비어있는지 확인 (null, 빈 문자열, 빈 컬렉션 등)
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj.getClass().isArray()) {
            return ((Object[]) obj).length == 0;
        }

        return false;
    }

    /**
     * 객체가 비어있지 않은지 확인
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 객체의 클래스 이름 반환
     */
    public static String getClassName(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName();
    }

    /**
     * 객체의 단순 클래스 이름 반환
     */
    public static String getSimpleClassName(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getSimpleName();
    }

    /**
     * 객체가 특정 타입인지 확인
     */
    public static boolean isInstanceOf(Object obj, Class<?> clazz) {
        if (obj == null || clazz == null) {
            return false;
        }
        return clazz.isInstance(obj);
    }

    /**
     * 객체의 HashCode 반환 (null-safe)
     */
    public static int hashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    /**
     * 여러 객체의 HashCode 계산
     */
    public static int hash(Object... objects) {
        return Arrays.hashCode(objects);
    }

    /**
     * 객체 복제 (Cloneable 인터페이스 구현 필요)
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T obj) {
        if (obj == null) {
            return null;
        }

        if (!(obj instanceof Cloneable)) {
            throw new IllegalArgumentException("Object must implement Cloneable interface");
        }

        try {
            return (T) obj.getClass().getMethod("clone").invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clone object", e);
        }
    }

    /**
     * 두 객체의 필드 값을 비교하여 다른 필드 목록 반환
     */
    public static List<String> getDifferentFields(Object obj1, Object obj2) {
        List<String> differentFields = new ArrayList<>();

        if (obj1 == null || obj2 == null) {
            return differentFields;
        }

        if (!obj1.getClass().equals(obj2.getClass())) {
            throw new IllegalArgumentException("Objects must be of the same type");
        }

        Map<String, Object> fields1 = getAllFields(obj1);
        Map<String, Object> fields2 = getAllFields(obj2);

        for (String fieldName : fields1.keySet()) {
            Object value1 = fields1.get(fieldName);
            Object value2 = fields2.get(fieldName);

            if (!equals(value1, value2)) {
                differentFields.add(fieldName);
            }
        }

        return differentFields;
    }
}
