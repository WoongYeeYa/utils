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

## 시작하기

### 필수 요구사항

- JDK 17 이상
- Maven 3.6 이상

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
