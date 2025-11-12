// API Base URL
const API_BASE = '/api/utils';

// Tab 전환 함수
function openTab(evt, tabName) {
    const tabContents = document.getElementsByClassName("tab-content");
    for (let i = 0; i < tabContents.length; i++) {
        tabContents[i].classList.remove("active");
    }

    const tabs = document.getElementsByClassName("tab");
    for (let i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove("active");
    }

    document.getElementById(tabName).classList.add("active");
    evt.currentTarget.classList.add("active");
}

// 결과 표시 함수
function showResult(elementId, result, isError = false) {
    const resultElement = document.getElementById(elementId);

    if (isError && typeof result === 'object' && result !== null) {
        // 에러 객체인 경우 상세 정보 표시
        let errorHtml = '<div class="result-label">오류 발생:</div>';

        if (result.status !== undefined) {
            errorHtml += `<div class="error-detail">
                <strong>HTTP 상태:</strong> ${result.status} ${result.statusText || ''}
            </div>`;
        }

        if (result.message) {
            errorHtml += `<div class="error-detail">
                <strong>오류 메시지:</strong> ${result.message}
            </div>`;
        }

        if (result.errorCode) {
            errorHtml += `<div class="error-detail">
                <strong>오류 코드:</strong> ${result.errorCode}
            </div>`;
        }

        if (result.url) {
            errorHtml += `<div class="error-detail">
                <strong>요청 URL:</strong> ${result.url}
            </div>`;
        }

        if (result.method) {
            errorHtml += `<div class="error-detail">
                <strong>HTTP 메소드:</strong> ${result.method}
            </div>`;
        }

        if (result.timestamp) {
            const date = new Date(result.timestamp);
            errorHtml += `<div class="error-detail">
                <strong>발생 시간:</strong> ${date.toLocaleString('ko-KR')}
            </div>`;
        }

        if (result.originalError) {
            errorHtml += `<div class="error-detail">
                <strong>원본 오류:</strong> ${result.originalError}
            </div>`;
        }

        // 전체 에러 객체를 JSON으로 표시 (개발자용)
        errorHtml += `<details style="margin-top: 10px;">
            <summary style="cursor: pointer; color: #667eea; font-weight: 600;">
                상세 디버그 정보 보기
            </summary>
            <pre style="margin-top: 10px; padding: 10px; background: #f1f3f5; border-radius: 5px; font-size: 12px; overflow-x: auto;">${JSON.stringify(result, null, 2)}</pre>
        </details>`;

        resultElement.innerHTML = errorHtml;
    } else {
        // 일반 결과 표시
        let displayValue;
        if (typeof result === 'string') {
            displayValue = result;
        } else {
            displayValue = `<pre style="margin: 0; white-space: pre-wrap; word-break: break-word;">${JSON.stringify(result, null, 2)}</pre>`;
        }

        resultElement.innerHTML = `
            <div class="result-label">결과:</div>
            <div class="result-value">${displayValue}</div>
        `;
    }

    resultElement.classList.remove('error');
    if (isError) {
        resultElement.classList.add('error');
    }
    resultElement.classList.add('show');
}

