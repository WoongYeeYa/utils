package com.common.controller;

import com.common.utils.collection.CollectionUtils;
import com.common.utils.date.DateUtils;
import com.common.utils.file.FileUtils;
import com.common.utils.http.HttpUtils;
import com.common.utils.id.IdUtils;
import com.common.utils.json.JsonUtils;
import com.common.utils.money.MoneyUtils;
import com.common.utils.number.NumberUtils;
import com.common.utils.object.ObjectUtils;
import com.common.utils.regex.RegexUtils;
import com.common.utils.response.ApiResponse;
import com.common.utils.response.ResponseUtils;
import com.common.utils.string.StringUtils;
import com.common.utils.validation.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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

    // ==================== ValidationUtils API ====================

    @PostMapping("/validation/isValidEmail")
    public ApiResponse<Boolean> isValidEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean result = ValidationUtils.isValidEmail(email);
        return ResponseUtils.success(result);
    }

    @PostMapping("/validation/isValidPhone")
    public ApiResponse<Boolean> isValidPhone(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        boolean result = ValidationUtils.isValidPhone(phone);
        return ResponseUtils.success(result);
    }

    @PostMapping("/validation/isValidUrl")
    public ApiResponse<Boolean> isValidUrl(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        boolean result = ValidationUtils.isValidUrl(url);
        return ResponseUtils.success(result);
    }

    @PostMapping("/validation/checkPasswordStrength")
    public ApiResponse<Map<String, Object>> checkPasswordStrength(@RequestBody Map<String, String> request) {
        String password = request.get("password");
        int strength = ValidationUtils.checkPasswordStrength(password);
        String strengthText = ValidationUtils.getPasswordStrengthText(password);

        Map<String, Object> result = new HashMap<>();
        result.put("strength", strength);
        result.put("strengthText", strengthText);

        return ResponseUtils.success(result);
    }

    @PostMapping("/validation/isValidCreditCard")
    public ApiResponse<Boolean> isValidCreditCard(@RequestBody Map<String, String> request) {
        String cardNumber = request.get("cardNumber");
        boolean result = ValidationUtils.isValidCreditCard(cardNumber);
        return ResponseUtils.success(result);
    }

    @PostMapping("/validation/isValidIpAddress")
    public ApiResponse<Boolean> isValidIpAddress(@RequestBody Map<String, String> request) {
        String ip = request.get("ip");
        boolean result = ValidationUtils.isValidIpAddress(ip);
        return ResponseUtils.success(result);
    }

    // ==================== JsonUtils API ====================

    @PostMapping("/json/toJson")
    public ApiResponse<String> toJson(@RequestBody Object obj) {
        String result = JsonUtils.toJson(obj);
        return ResponseUtils.success(result);
    }

    @PostMapping("/json/toPrettyJson")
    public ApiResponse<String> toPrettyJson(@RequestBody Object obj) {
        String result = JsonUtils.toPrettyJson(obj);
        return ResponseUtils.success(result);
    }

    @PostMapping("/json/isValidJson")
    public ApiResponse<Boolean> isValidJson(@RequestBody Map<String, String> request) {
        String json = request.get("json");
        boolean result = JsonUtils.isValidJson(json);
        return ResponseUtils.success(result);
    }

    @PostMapping("/json/formatJson")
    public ApiResponse<String> formatJson(@RequestBody Map<String, String> request) {
        String json = request.get("json");
        String result = JsonUtils.formatJson(json);
        return ResponseUtils.success(result);
    }

    @PostMapping("/json/minifyJson")
    public ApiResponse<String> minifyJson(@RequestBody Map<String, String> request) {
        String json = request.get("json");
        String result = JsonUtils.minifyJson(json);
        return ResponseUtils.success(result);
    }

    // ==================== NumberUtils API ====================

    @PostMapping("/number/formatWithComma")
    public ApiResponse<String> formatWithComma(@RequestBody Map<String, Object> request) {
        Number number = (Number) request.get("number");
        String result = NumberUtils.formatWithComma(number.longValue());
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/calculatePercentage")
    public ApiResponse<String> calculatePercentage(@RequestBody Map<String, Object> request) {
        Number value = (Number) request.get("value");
        Number total = (Number) request.get("total");
        String result = NumberUtils.formatPercentage(value.doubleValue(), total.doubleValue());
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/round")
    public ApiResponse<Double> round(@RequestBody Map<String, Object> request) {
        Number value = (Number) request.get("value");
        int scale = (Integer) request.getOrDefault("scale", 2);
        double result = NumberUtils.round(value.doubleValue(), scale);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/randomInt")
    public ApiResponse<Integer> randomInt(@RequestBody Map<String, Integer> request) {
        int min = request.getOrDefault("min", 1);
        int max = request.getOrDefault("max", 100);
        int result = NumberUtils.randomInt(min, max);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/isPrime")
    public ApiResponse<Boolean> isPrime(@RequestBody Map<String, Integer> request) {
        int number = request.get("number");
        boolean result = NumberUtils.isPrime(number);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/ceil")
    public ApiResponse<Double> ceil(@RequestBody Map<String, Object> request) {
        Number value = (Number) request.get("value");
        int scale = (Integer) request.getOrDefault("scale", 2);
        double result = NumberUtils.ceil(value.doubleValue(), scale);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/floor")
    public ApiResponse<Double> floor(@RequestBody Map<String, Object> request) {
        Number value = (Number) request.get("value");
        int scale = (Integer) request.getOrDefault("scale", 2);
        double result = NumberUtils.floor(value.doubleValue(), scale);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/randomDouble")
    public ApiResponse<Double> randomDouble(@RequestBody Map<String, Object> request) {
        Number min = (Number) request.getOrDefault("min", 0);
        Number max = (Number) request.getOrDefault("max", 1);
        double result = NumberUtils.randomDouble(min.doubleValue(), max.doubleValue());
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/average")
    public ApiResponse<Double> average(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Number> numbers = (List<Number>) request.get("numbers");
        double[] doubleArray = numbers.stream().mapToDouble(Number::doubleValue).toArray();
        double result = NumberUtils.average(doubleArray);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/gcd")
    public ApiResponse<Integer> gcd(@RequestBody Map<String, Integer> request) {
        int a = request.get("a");
        int b = request.get("b");
        int result = NumberUtils.gcd(a, b);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/lcm")
    public ApiResponse<Integer> lcm(@RequestBody Map<String, Integer> request) {
        int a = request.get("a");
        int b = request.get("b");
        int result = NumberUtils.lcm(a, b);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/isEven")
    public ApiResponse<Boolean> isEven(@RequestBody Map<String, Integer> request) {
        int number = request.get("number");
        boolean result = NumberUtils.isEven(number);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/isOdd")
    public ApiResponse<Boolean> isOdd(@RequestBody Map<String, Integer> request) {
        int number = request.get("number");
        boolean result = NumberUtils.isOdd(number);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/toRoman")
    public ApiResponse<String> toRoman(@RequestBody Map<String, Integer> request) {
        int number = request.get("number");
        String result = NumberUtils.toRoman(number);
        return ResponseUtils.success(result);
    }

    @PostMapping("/number/formatBytes")
    public ApiResponse<String> formatBytes(@RequestBody Map<String, Object> request) {
        Number bytes = (Number) request.get("bytes");
        String result = NumberUtils.formatBytes(bytes.longValue());
        return ResponseUtils.success(result);
    }

    // ==================== HttpUtils API ====================

    @GetMapping("/http/getClientIp")
    public ApiResponse<String> getClientIp(HttpServletRequest request) {
        String result = HttpUtils.getClientIp(request);
        return ResponseUtils.success(result);
    }

    @GetMapping("/http/getBrowserType")
    public ApiResponse<String> getBrowserType(HttpServletRequest request) {
        String result = HttpUtils.getBrowserType(request);
        return ResponseUtils.success(result);
    }

    @GetMapping("/http/isMobileDevice")
    public ApiResponse<Boolean> isMobileDevice(HttpServletRequest request) {
        boolean result = HttpUtils.isMobileDevice(request);
        return ResponseUtils.success(result);
    }

    @PostMapping("/http/parseQueryString")
    public ApiResponse<Map<String, String>> parseQueryString(@RequestBody Map<String, String> request) {
        String queryString = request.get("queryString");
        Map<String, String> result = HttpUtils.parseQueryString(queryString);
        return ResponseUtils.success(result);
    }

    @PostMapping("/http/buildQueryString")
    public ApiResponse<String> buildQueryString(@RequestBody Map<String, String> params) {
        String result = HttpUtils.buildQueryString(params);
        return ResponseUtils.success(result);
    }

    // ==================== IdUtils API ====================

    @GetMapping("/id/generateUuid")
    public ApiResponse<String> generateUuid() {
        String result = IdUtils.generateUuid();
        return ResponseUtils.success(result);
    }

    @GetMapping("/id/generateShortUuid")
    public ApiResponse<String> generateShortUuid() {
        String result = IdUtils.generateShortUuid();
        return ResponseUtils.success(result);
    }

    @GetMapping("/id/generateSnowflakeId")
    public ApiResponse<String> generateSnowflakeId() {
        String result = IdUtils.generateSnowflakeIdString();
        return ResponseUtils.success(result);
    }

    @GetMapping("/id/generateNanoId")
    public ApiResponse<String> generateNanoId() {
        String result = IdUtils.generateNanoId();
        return ResponseUtils.success(result);
    }

    @GetMapping("/id/generateUlid")
    public ApiResponse<String> generateUlid() {
        String result = IdUtils.generateUlid();
        return ResponseUtils.success(result);
    }

    @PostMapping("/id/generateRandomId")
    public ApiResponse<String> generateRandomId(@RequestBody Map<String, Integer> request) {
        int length = request.getOrDefault("length", 10);
        String result = IdUtils.generateRandomId(length);
        return ResponseUtils.success(result);
    }

    // ==================== RegexUtils API ====================

    @PostMapping("/regex/matches")
    public ApiResponse<Boolean> regexMatches(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String regex = request.get("regex");
        boolean result = RegexUtils.matches(text, regex);
        return ResponseUtils.success(result);
    }

    @PostMapping("/regex/findAll")
    public ApiResponse<List<String>> findAll(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String regex = request.get("regex");
        List<String> result = RegexUtils.findAll(text, regex);
        return ResponseUtils.success(result);
    }

    @PostMapping("/regex/replace")
    public ApiResponse<String> regexReplace(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String regex = request.get("regex");
        String replacement = request.get("replacement");
        String result = RegexUtils.replace(text, regex, replacement);
        return ResponseUtils.success(result);
    }

    @PostMapping("/regex/isEmail")
    public ApiResponse<Boolean> regexIsEmail(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        boolean result = RegexUtils.isEmail(text);
        return ResponseUtils.success(result);
    }

    @PostMapping("/regex/extractEmails")
    public ApiResponse<List<String>> extractEmails(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        List<String> result = RegexUtils.extractEmails(text);
        return ResponseUtils.success(result);
    }

    @PostMapping("/regex/removeHtmlTags")
    public ApiResponse<String> removeHtmlTags(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String result = RegexUtils.removeHtmlTags(text);
        return ResponseUtils.success(result);
    }

    // ==================== MoneyUtils API ====================

    @PostMapping("/money/formatKRW")
    public ApiResponse<String> formatKRW(@RequestBody Map<String, Object> request) {
        Number amount = (Number) request.get("amount");
        String result = MoneyUtils.formatKRW(amount.longValue());
        return ResponseUtils.success(result);
    }

    @PostMapping("/money/convertCurrency")
    public ApiResponse<String> convertCurrency(@RequestBody Map<String, Object> request) {
        Number amount = (Number) request.get("amount");
        String fromCurrency = (String) request.get("fromCurrency");
        String toCurrency = (String) request.get("toCurrency");
        BigDecimal result = MoneyUtils.convertCurrency(
                new BigDecimal(amount.toString()), fromCurrency, toCurrency);
        return ResponseUtils.success(result.toString());
    }

    @PostMapping("/money/calculateDiscount")
    public ApiResponse<String> calculateDiscount(@RequestBody Map<String, Object> request) {
        Number originalPrice = (Number) request.get("originalPrice");
        int discountPercent = (Integer) request.get("discountPercent");
        BigDecimal result = MoneyUtils.calculateDiscount(
                new BigDecimal(originalPrice.toString()), discountPercent);
        return ResponseUtils.success(result.toString());
    }

    @PostMapping("/money/addVAT")
    public ApiResponse<String> addVAT(@RequestBody Map<String, Object> request) {
        Number amount = (Number) request.get("amount");
        BigDecimal result = MoneyUtils.addVAT(new BigDecimal(amount.toString()));
        return ResponseUtils.success(result.toString());
    }

    @PostMapping("/money/splitAmount")
    public ApiResponse<String> splitAmount(@RequestBody Map<String, Object> request) {
        Number amount = (Number) request.get("amount");
        int numberOfPeople = (Integer) request.get("numberOfPeople");
        BigDecimal result = MoneyUtils.splitAmount(
                new BigDecimal(amount.toString()), numberOfPeople);
        return ResponseUtils.success(result.toString());
    }

    // ==================== ObjectUtils API ====================

    @PostMapping("/object/isNull")
    public ApiResponse<Boolean> isNull(@RequestBody Map<String, Object> request) {
        Object obj = request.get("obj");
        boolean result = ObjectUtils.isNull(obj);
        return ResponseUtils.success(result);
    }

    @PostMapping("/object/toJsonString")
    public ApiResponse<String> objectToJsonString(@RequestBody Object obj) {
        String result = ObjectUtils.toJsonString(obj);
        return ResponseUtils.success(result);
    }

    @PostMapping("/object/isEmpty")
    public ApiResponse<Boolean> isObjectEmpty(@RequestBody Map<String, Object> request) {
        Object obj = request.get("obj");
        boolean result = ObjectUtils.isEmpty(obj);
        return ResponseUtils.success(result);
    }

    @GetMapping("/object/test")
    public ApiResponse<Map<String, Object>> objectTest() {
        Map<String, Object> testObj = new HashMap<>();
        testObj.put("name", "John Doe");
        testObj.put("age", 30);
        testObj.put("email", "john@example.com");

        Map<String, Object> result = new HashMap<>();
        result.put("original", testObj);
        result.put("json", ObjectUtils.toJsonString(testObj));
        result.put("isEmpty", ObjectUtils.isEmpty(testObj));
        result.put("className", ObjectUtils.getClassName(testObj));

        return ResponseUtils.success(result);
    }
}
