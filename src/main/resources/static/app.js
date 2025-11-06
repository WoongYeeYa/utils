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
    resultElement.innerHTML = `
        <div class="result-label">결과:</div>
        <div class="result-value">${JSON.stringify(result, null, 2)}</div>
    `;
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

        const response = await fetch(`${API_BASE}${endpoint}`, options);
        const result = await response.json();

        if (!result.success) {
            throw new Error(result.message || 'API 호출 실패');
        }

        return result.data;
    } catch (error) {
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
        showResult('result-string-check', error.message, true);
    }
}

async function convertCase(type) {
    const str = document.getElementById('str-case').value;
    try {
        const result = await callApi(`/string/${type}`, { str });
        showResult('result-case', result);
    } catch (error) {
        showResult('result-case', error.message, true);
    }
}

async function maskEmail() {
    const email = document.getElementById('email-mask').value;
    try {
        const result = await callApi('/string/maskEmail', { email });
        showResult('result-email-mask', result);
    } catch (error) {
        showResult('result-email-mask', error.message, true);
    }
}

async function maskPhone() {
    const phone = document.getElementById('phone-mask').value;
    try {
        const result = await callApi('/string/maskPhone', { phone });
        showResult('result-phone-mask', result);
    } catch (error) {
        showResult('result-phone-mask', error.message, true);
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
        showResult(resultId, error.message, true);
    }
}

// ==================== DateUtils Functions ====================

async function getCurrentDateTime() {
    const pattern = document.getElementById('date-pattern').value;
    try {
        const result = await callApi(`/date/getCurrentDateTime?pattern=${encodeURIComponent(pattern)}`, {}, 'GET');
        showResult('result-current-date', result);
    } catch (error) {
        showResult('result-current-date', error.message, true);
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
        showResult('result-convert-date', error.message, true);
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
        showResult('result-days-between', error.message, true);
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
        showResult('result-add-days', error.message, true);
    }
}

// ==================== FileUtils Functions ====================

async function getExtension() {
    const filename = document.getElementById('filename-ext').value;
    try {
        const result = await callApi('/file/getExtension', { filename });
        showResult('result-extension', result || '(확장자 없음)');
    } catch (error) {
        showResult('result-extension', error.message, true);
    }
}

async function formatFileSize() {
    const size = parseInt(document.getElementById('file-size-bytes').value);
    try {
        const result = await callApi('/file/formatFileSize', { size });
        showResult('result-format-size', result);
    } catch (error) {
        showResult('result-format-size', error.message, true);
    }
}

async function parseFileSize() {
    const sizeStr = document.getElementById('file-size-str').value;
    try {
        const result = await callApi('/file/parseFileSize', { sizeStr });
        showResult('result-parse-size', `${result} bytes`);
    } catch (error) {
        showResult('result-parse-size', error.message, true);
    }
}

async function checkFileType(type) {
    const filename = document.getElementById('filename-check').value;
    try {
        const result = await callApi(`/file/${type}`, { filename });
        showResult('result-file-type', result ? '예' : '아니오');
    } catch (error) {
        showResult('result-file-type', error.message, true);
    }
}

async function sanitizeFilename() {
    const filename = document.getElementById('filename-sanitize').value;
    try {
        const result = await callApi('/file/sanitizeFilename', { filename });
        showResult('result-sanitize', result);
    } catch (error) {
        showResult('result-sanitize', error.message, true);
    }
}

async function generateUniqueFilename() {
    const filename = document.getElementById('filename-unique').value;
    try {
        const result = await callApi('/file/generateUniqueFilename', { filename });
        showResult('result-unique', result);
    } catch (error) {
        showResult('result-unique', error.message, true);
    }
}

async function guessMimeType() {
    const filename = document.getElementById('filename-mime').value;
    try {
        const result = await callApi('/file/guessMimeType', { filename });
        showResult('result-mime', result);
    } catch (error) {
        showResult('result-mime', error.message, true);
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
        showResult('result-partition', error.message, true);
    }
}