// API 호출 함수
async function callApi(endpoint, data = {}, method = 'POST') {
    try {
        const options = {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        if (method === 'POST') {
            options.body = JSON.stringify(data);
        }

        const url = `${API_BASE}${endpoint}`;
        const response = await fetch(url, options);

        // HTTP 상태 코드 확인
        if (!response.ok) {
            let errorDetail = {
                status: response.status,
                statusText: response.statusText,
                url: url,
                method: method
            };

            // 응답 본문 파싱 시도
            try {
                const errorBody = await response.json();
                errorDetail.message = errorBody.message || '서버 오류가 발생했습니다';
                errorDetail.errorCode = errorBody.errorCode;
                errorDetail.timestamp = errorBody.timestamp;
            } catch (e) {
                // JSON 파싱 실패 시 텍스트로 읽기 시도
                try {
                    errorDetail.message = await response.text() || response.statusText;
                } catch (textError) {
                    errorDetail.message = response.statusText;
                }
            }

            throw errorDetail;
        }

        const result = await response.json();

        if (!result.success) {
            const errorDetail = {
                status: response.status,
                statusText: 'Business Logic Error',
                url: url,
                method: method,
                message: result.message || 'API 호출 실패',
                errorCode: result.errorCode,
                timestamp: result.timestamp
            };
            throw errorDetail;
        }

        return result.data;
    } catch (error) {
        // 네트워크 오류나 기타 예외 처리
        if (!error.status) {
            throw {
                status: 0,
                statusText: 'Network Error',
                url: `${API_BASE}${endpoint}`,
                method: method,
                message: error.message || '네트워크 오류가 발생했습니다. 서버 연결을 확인해주세요.',
                originalError: error.toString()
            };
        }
        throw error;
    }
}

// ==================== StringUtils Functions ====================

async function checkString(type) {
    const str = document.getElementById('str-check').value;
    try {
        const result = await callApi(`/string/${type}`, { str });
        showResult('result-string-check', result);
    } catch (error) {
        showResult('result-string-check', error, true);
    }
}

async function convertCase(type) {
    const str = document.getElementById('str-case').value;
    try {
        const result = await callApi(`/string/${type}`, { str });
        showResult('result-case', result);
    } catch (error) {
        showResult('result-case', error, true);
    }
}

async function maskEmail() {
    const email = document.getElementById('email-mask').value;
    try {
        const result = await callApi('/string/maskEmail', { email });
        showResult('result-email-mask', result);
    } catch (error) {
        showResult('result-email-mask', error, true);
    }
}

async function maskPhone() {
    const phone = document.getElementById('phone-mask').value;
    try {
        const result = await callApi('/string/maskPhone', { phone });
        showResult('result-phone-mask', result);
    } catch (error) {
        showResult('result-phone-mask', error, true);
    }
}

async function generateRandom(type) {
    const lengthId = type === 'alphanumeric' ? 'random-alpha-length' : 'random-num-length';
    const resultId = type === 'alphanumeric' ? 'result-random-alpha' : 'result-random-num';
    const endpoint = type === 'alphanumeric' ? '/string/generateRandomAlphanumeric' : '/string/generateRandomNumeric';

    const length = parseInt(document.getElementById(lengthId).value);
    try {
        const result = await callApi(endpoint, { length });
        showResult(resultId, result);
    } catch (error) {
        showResult(resultId, error, true);
    }
}

// ==================== DateUtils Functions ====================

async function getCurrentDateTime() {
    const pattern = document.getElementById('date-pattern').value;
    try {
        const result = await callApi(`/date/getCurrentDateTime?pattern=${encodeURIComponent(pattern)}`, {}, 'GET');
        showResult('result-current-date', result);
    } catch (error) {
        showResult('result-current-date', error, true);
    }
}

async function convertDateFormat() {
    const dateStr = document.getElementById('date-str').value;
    const fromPattern = document.getElementById('from-pattern').value;
    const toPattern = document.getElementById('to-pattern').value;

    try {
        const result = await callApi('/date/convertFormat', { dateStr, fromPattern, toPattern });
        showResult('result-convert-date', result);
    } catch (error) {
        showResult('result-convert-date', error, true);
    }
}

async function getDaysBetween() {
    const startDate = document.getElementById('start-date').value;
    const endDate = document.getElementById('end-date').value;

    if (!startDate || !endDate) {
        showResult('result-days-between', '시작 날짜와 종료 날짜를 모두 입력해주세요.', true);
        return;
    }

    try {
        const result = await callApi('/date/getDaysBetween', {
            startDate,
            endDate,
            pattern: 'yyyy-MM-dd'
        });
        showResult('result-days-between', `${result}일`);
    } catch (error) {
        showResult('result-days-between', error, true);
    }
}

async function addDays() {
    const dateStr = document.getElementById('base-date').value;
    const days = parseInt(document.getElementById('add-days-value').value);

    if (!dateStr) {
        showResult('result-add-days', '기준 날짜를 입력해주세요.', true);
        return;
    }

    try {
        const result = await callApi('/date/addDays', {
            dateStr,
            days,
            pattern: 'yyyy-MM-dd'
        });
        showResult('result-add-days', result);
    } catch (error) {
        showResult('result-add-days', error, true);
    }
}

// ==================== FileUtils Functions ====================

async function getExtension() {
    const filename = document.getElementById('filename-ext').value;
    try {
        const result = await callApi('/file/getExtension', { filename });
        showResult('result-extension', result || '(확장자 없음)');
    } catch (error) {
        showResult('result-extension', error, true);
    }
}

async function formatFileSize() {
    const size = parseInt(document.getElementById('file-size-bytes').value);
    try {
        const result = await callApi('/file/formatFileSize', { size });
        showResult('result-format-size', result);
    } catch (error) {
        showResult('result-format-size', error, true);
    }
}

async function parseFileSize() {
    const sizeStr = document.getElementById('file-size-str').value;
    try {
        const result = await callApi('/file/parseFileSize', { sizeStr });
        showResult('result-parse-size', `${result} bytes`);
    } catch (error) {
        showResult('result-parse-size', error, true);
    }
}

async function checkFileType(type) {
    const filename = document.getElementById('filename-check').value;
    try {
        const result = await callApi(`/file/${type}`, { filename });
        showResult('result-file-type', result ? '예' : '아니오');
    } catch (error) {
        showResult('result-file-type', error, true);
    }
}

async function sanitizeFilename() {
    const filename = document.getElementById('filename-sanitize').value;
    try {
        const result = await callApi('/file/sanitizeFilename', { filename });
        showResult('result-sanitize', result);
    } catch (error) {
        showResult('result-sanitize', error, true);
    }
}

async function generateUniqueFilename() {
    const filename = document.getElementById('filename-unique').value;
    try {
        const result = await callApi('/file/generateUniqueFilename', { filename });
        showResult('result-unique', result);
    } catch (error) {
        showResult('result-unique', error, true);
    }
}

async function guessMimeType() {
    const filename = document.getElementById('filename-mime').value;
    try {
        const result = await callApi('/file/guessMimeType', { filename });
        showResult('result-mime', result);
    } catch (error) {
        showResult('result-mime', error, true);
    }
}

// ==================== CollectionUtils Functions ====================

function parseList(str) {
    return str.split(',').map(item => item.trim()).filter(item => item !== '');
}

async function partitionList() {
    const listStr = document.getElementById('list-partition').value;
    const size = parseInt(document.getElementById('partition-size').value);
    const list = parseList(listStr);

    try {
        const result = await callApi('/collection/partition', { list, size });
        showResult('result-partition', result);
    } catch (error) {
        showResult('result-partition', error, true);
    }
}

async function removeDuplicates() {
    const listStr = document.getElementById('list-duplicates').value;
    const list = parseList(listStr);

    try {
        const result = await callApi('/collection/removeDuplicates', { list });
        showResult('result-duplicates', result);
    } catch (error) {
        showResult('result-duplicates', error, true);
    }
}

async function setOperation(operation) {
    const list1Str = document.getElementById('collection1').value;
    const list2Str = document.getElementById('collection2').value;
    const collection1 = parseList(list1Str);
    const collection2 = parseList(list2Str);

    try {
        const result = await callApi(`/collection/${operation}`, { collection1, collection2 });
        showResult('result-set-operation', result);
    } catch (error) {
        showResult('result-set-operation', error, true);
    }
}

async function manipulateList(operation) {
    const listStr = document.getElementById('list-manipulate').value;
    const list = parseList(listStr);

    try {
        const result = await callApi(`/collection/${operation}`, { list });
        showResult('result-manipulate', result);
    } catch (error) {
        showResult('result-manipulate', error, true);
    }
}

async function paginateList() {
    const listStr = document.getElementById('list-paginate').value;
    const page = parseInt(document.getElementById('page-number').value);
    const pageSize = parseInt(document.getElementById('page-size').value);
    const list = parseList(listStr);

    try {
        const result = await callApi('/collection/paginate', { list, page, pageSize });
        showResult('result-paginate', result);
    } catch (error) {
        showResult('result-paginate', error, true);
    }
}

// ==================== ValidationUtils Functions ====================

async function validateEmail() {
    const email = document.getElementById('validate-email').value;
    try {
        const result = await callApi('/validation/isValidEmail', { email });
        showResult('result-validate-email', result ? '유효한 이메일입니다' : '유효하지 않은 이메일입니다');
    } catch (error) {
        showResult('result-validate-email', error, true);
    }
}

async function validatePhone() {
    const phone = document.getElementById('validate-phone').value;
    try {
        const result = await callApi('/validation/isValidPhone', { phone });
        showResult('result-validate-phone', result ? '유효한 전화번호입니다' : '유효하지 않은 전화번호입니다');
    } catch (error) {
        showResult('result-validate-phone', error, true);
    }
}

async function checkPassword() {
    const password = document.getElementById('password-strength').value;
    try {
        const result = await callApi('/validation/checkPasswordStrength', { password });
        showResult('result-password', result);
    } catch (error) {
        showResult('result-password', error, true);
    }
}

async function validateUrl() {
    const url = document.getElementById('validate-url').value;
    try {
        const result = await callApi('/validation/isValidUrl', { url });
        showResult('result-validate-url', result ? '유효한 URL입니다' : '유효하지 않은 URL입니다');
    } catch (error) {
        showResult('result-validate-url', error, true);
    }
}

async function validateIp() {
    const ip = document.getElementById('validate-ip').value;
    try {
        const result = await callApi('/validation/isValidIpAddress', { ip });
        showResult('result-validate-ip', result ? '유효한 IP 주소입니다' : '유효하지 않은 IP 주소입니다');
    } catch (error) {
        showResult('result-validate-ip', error, true);
    }
}

// ==================== JsonUtils Functions ====================

async function formatJsonPretty() {
    const json = document.getElementById('json-input').value;
    try {
        const result = await callApi('/json/formatJson', { json });
        showResult('result-json', result);
    } catch (error) {
        showResult('result-json', error, true);
    }
}

async function minifyJsonString() {
    const json = document.getElementById('json-input').value;
    try {
        const result = await callApi('/json/minifyJson', { json });
        showResult('result-json', result);
    } catch (error) {
        showResult('result-json', error, true);
    }
}

async function validateJson() {
    const json = document.getElementById('json-input').value;
    try {
        const result = await callApi('/json/isValidJson', { json });
        showResult('result-json', result ? '유효한 JSON입니다' : '유효하지 않은 JSON입니다');
    } catch (error) {
        showResult('result-json', error, true);
    }
}

// ==================== NumberUtils Functions ====================

async function formatNumber() {
    const number = parseInt(document.getElementById('number-format').value);
    try {
        const result = await callApi('/number/formatWithComma', { number });
        showResult('result-number-format', result);
    } catch (error) {
        showResult('result-number-format', error, true);
    }
}

async function calculatePercent() {
    const value = parseFloat(document.getElementById('percent-value').value);
    const total = parseFloat(document.getElementById('percent-total').value);
    try {
        const result = await callApi('/number/calculatePercentage', { value, total });
        showResult('result-percent', result);
    } catch (error) {
        showResult('result-percent', error, true);
    }
}

async function checkPrime() {
    const number = parseInt(document.getElementById('prime-number').value);
    try {
        const result = await callApi('/number/isPrime', { number });
        showResult('result-prime', result ? `${number}은(는) 소수입니다` : `${number}은(는) 소수가 아닙니다`);
    } catch (error) {
        showResult('result-prime', error, true);
    }
}

async function roundNumber() {
    const value = parseFloat(document.getElementById('round-value').value);
    const scale = parseInt(document.getElementById('round-scale').value);
    try {
        const result = await callApi('/number/round', { value, scale });
        showResult('result-round', `반올림 결과: ${result}`);
    } catch (error) {
        showResult('result-round', error, true);
    }
}

async function ceilNumber() {
    const value = parseFloat(document.getElementById('round-value').value);
    const scale = parseInt(document.getElementById('round-scale').value);
    try {
        const result = await callApi('/number/ceil', { value, scale });
        showResult('result-round', `올림 결과: ${result}`);
    } catch (error) {
        showResult('result-round', error, true);
    }
}

async function floorNumber() {
    const value = parseFloat(document.getElementById('round-value').value);
    const scale = parseInt(document.getElementById('round-scale').value);
    try {
        const result = await callApi('/number/floor', { value, scale });
        showResult('result-round', `내림 결과: ${result}`);
    } catch (error) {
        showResult('result-round', error, true);
    }
}

async function generateRandomInt() {
    const min = parseInt(document.getElementById('random-int-min').value);
    const max = parseInt(document.getElementById('random-int-max').value);
    try {
        const result = await callApi('/number/randomInt', { min, max });
        showResult('result-random-int', `랜덤 정수: ${result}`);
    } catch (error) {
        showResult('result-random-int', error, true);
    }
}

async function generateRandomDouble() {
    const min = parseFloat(document.getElementById('random-double-min').value);
    const max = parseFloat(document.getElementById('random-double-max').value);
    try {
        const result = await callApi('/number/randomDouble', { min, max });
        showResult('result-random-double', `랜덤 실수: ${result}`);
    } catch (error) {
        showResult('result-random-double', error, true);
    }
}

async function calculateAverage() {
    const numbersStr = document.getElementById('average-numbers').value;
    const numbers = numbersStr.split(',').map(n => parseFloat(n.trim())).filter(n => !isNaN(n));

    if (numbers.length === 0) {
        showResult('result-average', '유효한 숫자를 입력해주세요.', true);
        return;
    }

    try {
        const result = await callApi('/number/average', { numbers });
        showResult('result-average', `평균: ${result}\n입력된 숫자: ${numbers.join(', ')}\n개수: ${numbers.length}개`);
    } catch (error) {
        showResult('result-average', error, true);
    }
}

async function calculateGCD() {
    const a = parseInt(document.getElementById('gcd-num1').value);
    const b = parseInt(document.getElementById('gcd-num2').value);
    try {
        const result = await callApi('/number/gcd', { a, b });
        showResult('result-gcd-lcm', `최대공약수(GCD): ${result}`);
    } catch (error) {
        showResult('result-gcd-lcm', error, true);
    }
}

async function calculateLCM() {
    const a = parseInt(document.getElementById('gcd-num1').value);
    const b = parseInt(document.getElementById('gcd-num2').value);
    try {
        const result = await callApi('/number/lcm', { a, b });
        showResult('result-gcd-lcm', `최소공배수(LCM): ${result}`);
    } catch (error) {
        showResult('result-gcd-lcm', error, true);
    }
}

async function checkNumberProperty(type) {
    const number = parseInt(document.getElementById('check-number').value);
    try {
        let result, message;

        if (type === 'prime') {
            result = await callApi('/number/isPrime', { number });
            message = result ? `${number}은(는) 소수입니다` : `${number}은(는) 소수가 아닙니다`;
        } else if (type === 'even') {
            result = await callApi('/number/isEven', { number });
            message = result ? `${number}은(는) 짝수입니다` : `${number}은(는) 홀수입니다`;
        } else if (type === 'odd') {
            result = await callApi('/number/isOdd', { number });
            message = result ? `${number}은(는) 홀수입니다` : `${number}은(는) 짝수입니다`;
        }

        showResult('result-number-property', message);
    } catch (error) {
        showResult('result-number-property', error, true);
    }
}

async function convertToRoman() {
    const number = parseInt(document.getElementById('roman-number').value);

    if (number < 1 || number > 3999) {
        showResult('result-roman', '1부터 3999 사이의 숫자를 입력해주세요.', true);
        return;
    }

    try {
        const result = await callApi('/number/toRoman', { number });
        showResult('result-roman', `${number} = ${result}`);
    } catch (error) {
        showResult('result-roman', error, true);
    }
}

async function formatBytes() {
    const bytes = parseInt(document.getElementById('bytes-number').value);
    try {
        const result = await callApi('/number/formatBytes', { bytes });
        showResult('result-bytes', `${bytes} bytes = ${result}`);
    } catch (error) {
        showResult('result-bytes', error, true);
    }
}

// ==================== HttpUtils Functions ====================

async function getClientInfo() {
    try {
        const result = await callApi('/http/getClientIp', {}, 'GET');
        showResult('result-http-info', `IP 주소: ${result}`);
    } catch (error) {
        showResult('result-http-info', error, true);
    }
}

async function getBrowser() {
    try {
        const result = await callApi('/http/getBrowserType', {}, 'GET');
        showResult('result-http-info', `브라우저: ${result}`);
    } catch (error) {
        showResult('result-http-info', error, true);
    }
}

async function checkMobile() {
    try {
        const result = await callApi('/http/isMobileDevice', {}, 'GET');
        showResult('result-http-info', result ? '모바일 디바이스입니다' : 'PC입니다');
    } catch (error) {
        showResult('result-http-info', error, true);
    }
}

async function parseQuery() {
    const queryString = document.getElementById('query-string').value;
    try {
        const result = await callApi('/http/parseQueryString', { queryString });
        showResult('result-query', result);
    } catch (error) {
        showResult('result-query', error, true);
    }
}

// ==================== IdUtils Functions ====================

async function generateId(type) {
    try {
        const endpoints = {
            'uuid': '/id/generateUuid',
            'shortUuid': '/id/generateShortUuid',
            'snowflakeId': '/id/generateSnowflakeId',
            'nanoId': '/id/generateNanoId',
            'ulid': '/id/generateUlid'
        };
        const result = await callApi(endpoints[type], {}, 'GET');
        showResult('result-id', result);
    } catch (error) {
        showResult('result-id', error, true);
    }
}

async function generateRandomIdCustom() {
    const length = parseInt(document.getElementById('random-id-length').value);
    try {
        const result = await callApi('/id/generateRandomId', { length });
        showResult('result-random-id', result);
    } catch (error) {
        showResult('result-random-id', error, true);
    }
}

// ==================== RegexUtils Functions ====================

async function regexMatch() {
    const text = document.getElementById('regex-text').value;
    const regex = document.getElementById('regex-pattern').value;
    try {
        const result = await callApi('/regex/matches', { text, regex });
        showResult('result-regex', result ? '매칭됩니다' : '매칭되지 않습니다');
    } catch (error) {
        showResult('result-regex', error, true);
    }
}

async function regexFindAll() {
    const text = document.getElementById('regex-text').value;
    const regex = document.getElementById('regex-pattern').value;
    try {
        const result = await callApi('/regex/findAll', { text, regex });
        showResult('result-regex', result);
    } catch (error) {
        showResult('result-regex', error, true);
    }
}

async function extractEmails() {
    const text = document.getElementById('extract-email-text').value;
    try {
        const result = await callApi('/regex/extractEmails', { text });
        showResult('result-extract-email', result);
    } catch (error) {
        showResult('result-extract-email', error, true);
    }
}

async function removeHtml() {
    const text = document.getElementById('html-text').value;
    try {
        const result = await callApi('/regex/removeHtmlTags', { text });
        showResult('result-remove-html', result);
    } catch (error) {
        showResult('result-remove-html', error, true);
    }
}

// ==================== MoneyUtils Functions ====================

async function formatKoreanWon() {
    const amount = parseInt(document.getElementById('krw-amount').value);
    try {
        const result = await callApi('/money/formatKRW', { amount });
        showResult('result-krw', result);
    } catch (error) {
        showResult('result-krw', error, true);
    }
}

async function calcDiscount() {
    const originalPrice = parseInt(document.getElementById('original-price').value);
    const discountPercent = parseInt(document.getElementById('discount-rate').value);
    try {
        const result = await callApi('/money/calculateDiscount', { originalPrice, discountPercent });
        showResult('result-discount', `할인가: ${result}원`);
    } catch (error) {
        showResult('result-discount', error, true);
    }
}

async function calcVat() {
    const amount = parseInt(document.getElementById('vat-amount').value);
    try {
        const result = await callApi('/money/addVAT', { amount });
        showResult('result-vat', `부가세 포함: ${result}원`);
    } catch (error) {
        showResult('result-vat', error, true);
    }
}

async function splitMoney() {
    const amount = parseInt(document.getElementById('split-amount').value);
    const numberOfPeople = parseInt(document.getElementById('split-people').value);
    try {
        const result = await callApi('/money/splitAmount', { amount, numberOfPeople });
        showResult('result-split', `1인당: ${result}원`);
    } catch (error) {
        showResult('result-split', error, true);
    }
}

// ==================== ObjectUtils Functions ====================

async function testObject() {
    try {
        const result = await callApi('/object/test', {}, 'GET');
        showResult('result-object-test', result);
    } catch (error) {
        showResult('result-object-test', error, true);
    }
}

// ==================== ResponseUtils Examples ====================

function showSuccessExample() {
    const example = {
        success: true,
        message: "데이터 조회 성공",
        data: {
            id: 1,
            name: "홍길동",
            email: "hong@example.com"
        },
        errorCode: null,
        timestamp: Date.now()
    };
    showResult('result-success', example);
}

function showFailExample() {
    const example = {
        success: false,
        message: "데이터를 찾을 수 없습니다",
        data: null,
        errorCode: "3001",
        timestamp: Date.now()
    };
    showResult('result-fail', example);
}

function showPageExample() {
    const example = {
        success: true,
        message: "Success",
        data: {
            content: [
                { id: 1, name: "Item 1" },
                { id: 2, name: "Item 2" },
                { id: 3, name: "Item 3" }
            ],
            currentPage: 1,
            pageSize: 10,
            totalElements: 100,
            totalPages: 10,
            first: true,
            last: false,
            empty: false
        },
        errorCode: null,
        timestamp: Date.now()
    };
    showResult('result-page', example);
}
