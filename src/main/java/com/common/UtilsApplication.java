package com.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Web Utils Application
 */
@SpringBootApplication
public class UtilsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilsApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("🚀 Utils Application Started Successfully!");
        System.out.println("📱 Web Test Page: http://localhost:8080");
        System.out.println("🔧 API Base URL: http://localhost:8080/api/utils");
        System.out.println("==============================================\n");
    }
}
