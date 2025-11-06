# Java Web Utils

Java 웹 프로젝트에서 범용적으로 사용할 수 있는 유틸리티 라이브러리입니다.

## 주요 기능

### 1. StringUtils
문자열 처리 관련 유틸리티

- `isEmpty()`, `isBlank()` - null-safe 문자열 검증
- `camelToSnake()`, `snakeToCamel()` - 케이스 변환
- `maskEmail()`, `maskPhone()` - 민감정보 마스킹
- `generateRandomAlphanumeric()`, `generateRandomNumeric()` - 랜덤 문자열 생성
- `truncate()` - 문자열 자르기
- `capitalize()`, `uncapitalize()` - 대소문자 변환

### 2. DateUtils
날짜/시간 처리 관련 유틸리티

- `getCurrentDateTime()`, `getCurrentDate()` - 현재 날짜/시간 조회
- `convertDateFormat()` - 날짜 포맷 변환
- `getDaysBetween()`, `getDaysUntil()` - 날짜 차이 계산
- `addDays()`, `addMonths()`, `addYears()` - 날짜 연산
- `convertTimeZone()` - 타임존 변환
- `timestampToString()`, `stringToTimestamp()` - 타임스탬프 변환
- `isWeekend()`, `isToday()` - 날짜 판별

### 3. FileUtils
파일 처리 관련 유틸리티

- `getExtension()` - 파일 확장자 추출
- `formatFileSize()`, `parseFileSize()` - 파일 크기 변환
- `isImage()`, `isDocument()`, `isVideo()` - 파일 타입 확인
- `sanitizeFilename()` - 안전한 파일명 생성
- `generateUniqueFilename()` - 고유 파일명 생성
- `guessMimeType()` - MIME 타입 추측
- `saveFile()`, `deleteFile()` - 파일 저장/삭제

### 4. CollectionUtils
컬렉션 처리 관련 유틸리티

- `isEmpty()`, `isNotEmpty()` - null-safe 컬렉션 검증
- `partition()` - 리스트 분할
- `removeDuplicates()` - 중복 제거
- `intersection()`, `union()`, `subtract()` - 집합 연산
- `reverse()`, `shuffle()` - 리스트 조작
- `paginate()` - 페이지네이션
- `groupBy()`, `toMap()` - 변환 유틸리티

### 5. ResponseUtils
API 응답 통일 관련 유틸리티

- `success()` - 성공 응답 생성
- `fail()` - 실패 응답 생성
- `pageSuccess()` - 페이지네이션 응답 생성
- `ApiResponse` - 공통 응답 DTO
- `PageResponse` - 페이지네이션 응답 DTO
- `ErrorCode` - 공통 에러 코드

### 6. ValidationUtils
유효성 검증 관련 유틸리티

- `isValidEmail()`, `isValidPhone()`, `isValidUrl()` - 형식 검증
- `isValidCreditCard()` - 신용카드 번호 검증 (Luhn 알고리즘)
- `isValidResidentNumber()` - 주민등록번호 검증
- `isValidBusinessNumber()` - 사업자등록번호 검증
- `checkPasswordStrength()` - 비밀번호 강도 체크
- `isNumeric()`, `isAlpha()`, `isAlphanumeric()` - 문자 타입 확인
- `isValidIpAddress()` - IP 주소 검증

### 7. JsonUtils
JSON 처리 관련 유틸리티

- `toJson()`, `toPrettyJson()` - 객체를 JSON으로 변환
- `fromJson()` - JSON을 객체로 변환
- `toMap()`, `toList()` - JSON을 컬렉션으로 변환
- `isValidJson()` - JSON 유효성 검증
- `formatJson()`, `minifyJson()` - JSON 포맷팅
- `mergeJson()` - JSON 병합
- `getValueByPath()` - JSON 경로로 값 추출

### 8. NumberUtils
숫자 처리 관련 유틸리티

- `formatWithComma()` - 천 단위 콤마 포맷
- `calculatePercentage()`, `formatPercentage()` - 백분율 계산
- `round()`, `ceil()`, `floor()` - 반올림/올림/내림
- `isInRange()`, `clamp()` - 범위 체크 및 제한
- `randomInt()`, `randomDouble()` - 랜덤 숫자 생성
- `average()` - 평균 계산
- `gcd()`, `lcm()` - 최대공약수/최소공배수
- `isPrime()` - 소수 판별

