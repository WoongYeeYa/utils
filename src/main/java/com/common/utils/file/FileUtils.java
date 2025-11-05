package com.common.utils.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 파일 처리 유틸리티 클래스
 */
public class FileUtils {

    // 이미지 파일 확장자
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp", "webp", "svg"
    );

    // 문서 파일 확장자
    private static final List<String> DOCUMENT_EXTENSIONS = Arrays.asList(
        "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "hwp"
    );

    // 비디오 파일 확장자
    private static final List<String> VIDEO_EXTENSIONS = Arrays.asList(
        "mp4", "avi", "mov", "wmv", "flv", "mkv"
    );

    /**
     * 파일 확장자 추출
     */
    public static String getExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    /**
     * 파일명에서 확장자 제거
     */
    public static String removeExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return filename;
        }
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    /**
     * 파일 크기를 읽기 쉬운 형식으로 변환
     * 예: 1024 -> 1 KB, 1048576 -> 1 MB
     */
    public static String formatFileSize(long size) {
        if (size <= 0) return "0 B";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 읽기 쉬운 파일 크기를 바이트로 변환
     * 예: "1 MB" -> 1048576
     */
    public static long parseFileSize(String sizeStr) {
        sizeStr = sizeStr.trim().toUpperCase();

        if (sizeStr.endsWith("B") && !sizeStr.endsWith("KB") && !sizeStr.endsWith("MB")
            && !sizeStr.endsWith("GB") && !sizeStr.endsWith("TB")) {
            return Long.parseLong(sizeStr.substring(0, sizeStr.length() - 1).trim().replace(",", ""));
        } else if (sizeStr.endsWith("KB")) {
            return (long) (Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2).trim().replace(",", "")) * 1024);
        } else if (sizeStr.endsWith("MB")) {
            return (long) (Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2).trim().replace(",", "")) * 1024 * 1024);
        } else if (sizeStr.endsWith("GB")) {
            return (long) (Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2).trim().replace(",", "")) * 1024 * 1024 * 1024);
        } else if (sizeStr.endsWith("TB")) {
            return (long) (Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2).trim().replace(",", "")) * 1024L * 1024 * 1024 * 1024);
        }

        return Long.parseLong(sizeStr.replace(",", ""));
    }

    /**
     * 이미지 파일 여부 확인
     */
    public static boolean isImage(String filename) {
        String extension = getExtension(filename);
        return IMAGE_EXTENSIONS.contains(extension);
    }

    /**
     * 문서 파일 여부 확인
     */
    public static boolean isDocument(String filename) {
        String extension = getExtension(filename);
        return DOCUMENT_EXTENSIONS.contains(extension);
    }

    /**
     * 비디오 파일 여부 확인
     */
    public static boolean isVideo(String filename) {
        String extension = getExtension(filename);
        return VIDEO_EXTENSIONS.contains(extension);
    }

    /**
     * 허용된 확장자인지 확인
     */
    public static boolean isAllowedExtension(String filename, List<String> allowedExtensions) {
        String extension = getExtension(filename);
        return allowedExtensions.stream()
            .map(String::toLowerCase)
            .anyMatch(ext -> ext.equals(extension));
    }

    /**
     * 파일 크기 제한 확인
     */
    public static boolean isWithinSizeLimit(MultipartFile file, long maxSizeInBytes) {
        return file.getSize() <= maxSizeInBytes;
    }

    /**
     * 안전한 파일명 생성 (특수문자 제거)
     */
    public static String sanitizeFilename(String filename) {
        if (filename == null) {
            return null;
        }
        // 위험한 문자 제거 및 공백을 언더스코어로 변경
        return filename.replaceAll("[^a-zA-Z0-9\\.\\-_가-힣]", "_");
    }

    /**
     * 고유한 파일명 생성 (UUID 사용)
     */
    public static String generateUniqueFilename(String originalFilename) {
        String extension = getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return extension.isEmpty() ? uuid : uuid + "." + extension;
    }

    /**
     * 타임스탬프 기반 파일명 생성
     */
    public static String generateTimestampFilename(String originalFilename) {
        String extension = getExtension(originalFilename);
        String nameWithoutExt = removeExtension(originalFilename);
        String timestamp = String.valueOf(System.currentTimeMillis());
        return nameWithoutExt + "_" + timestamp + "." + extension;
    }

    /**
     * MultipartFile을 지정된 경로에 저장
     */
    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot save empty file");
        }

        // 업로드 디렉토리 생성
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 고유한 파일명 생성
        String filename = generateUniqueFilename(file.getOriginalFilename());
        Path filePath = uploadPath.resolve(filename);

        // 파일 저장
        Files.copy(file.getInputStream(), filePath);

        return filename;
    }

    /**
     * 파일 삭제
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 파일 존재 여부 확인
     */
    public static boolean exists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * MIME 타입 추측
     */
    public static String guessMimeType(String filename) {
        String extension = getExtension(filename);

        // 이미지
        if (extension.equals("jpg") || extension.equals("jpeg")) return "image/jpeg";
        if (extension.equals("png")) return "image/png";
        if (extension.equals("gif")) return "image/gif";
        if (extension.equals("bmp")) return "image/bmp";
        if (extension.equals("webp")) return "image/webp";
        if (extension.equals("svg")) return "image/svg+xml";

        // 문서
        if (extension.equals("pdf")) return "application/pdf";
        if (extension.equals("doc")) return "application/msword";
        if (extension.equals("docx")) return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (extension.equals("xls")) return "application/vnd.ms-excel";
        if (extension.equals("xlsx")) return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if (extension.equals("ppt")) return "application/vnd.ms-powerpoint";
        if (extension.equals("pptx")) return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        if (extension.equals("txt")) return "text/plain";

        // 비디오
        if (extension.equals("mp4")) return "video/mp4";
        if (extension.equals("avi")) return "video/x-msvideo";
        if (extension.equals("mov")) return "video/quicktime";
        if (extension.equals("wmv")) return "video/x-ms-wmv";

        return "application/octet-stream";
    }
}