async function removeDuplicates() {
    const listStr = document.getElementById('list-duplicates').value;
    const list = parseList(listStr);

    try {
        const result = await callApi('/collection/removeDuplicates', { list });
        showResult('result-duplicates', result);
    } catch (error) {
        showResult('result-duplicates', error.message, true);
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
        showResult('result-set-operation', error.message, true);
    }
}

async function manipulateList(operation) {
    const listStr = document.getElementById('list-manipulate').value;
    const list = parseList(listStr);

    try {
        const result = await callApi(`/collection/${operation}`, { list });
        showResult('result-manipulate', result);
    } catch (error) {
        showResult('result-manipulate', error.message, true);
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
        showResult('result-paginate', error.message, true);
    }
}

// ==================== ValidationUtils Functions ====================

async function validateEmail() {
    const email = document.getElementById('validate-email').value;
    try {
        const result = await callApi('/validation/isValidEmail', { email });
        showResult('result-validate-email', result ? '유효한 이메일입니다' : '유효하지 않은 이메일입니다');
    } catch (error) {
        showResult('result-validate-email', error.message, true);
    }
}

async function validatePhone() {
    const phone = document.getElementById('validate-phone').value;
    try {
        const result = await callApi('/validation/isValidPhone', { phone });
        showResult('result-validate-phone', result ? '유효한 전화번호입니다' : '유효하지 않은 전화번호입니다');
    } catch (error) {
        showResult('result-validate-phone', error.message, true);
    }
}

async function checkPassword() {
    const password = document.getElementById('password-strength').value;
    try {
        const result = await callApi('/validation/checkPasswordStrength', { password });
        showResult('result-password', result);
    } catch (error) {
        showResult('result-password', error.message, true);
    }
}

async function validateUrl() {
    const url = document.getElementById('validate-url').value;
    try {
        const result = await callApi('/validation/isValidUrl', { url });
        showResult('result-validate-url', result ? '유효한 URL입니다' : '유효하지 않은 URL입니다');
    } catch (error) {
        showResult('result-validate-url', error.message, true);
    }
}

async function validateIp() {
    const ip = document.getElementById('validate-ip').value;
    try {
        const result = await callApi('/validation/isValidIpAddress', { ip });
        showResult('result-validate-ip', result ? '유효한 IP 주소입니다' : '유효하지 않은 IP 주소입니다');
    } catch (error) {
        showResult('result-validate-ip', error.message, true);
    }
}

// ==================== JsonUtils Functions ====================

async function formatJsonPretty() {
    const json = document.getElementById('json-input').value;
    try {
        const result = await callApi('/json/formatJson', { json });
        showResult('result-json', result);
    } catch (error) {
        showResult('result-json', error.message, true);
    }
}

async function minifyJsonString() {
    const json = document.getElementById('json-input').value;
    try {
        const result = await callApi('/json/minifyJson', { json });
        showResult('result-json', result);
    } catch (error) {
        showResult('result-json', error.message, true);
    }
}

async function validateJson() {
    const json = document.getElementById('json-input').value;
    try {
        const result = await callApi('/json/isValidJson', { json });
        showResult('result-json', result ? '유효한 JSON입니다' : '유효하지 않은 JSON입니다');
    } catch (error) {
        showResult('result-json', error.message, true);
    }
}

// ==================== NumberUtils Functions ====================

async function formatNumber() {
    const number = parseInt(document.getElementById('number-format').value);
    try {
        const result = await callApi('/number/formatWithComma', { number });
        showResult('result-number-format', result);
    } catch (error) {
        showResult('result-number-format', error.message, true);
    }
}

async function calculatePercent() {
    const value = parseFloat(document.getElementById('percent-value').value);
    const total = parseFloat(document.getElementById('percent-total').value);
    try {
        const result = await callApi('/number/calculatePercentage', { value, total });
        showResult('result-percent', result);
    } catch (error) {
        showResult('result-percent', error.message, true);
    }
}

async function checkPrime() {
    const number = parseInt(document.getElementById('prime-number').value);
    try {
        const result = await callApi('/number/isPrime', { number });
        showResult('result-prime', result ? `${number}은(는) 소수입니다` : `${number}은(는) 소수가 아닙니다`);
    } catch (error) {
        showResult('result-prime', error.message, true);
    }
}

