package com.zee.systems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @dev : Ezekiel Eromosei
 * @date : 15 Jul, 2026
 */

@SpringBootApplication(scanBasePackages = "com.zee.systems")
public class HrServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(HrServiceApplication.class, args);
    }
}