### 9. HttpUtils
HTTP 통신 관련 유틸리티

- `getClientIp()` - 클라이언트 IP 추출
- `getUserAgent()`, `getBrowserType()` - 브라우저 정보 추출
- `isMobileDevice()` - 모바일 디바이스 확인
- `urlEncode()`, `urlDecode()` - URL 인코딩/디코딩
- `parseQueryString()`, `buildQueryString()` - 쿼리 스트링 처리
- `isAjaxRequest()`, `isJsonRequest()` - 요청 타입 확인
- `getFullUrl()`, `getBaseUrl()` - URL 추출

### 10. ExcelUtils
Excel 파일 처리 관련 유틸리티

- `readExcelToList()` - Excel을 List<Map>으로 읽기
- `writeListToExcel()` - List<Map>을 Excel로 쓰기
- `excelToCsv()`, `csvToExcel()` - Excel ↔ CSV 변환
- `getSheetCount()`, `getSheetName()` - 시트 정보 조회
- `readSheetToList()` - 특정 시트 읽기
- `isValidExcelFile()` - Excel 파일 유효성 확인

### 11. ObjectUtils
객체 처리 관련 유틸리티

- `isNull()`, `isNotNull()` - null 체크
- `defaultIfNull()`, `firstNonNull()` - null 처리
- `deepCopy()` - 깊은 복사
- `objectToMap()`, `mapToObject()` - 객체 ↔ Map 변환
- `equals()`, `deepEquals()` - 객체 비교
- `isEmpty()`, `isNotEmpty()` - 빈 객체 확인
- `getFieldValue()`, `setFieldValue()` - 필드 값 처리

### 12. IdUtils
ID 생성 관련 유틸리티

- `generateUuid()` - UUID 생성
- `generateShortUuid()` - 짧은 UUID 생성
- `generateSnowflakeId()` - Snowflake ID 생성
- `generateNanoId()` - Nano ID 생성
- `generateUlid()` - ULID 생성
- `generateObjectId()` - MongoDB ObjectId 스타일 생성
- `generateRandomId()` - 랜덤 ID 생성

### 13. RegexUtils
정규식 관련 유틸리티

- `matches()`, `matchesIgnoreCase()` - 패턴 매칭
- `find()`, `findAll()` - 패턴 검색
- `replace()`, `replaceFirst()` - 패턴 치환
- `extractEmails()`, `extractUrls()` - 정보 추출
- `removeHtmlTags()` - HTML 태그 제거
- `isEmail()`, `isUrl()`, `isNumeric()` - 형식 확인
- `Patterns` - 자주 사용하는 정규식 패턴 모음

### 14. MoneyUtils
금액 처리 관련 유틸리티

- `formatKRW()`, `format()` - 통화 포맷팅
- `convertCurrency()` - 환율 계산
- `add()`, `subtract()`, `multiply()`, `divide()` - 금액 연산
- `calculateDiscount()` - 할인 금액 계산
- `calculateVAT()`, `addVAT()`, `removeVAT()` - 부가세 처리
- `calculateTip()`, `addTip()` - 팁 계산
- `splitAmount()` - 금액 N등분
- `roundToWon()`, `roundToThousandWon()` - 단위 반올림

## 의존성 정보

### 📦 의존성이 필요 없는 유틸리티 (순수 Java)

다음 유틸리티들은 외부 라이브러리 없이 순수 Java만으로 동작합니다:

- **StringUtils** - 문자열 처리
- **DateUtils** - 날짜/시간 처리
- **FileUtils** - 파일 처리
- **CollectionUtils** - 컬렉션 처리
- **ValidationUtils** - 유효성 검증
- **NumberUtils** - 숫자 처리
- **RegexUtils** - 정규식 처리
- **IdUtils** - ID 생성
- **MoneyUtils** - 금액 처리

### 📦 의존성이 필요한 유틸리티

