package com.common.controller;

import com.common.utils.collection.CollectionUtils;
import com.common.utils.date.DateUtils;
import com.common.utils.file.FileUtils;
import com.common.utils.response.ApiResponse;
import com.common.utils.response.ResponseUtils;
import com.common.utils.string.StringUtils;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 유틸리티 테스트용 REST API Controller
 */
@RestController
@RequestMapping("/api/utils")
@CrossOrigin(origins = "*")
public class UtilsController {

    // ==================== StringUtils API ====================

    @PostMapping("/string/isEmpty")
    public ApiResponse<Boolean> isEmpty(@RequestBody Map<String, String> request) {
        String str = request.get("str");
        boolean result = StringUtils.isEmpty(str);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/isBlank")
    public ApiResponse<Boolean> isBlank(@RequestBody Map<String, String> request) {
        String str = request.get("str");
        boolean result = StringUtils.isBlank(str);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/camelToSnake")
    public ApiResponse<String> camelToSnake(@RequestBody Map<String, String> request) {
        String str = request.get("str");
        String result = StringUtils.camelToSnake(str);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/snakeToCamel")
    public ApiResponse<String> snakeToCamel(@RequestBody Map<String, String> request) {
        String str = request.get("str");
        String result = StringUtils.snakeToCamel(str);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/maskEmail")
    public ApiResponse<String> maskEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String result = StringUtils.maskEmail(email);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/maskPhone")
    public ApiResponse<String> maskPhone(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        String result = StringUtils.maskPhone(phone);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/generateRandomAlphanumeric")
    public ApiResponse<String> generateRandomAlphanumeric(@RequestBody Map<String, Integer> request) {
        int length = request.getOrDefault("length", 10);
        String result = StringUtils.generateRandomAlphanumeric(length);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/generateRandomNumeric")
    public ApiResponse<String> generateRandomNumeric(@RequestBody Map<String, Integer> request) {
        int length = request.getOrDefault("length", 6);
        String result = StringUtils.generateRandomNumeric(length);
        return ResponseUtils.success(result);
    }

    @PostMapping("/string/truncate")
    public ApiResponse<String> truncate(@RequestBody Map<String, Object> request) {
        String str = (String) request.get("str");
        int maxLength = (Integer) request.getOrDefault("maxLength", 10);
        String suffix = (String) request.getOrDefault("suffix", "...");
        String result = StringUtils.truncate(str, maxLength, suffix);
        return ResponseUtils.success(result);
    }

    // ==================== DateUtils API ====================

    @GetMapping("/date/getCurrentDateTime")
    public ApiResponse<String> getCurrentDateTime(@RequestParam(defaultValue = "yyyy-MM-dd HH:mm:ss") String pattern) {
        String result = DateUtils.getCurrentDateTime(pattern);
        return ResponseUtils.success(result);
    }

    @GetMapping("/date/getCurrentDate")
    public ApiResponse<String> getCurrentDate(@RequestParam(defaultValue = "yyyy-MM-dd") String pattern) {
        String result = DateUtils.getCurrentDate(pattern);
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/convertFormat")
    public ApiResponse<String> convertDateFormat(@RequestBody Map<String, String> request) {
        String dateStr = request.get("dateStr");
        String fromPattern = request.get("fromPattern");
        String toPattern = request.get("toPattern");
        String result = DateUtils.convertDateFormat(dateStr, fromPattern, toPattern);
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/getDaysBetween")
    public ApiResponse<Long> getDaysBetween(@RequestBody Map<String, String> request) {
        String startDate = request.get("startDate");
        String endDate = request.get("endDate");
        String pattern = request.getOrDefault("pattern", "yyyy-MM-dd");
        long result = DateUtils.getDaysBetween(startDate, endDate, pattern);
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/getDaysUntil")
    public ApiResponse<Long> getDaysUntil(@RequestBody Map<String, String> request) {
        String targetDate = request.get("targetDate");
        String pattern = request.getOrDefault("pattern", "yyyy-MM-dd");
        long result = DateUtils.getDaysUntil(targetDate, pattern);
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/addDays")
    public ApiResponse<String> addDays(@RequestBody Map<String, Object> request) {
        String dateStr = (String) request.get("dateStr");
        long days = ((Number) request.get("days")).longValue();
        String pattern = (String) request.getOrDefault("pattern", "yyyy-MM-dd");
        String result = DateUtils.addDays(dateStr, days, pattern);
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/convertTimeZone")
    public ApiResponse<String> convertTimeZone(@RequestBody Map<String, String> request) {
        String dateTimeStr = request.get("dateTimeStr");
        String pattern = request.get("pattern");
        String fromZone = request.get("fromZone");
        String toZone = request.get("toZone");
        String result = DateUtils.convertTimeZone(dateTimeStr, pattern, fromZone, toZone);
        return ResponseUtils.success(result);
    }

    @GetMapping("/date/getCurrentTimestamp")
    public ApiResponse<Long> getCurrentTimestamp() {
        long result = DateUtils.getCurrentTimestamp();
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/timestampToString")
    public ApiResponse<String> timestampToString(@RequestBody Map<String, Object> request) {
        long timestamp = ((Number) request.get("timestamp")).longValue();
        String pattern = (String) request.getOrDefault("pattern", "yyyy-MM-dd HH:mm:ss");
        String result = DateUtils.timestampToString(timestamp, pattern);
        return ResponseUtils.success(result);
    }

    @PostMapping("/date/isWeekend")
    public ApiResponse<Boolean> isWeekend(@RequestBody Map<String, String> request) {
        String dateStr = request.get("dateStr");
        String pattern = request.getOrDefault("pattern", "yyyy-MM-dd");
        boolean result = DateUtils.isWeekend(dateStr, pattern);
        return ResponseUtils.success(result);
    }

    // ==================== FileUtils API ====================

    @PostMapping("/file/getExtension")
    public ApiResponse<String> getExtension(@RequestBody Map<String, String> request) {
        String filename = request.get("filename");
        String result = FileUtils.getExtension(filename);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/formatFileSize")
    public ApiResponse<String> formatFileSize(@RequestBody Map<String, Object> request) {
        long size = ((Number) request.get("size")).longValue();
        String result = FileUtils.formatFileSize(size);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/parseFileSize")
    public ApiResponse<Long> parseFileSize(@RequestBody Map<String, String> request) {
        String sizeStr = request.get("sizeStr");
        long result = FileUtils.parseFileSize(sizeStr);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/isImage")
    public ApiResponse<Boolean> isImage(@RequestBody Map<String, String> request) {
        String filename = request.get("filename");
        boolean result = FileUtils.isImage(filename);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/isDocument")
    public ApiResponse<Boolean> isDocument(@RequestBody Map<String, String> request) {
        String filename = request.get("filename");
        boolean result = FileUtils.isDocument(filename);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/sanitizeFilename")
    public ApiResponse<String> sanitizeFilename(@RequestBody Map<String, String> request) {
        String filename = request.get("filename");
        String result = FileUtils.sanitizeFilename(filename);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/generateUniqueFilename")
    public ApiResponse<String> generateUniqueFilename(@RequestBody Map<String, String> request) {
        String originalFilename = request.get("filename");
        String result = FileUtils.generateUniqueFilename(originalFilename);
        return ResponseUtils.success(result);
    }

    @PostMapping("/file/guessMimeType")
    public ApiResponse<String> guessMimeType(@RequestBody Map<String, String> request) {
        String filename = request.get("filename");
        String result = FileUtils.guessMimeType(filename);
        return ResponseUtils.success(result);
    }

    // ==================== CollectionUtils API ====================

    @PostMapping("/collection/isEmpty")
    public ApiResponse<Boolean> isCollectionEmpty(@RequestBody Map<String, List<Object>> request) {
        List<Object> collection = request.get("collection");
        boolean result = CollectionUtils.isEmpty(collection);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/partition")
    public ApiResponse<List<List<Object>>> partition(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) request.get("list");
        int size = (Integer) request.get("size");
        List<List<Object>> result = CollectionUtils.partition(list, size);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/removeDuplicates")
    public ApiResponse<List<Object>> removeDuplicates(@RequestBody Map<String, List<Object>> request) {
        List<Object> list = request.get("list");
        List<Object> result = CollectionUtils.removeDuplicates(list);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/intersection")
    public ApiResponse<List<Object>> intersection(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Object> collection1 = (List<Object>) request.get("collection1");
        @SuppressWarnings("unchecked")
        List<Object> collection2 = (List<Object>) request.get("collection2");
        List<Object> result = CollectionUtils.intersection(collection1, collection2);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/union")
    public ApiResponse<List<Object>> union(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Object> collection1 = (List<Object>) request.get("collection1");
        @SuppressWarnings("unchecked")
        List<Object> collection2 = (List<Object>) request.get("collection2");
        List<Object> result = CollectionUtils.union(collection1, collection2);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/subtract")
    public ApiResponse<List<Object>> subtract(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Object> collection1 = (List<Object>) request.get("collection1");
        @SuppressWarnings("unchecked")
        List<Object> collection2 = (List<Object>) request.get("collection2");
        List<Object> result = CollectionUtils.subtract(collection1, collection2);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/reverse")
    public ApiResponse<List<Object>> reverse(@RequestBody Map<String, List<Object>> request) {
        List<Object> list = request.get("list");
        List<Object> result = CollectionUtils.reverse(list);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/shuffle")
    public ApiResponse<List<Object>> shuffle(@RequestBody Map<String, List<Object>> request) {
        List<Object> list = request.get("list");
        List<Object> result = CollectionUtils.shuffle(list);
        return ResponseUtils.success(result);
    }

    @PostMapping("/collection/paginate")
    public ApiResponse<List<Object>> paginate(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) request.get("list");
        int page = (Integer) request.get("page");
        int pageSize = (Integer) request.get("pageSize");
        List<Object> result = CollectionUtils.paginate(list, page, pageSize);
        return ResponseUtils.success(result);
    }
}
