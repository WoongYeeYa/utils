package com.common.utils.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpUtils - HTTP 통신 관련 유틸리티
 */
public class HttpUtils {

    private static final String UNKNOWN = "unknown";

    private HttpUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 클라이언트 IP 주소 추출
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // X-Forwarded-For에 여러 IP가 있을 경우 첫 번째 IP 사용
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }

    /**
     * User-Agent 추출
     */
    public static String getUserAgent(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeader("User-Agent");
    }

    /**
     * 브라우저 종류 확인
     */
    public static String getBrowserType(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        if (userAgent == null) {
            return "Unknown";
        }

        userAgent = userAgent.toLowerCase();

        if (userAgent.contains("edg")) {
            return "Edge";
        } else if (userAgent.contains("chrome")) {
            return "Chrome";
        } else if (userAgent.contains("firefox")) {
            return "Firefox";
        } else if (userAgent.contains("safari") && !userAgent.contains("chrome")) {
            return "Safari";
        } else if (userAgent.contains("opera") || userAgent.contains("opr")) {
            return "Opera";
        } else if (userAgent.contains("msie") || userAgent.contains("trident")) {
            return "Internet Explorer";
        }

        return "Unknown";
    }

    /**
     * 모바일 디바이스인지 확인
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        if (userAgent == null) {
            return false;
        }

        userAgent = userAgent.toLowerCase();
        return userAgent.contains("mobile") ||
                userAgent.contains("android") ||
                userAgent.contains("iphone") ||
                userAgent.contains("ipad") ||
                userAgent.contains("ipod") ||
                userAgent.contains("blackberry") ||
                userAgent.contains("windows phone");
    }

    /**
     * URL 인코딩
     */
    public static String urlEncode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode URL", e);
        }
    }

    /**
     * URL 디코딩
     */
    public static String urlDecode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to decode URL", e);
        }
    }

    /**
     * 쿼리 파라미터 맵으로 변환
     */
    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> params = new HashMap<>();

        if (queryString == null || queryString.trim().isEmpty()) {
            return params;
        }

        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                String key = urlDecode(pair.substring(0, idx));
                String value = urlDecode(pair.substring(idx + 1));
                params.put(key, value);
            }
        }

        return params;
    }

    /**
     * 맵을 쿼리 스트링으로 변환
     */
    public static String buildQueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!first) {
                sb.append("&");
            }
            sb.append(urlEncode(entry.getKey()));
            sb.append("=");
            sb.append(urlEncode(entry.getValue()));
            first = false;
        }

        return sb.toString();
    }

    /**
     * URL에 쿼리 파라미터 추가
     */
    public static String addQueryParams(String url, Map<String, String> params) {
        if (url == null) {
            return null;
        }
        if (params == null || params.isEmpty()) {
            return url;
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        params.forEach(builder::queryParam);

        return builder.build().toUriString();
    }

    /**
     * Referer 추출
     */
    public static String getReferer(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeader("Referer");
    }

    /**
     * Content-Type 추출
     */
    public static String getContentType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getContentType();
    }

    /**
     * JSON 요청인지 확인
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        String contentType = getContentType(request);
        return contentType != null && contentType.toLowerCase().contains("application/json");
    }

    /**
     * AJAX 요청인지 확인
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith);
    }

    /**
     * 요청 메서드 확인
     */
    public static String getMethod(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getMethod();
    }

    /**
     * GET 요청인지 확인
     */
    public static boolean isGetRequest(HttpServletRequest request) {
        return "GET".equalsIgnoreCase(getMethod(request));
    }

    /**
     * POST 요청인지 확인
     */
    public static boolean isPostRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(getMethod(request));
    }

    /**
     * PUT 요청인지 확인
     */
    public static boolean isPutRequest(HttpServletRequest request) {
        return "PUT".equalsIgnoreCase(getMethod(request));
    }

    /**
     * DELETE 요청인지 확인
     */
    public static boolean isDeleteRequest(HttpServletRequest request) {
        return "DELETE".equalsIgnoreCase(getMethod(request));
    }

    /**
     * 요청 URL 추출 (전체)
     */
    public static String getFullUrl(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    /**
     * 요청 경로 추출
     */
    public static String getRequestPath(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getRequestURI();
    }

    /**
     * Base URL 추출 (프로토콜 + 도메인 + 포트)
     */
    public static String getBaseUrl(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((scheme.equals("http") && serverPort != 80) ||
                (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        return url.toString();
    }

    /**
     * 모든 헤더를 맵으로 변환
     */
    public static Map<String, String> getAllHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();

        if (request == null) {
            return headers;
        }

        var headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        return headers;
    }

    /**
     * Accept-Language 헤더에서 선호 언어 추출
     */
    public static String getPreferredLanguage(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return null;
        }

        // 첫 번째 언어 반환
        String[] languages = acceptLanguage.split(",");
        if (languages.length > 0) {
            String firstLanguage = languages[0].trim();
            int semicolonIndex = firstLanguage.indexOf(';');
            if (semicolonIndex > 0) {
                return firstLanguage.substring(0, semicolonIndex);
            }
            return firstLanguage;
        }

        return null;
    }
}