#### 1. JsonUtils & ObjectUtils
Jackson 라이브러리가 필요합니다.

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.0</version>
</dependency>
```

#### 2. ExcelUtils
Apache POI 라이브러리가 필요합니다.

```xml
<!-- Excel 파일 처리 -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.5</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
```

#### 3. HttpUtils
Servlet API가 필요합니다 (Spring Boot 프로젝트에는 기본 포함).

```xml
<!-- Spring Boot 프로젝트가 아닌 경우 -->
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
    <scope>provided</scope>
</dependency>
```

#### 4. ResponseUtils
Spring Web이 필요합니다 (Controller에서 사용).

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 💡 다른 프로젝트에서 사용하기

#### 방법 1: 필요한 클래스만 복사

특정 유틸리티만 필요한 경우, 해당 Java 파일만 복사하고 필요한 의존성을 추가하세요.

예시: `StringUtils`와 `ValidationUtils`만 사용
```bash
# 복사
src/main/java/com/common/utils/string/StringUtils.java
src/main/java/com/common/utils/validation/ValidationUtils.java

# 의존성 추가 불필요 (순수 Java)
```

예시: `ExcelUtils`만 사용
```bash
# 복사
src/main/java/com/common/utils/excel/ExcelUtils.java

# pom.xml에 Apache POI 의존성 추가
```

#### 방법 2: 전체 프로젝트를 라이브러리로 사용

1. 이 프로젝트를 Maven으로 빌드:
```bash
mvn clean install
```

2. 다른 프로젝트의 pom.xml에 추가:
```xml
<dependency>
    <groupId>com.common</groupId>
    <artifactId>web-utils</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- 사용할 유틸리티에 따라 추가 의존성 포함 -->
```

3. 사용할 유틸리티에 따라 필요한 의존성 추가 (위 참고)

## 시작하기

### 필수 요구사항

⚠️ **중요: 이 라이브러리는 최신 환경 전용입니다**

- **JDK 17 이상** (필수)
- Maven 3.6 이상
- Spring Boot 3.x (Spring 관련 유틸리티 사용 시)

### ⚠️ 호환성 제약사항

**이 라이브러리는 구버전 환경과 호환되지 않습니다:**

| 환경 | 호환 여부 | 이유 |
|------|----------|------|
| JDK 8 | ❌ 불가능 | Java 14+ switch expression 사용 |
| JDK 11 | ❌ 불가능 | Java 14+ switch expression 사용 |
| **JDK 17+** | ✅ 가능 | 정상 동작 |
| Spring Boot 2.x | ❌ 불가능 | jakarta.servlet 패키지 사용 (javax 아님) |
| **Spring Boot 3.x** | ✅ 가능 | 정상 동작 |

**컴파일 에러가 발생하는 경우:**
- JDK 8/11 환경: switch expression 문법 에러
- Spring Boot 2.x: jakarta.servlet 패키지를 찾을 수 없음

**해결 방법:**
- JDK를 17 이상으로 업그레이드
- Spring Boot를 3.x로 업그레이드
- 또는 레거시 환경에서는 필요한 메서드만 수동으로 수정하여 사용

### 설치 및 실행

1. 프로젝트 클론
```bash
git clone <repository-url>
cd utils
```

2. Maven 빌드
```bash
mvn clean install
```

3. 애플리케이션 실행
```bash
mvn spring-boot:run
```

4. 웹 브라우저에서 접속
```
http://localhost:8080
```

## 웹 테스트 페이지

애플리케이션을 실행하면 `http://localhost:8080`에서 각 유틸리티의 기능을 탭별로 테스트할 수 있는 웹 페이지가 제공됩니다.

- **StringUtils 탭**: 문자열 처리 기능 테스트
- **DateUtils 탭**: 날짜/시간 처리 기능 테스트
- **FileUtils 탭**: 파일 처리 기능 테스트
- **CollectionUtils 탭**: 컬렉션 처리 기능 테스트
- **ResponseUtils 탭**: API 응답 형식 예제

## REST API 사용 예제

### StringUtils API

```bash
# 이메일 마스킹
curl -X POST http://localhost:8080/api/utils/string/maskEmail \
  -H "Content-Type: application/json" \
  -d '{"email":"example@email.com"}'

# 응답
{
  "success": true,
  "message": "Success",
  "data": "exa***@email.com",
  "timestamp": 1234567890
}
```

### DateUtils API