// ==================== HttpUtils Functions ====================

async function getClientInfo() {
    try {
        const result = await callApi('/http/getClientIp', {}, 'GET');
        showResult('result-http-info', `IP 주소: ${result}`);
    } catch (error) {
        showResult('result-http-info', error.message, true);
    }
}

async function getBrowser() {
    try {
        const result = await callApi('/http/getBrowserType', {}, 'GET');
        showResult('result-http-info', `브라우저: ${result}`);
    } catch (error) {
        showResult('result-http-info', error.message, true);
    }
}

async function checkMobile() {
    try {
        const result = await callApi('/http/isMobileDevice', {}, 'GET');
        showResult('result-http-info', result ? '모바일 디바이스입니다' : 'PC입니다');
    } catch (error) {
        showResult('result-http-info', error.message, true);
    }
}

async function parseQuery() {
    const queryString = document.getElementById('query-string').value;
    try {
        const result = await callApi('/http/parseQueryString', { queryString });
        showResult('result-query', result);
    } catch (error) {
        showResult('result-query', error.message, true);
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
        showResult('result-id', error.message, true);
    }
}

async function generateRandomIdCustom() {
    const length = parseInt(document.getElementById('random-id-length').value);
    try {
        const result = await callApi('/id/generateRandomId', { length });
        showResult('result-random-id', result);
    } catch (error) {
        showResult('result-random-id', error.message, true);
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
        showResult('result-regex', error.message, true);
    }
}

async function regexFindAll() {
    const text = document.getElementById('regex-text').value;
    const regex = document.getElementById('regex-pattern').value;
    try {
        const result = await callApi('/regex/findAll', { text, regex });
        showResult('result-regex', result);
    } catch (error) {
        showResult('result-regex', error.message, true);
    }
}

async function extractEmails() {
    const text = document.getElementById('extract-email-text').value;
    try {
        const result = await callApi('/regex/extractEmails', { text });
        showResult('result-extract-email', result);
    } catch (error) {
        showResult('result-extract-email', error.message, true);
    }
}

async function removeHtml() {
    const text = document.getElementById('html-text').value;
    try {
        const result = await callApi('/regex/removeHtmlTags', { text });
        showResult('result-remove-html', result);
    } catch (error) {
        showResult('result-remove-html', error.message, true);
    }
}

// ==================== MoneyUtils Functions ====================

async function formatKoreanWon() {
    const amount = parseInt(document.getElementById('krw-amount').value);
    try {
        const result = await callApi('/money/formatKRW', { amount });
        showResult('result-krw', result);
    } catch (error) {
        showResult('result-krw', error.message, true);
    }
}

async function calcDiscount() {
    const originalPrice = parseInt(document.getElementById('original-price').value);
    const discountPercent = parseInt(document.getElementById('discount-rate').value);
    try {
        const result = await callApi('/money/calculateDiscount', { originalPrice, discountPercent });
        showResult('result-discount', `할인가: ${result}원`);
    } catch (error) {
        showResult('result-discount', error.message, true);
    }
}

async function calcVat() {
    const amount = parseInt(document.getElementById('vat-amount').value);
    try {
        const result = await callApi('/money/addVAT', { amount });
        showResult('result-vat', `부가세 포함: ${result}원`);
    } catch (error) {
        showResult('result-vat', error.message, true);
    }
}

async function splitMoney() {
    const amount = parseInt(document.getElementById('split-amount').value);
    const numberOfPeople = parseInt(document.getElementById('split-people').value);
    try {
        const result = await callApi('/money/splitAmount', { amount, numberOfPeople });
        showResult('result-split', `1인당: ${result}원`);
    } catch (error) {
        showResult('result-split', error.message, true);
    }
}

// ==================== ObjectUtils Functions ====================

async function testObject() {
    try {
        const result = await callApi('/object/test', {}, 'GET');
        showResult('result-object-test', result);
    } catch (error) {
        showResult('result-object-test', error.message, true);
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
