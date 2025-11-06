package com.common.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelUtils - Excel 파일 처리 유틸리티
 */
public class ExcelUtils {

    private ExcelUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Excel 파일을 List<Map>으로 읽기
     * 첫 번째 행을 헤더로 사용
     */
    public static List<Map<String, String>> readExcelToList(String filePath) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() == 0) {
                return result;
            }

            // 헤더 읽기
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            // 데이터 읽기
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = cell != null ? getCellValueAsString(cell) : "";
                    rowData.put(headers.get(j), value);
                }
                result.add(rowData);
            }
        }

        return result;
    }

    /**
     * List<Map>을 Excel 파일로 쓰기
     */
    public static void writeListToExcel(List<Map<String, String>> data, String filePath) throws IOException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be empty");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // 헤더 추출
            Map<String, String> firstRow = data.get(0);
            List<String> headers = new ArrayList<>(firstRow.keySet());

            // 헤더 스타일
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 헤더 작성
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
            }

            // 데이터 작성
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Map<String, String> rowData = data.get(i);

                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.createCell(j);
                    String value = rowData.get(headers.get(j));
                    cell.setCellValue(value != null ? value : "");
                }
            }

            // 열 너비 자동 조정
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // 파일 저장
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    /**
     * Excel 파일을 CSV 문자열로 변환
     */
    public static String excelToCsv(String excelFilePath) throws IOException {
        StringBuilder csvContent = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> cellValues = new ArrayList<>();
                for (Cell cell : row) {
                    cellValues.add(getCellValueAsString(cell));
                }
                csvContent.append(String.join(",", cellValues)).append("\n");
            }
        }

        return csvContent.toString();
    }

    /**
     * CSV 문자열을 Excel 파일로 변환
     */
    public static void csvToExcel(String csvContent, String excelFilePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            String[] lines = csvContent.split("\n");
            for (int i = 0; i < lines.length; i++) {
                Row row = sheet.createRow(i);
                String[] values = lines[i].split(",");

                for (int j = 0; j < values.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(values[j].trim());
                }
            }

            // 열 너비 자동 조정
            if (lines.length > 0) {
                String[] firstLine = lines[0].split(",");
                for (int i = 0; i < firstLine.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                workbook.write(fos);
            }
        }
    }

    /**
     * Excel 파일의 시트 개수 반환
     */
    public static int getSheetCount(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            return workbook.getNumberOfSheets();
        }
    }

    /**
     * Excel 파일의 특정 시트 이름 반환
     */
    public static String getSheetName(String filePath, int sheetIndex) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            return workbook.getSheetName(sheetIndex);
        }
    }

    /**
     * Excel 파일의 모든 시트 이름 반환
     */
    public static List<String> getAllSheetNames(String filePath) throws IOException {
        List<String> sheetNames = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
        }

        return sheetNames;
    }

    /**
     * 특정 시트를 List<Map>으로 읽기
     */
    public static List<Map<String, String>> readSheetToList(String filePath, int sheetIndex) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            if (sheet.getPhysicalNumberOfRows() == 0) {
                return result;
            }

            // 헤더 읽기
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            // 데이터 읽기
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = cell != null ? getCellValueAsString(cell) : "";
                    rowData.put(headers.get(j), value);
                }
                result.add(rowData);
            }
        }

        return result;
    }

    /**
     * 특정 시트의 행 개수 반환
     */
    public static int getRowCount(String filePath, int sheetIndex) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            return sheet.getLastRowNum() + 1;
        }
    }

    /**
     * 특정 시트의 열 개수 반환
     */
    public static int getColumnCount(String filePath, int sheetIndex) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            if (sheet.getPhysicalNumberOfRows() == 0) {
                return 0;
            }

            Row firstRow = sheet.getRow(0);
            return firstRow != null ? firstRow.getLastCellNum() : 0;
        }
    }

    /**
     * Cell 값을 문자열로 변환
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                } else {
                    // 정수인 경우 소수점 제거
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        yield String.valueOf((long) numericValue);
                    } else {
                        yield String.valueOf(numericValue);
                    }
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    /**
     * 빈 Excel 파일 생성
     */
    public static void createEmptyExcel(String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            workbook.createSheet("Sheet1");

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    /**
     * Excel 파일이 유효한지 확인
     */
    public static boolean isValidExcelFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