```bash
# 현재 날짜 조회
curl -X GET "http://localhost:8080/api/utils/date/getCurrentDate?pattern=yyyy-MM-dd"

# 두 날짜 사이 일수 계산
curl -X POST http://localhost:8080/api/utils/date/getDaysBetween \
  -H "Content-Type: application/json" \
  -d '{"startDate":"2024-01-01","endDate":"2024-12-31","pattern":"yyyy-MM-dd"}'
```

### FileUtils API

```bash
# 파일 크기 포맷 변환
curl -X POST http://localhost:8080/api/utils/file/formatFileSize \
  -H "Content-Type: application/json" \
  -d '{"size":1048576}'

# 응답
{
  "success": true,
  "message": "Success",
  "data": "1 MB",
  "timestamp": 1234567890
}
```

### CollectionUtils API

```bash
# 리스트 분할
curl -X POST http://localhost:8080/api/utils/collection/partition \
  -H "Content-Type: application/json" \
  -d '{"list":["1","2","3","4","5","6"],"size":2}'

# 응답
{
  "success": true,
  "message": "Success",
  "data": [["1","2"],["3","4"],["5","6"]],
  "timestamp": 1234567890
}
```

## 코드에서 사용하기

### StringUtils 사용 예제

```java
import com.common.utils.string.StringUtils;

public class Example {
    public void example() {
        // 문자열 검증
        boolean isEmpty = StringUtils.isEmpty(""); // true
        boolean isBlank = StringUtils.isBlank("  "); // true

        // 케이스 변환
        String snake = StringUtils.camelToSnake("userName"); // "user_name"
        String camel = StringUtils.snakeToCamel("user_name"); // "userName"

        // 마스킹
        String maskedEmail = StringUtils.maskEmail("example@email.com"); // "exa***@email.com"
        String maskedPhone = StringUtils.maskPhone("010-1234-5678"); // "010-****-5678"

        // 랜덤 생성
        String random = StringUtils.generateRandomAlphanumeric(10);
    }
}
```

### DateUtils 사용 예제

```java
import com.common.utils.date.DateUtils;

public class Example {
    public void example() {
        // 현재 날짜/시간
        String now = DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

        // 날짜 포맷 변환
        String converted = DateUtils.convertDateFormat(
            "2024-01-01", "yyyy-MM-dd", "yyyy년 MM월 dd일"
        );

        // 날짜 차이 계산
        long days = DateUtils.getDaysBetween(
            "2024-01-01", "2024-12-31", "yyyy-MM-dd"
        );

        // 날짜 더하기
        String futureDate = DateUtils.addDays("2024-01-01", 7, "yyyy-MM-dd");
    }
}
```

### ResponseUtils 사용 예제

```java
import com.common.utils.response.*;

@RestController
public class MyController {

    @GetMapping("/users/{id}")
    public ApiResponse<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseUtils.fail(ErrorCode.DATA_NOT_FOUND);
        }
        return ResponseUtils.success(user);
    }

    @GetMapping("/users")
    public ApiResponse<PageResponse<User>> getUsers(
        @RequestParam int page,
        @RequestParam int size
    ) {
        List<User> users = userService.findAll();
        return ResponseUtils.pageSuccess(users, page, size, users.size());
    }
}
```

## 프로젝트 구조

```
utils/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/common/
│   │   │       ├── UtilsApplication.java
│   │   │       ├── controller/
│   │   │       │   └── UtilsController.java
│   │   │       └── utils/
│   │   │           ├── string/
│   │   │           │   └── StringUtils.java
│   │   │           ├── date/
│   │   │           │   └── DateUtils.java
│   │   │           ├── file/
│   │   │           │   └── FileUtils.java
│   │   │           ├── collection/
│   │   │           │   └── CollectionUtils.java
│   │   │           └── response/
│   │   │               ├── ResponseUtils.java
│   │   │               ├── ApiResponse.java
│   │   │               ├── PageResponse.java
│   │   │               └── ErrorCode.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   └── app.js
│   │       └── application.properties
│   └── test/
├── pom.xml
├── .gitignore
└── README.md
```

## 라이선스

MIT License

## 기여

풀 리퀘스트는 언제나 환영합니다!

## 문의

이슈 또는 질문이 있으시면 GitHub Issues를 이용해주세요.
