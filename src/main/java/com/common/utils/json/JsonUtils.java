package com.common.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JsonUtils - JSON 처리 유틸리티
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 기본 설정
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 객체를 JSON 문자열로 변환
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * 객체를 예쁘게 포맷된 JSON 문자열로 변환
     */
    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to pretty JSON", e);
        }
    }

    /**
     * JSON 문자열을 객체로 변환
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    /**
     * JSON 문자열을 TypeReference를 사용하여 객체로 변환 (제네릭 타입용)
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to object", e);
        }
    }

    /**
     * JSON 문자열을 Map으로 변환
     */
    public static Map<String, Object> toMap(String json) {
        return fromJson(json, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * JSON 문자열을 List로 변환
     */
    public static <T> List<T> toList(String json, Class<T> elementClass) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to list", e);
        }
    }

    /**
     * JSON 유효성 검증
     */
    public static boolean isValidJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }

        try {
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    /**
     * JSON 문자열을 포맷팅 (Pretty Print)
     */
    public static String formatJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return json;
        }

        try {
            Object obj = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }

    /**
     * JSON 문자열을 압축 (공백 제거)
     */
    public static String minifyJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return json;
        }

        try {
            Object obj = objectMapper.readValue(json, Object.class);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to minify JSON", e);
        }
    }

    /**
     * 두 JSON 문자열 병합 (두 번째가 우선)
     */
    public static String mergeJson(String json1, String json2) {
        if (json1 == null || json1.trim().isEmpty()) {
            return json2;
        }
        if (json2 == null || json2.trim().isEmpty()) {
            return json1;
        }

        try {
            Map<String, Object> map1 = toMap(json1);
            Map<String, Object> map2 = toMap(json2);
            map1.putAll(map2);
            return toJson(map1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge JSON", e);
        }
    }

    /**
     * JSON에서 특정 경로의 값 추출
     */
    public static String getValueByPath(String json, String path) {
        if (json == null || json.trim().isEmpty() || path == null) {
            return null;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode valueNode = rootNode.at(path);

            if (valueNode.isMissingNode()) {
                return null;
            }

            return valueNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to get value from JSON", e);
        }
    }

    /**
     * Map을 JSON 문자열로 변환
     */
    public static String mapToJson(Map<String, Object> map) {
        return toJson(map);
    }

    /**
     * 객체를 Map으로 변환
     */
    public static Map<String, Object> objectToMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * Map을 객체로 변환
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * JSON 문자열을 JsonNode로 변환
     */
    public static JsonNode parseJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    /**
     * ObjectMapper 인스턴스 반환 (고급 사용자용)
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
