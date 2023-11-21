package com.czl.console.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Author: CHEN ZHI LING
 * Date: 2023/7/3
 * Description:
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.czl.console.backend.core.*.dao",
        "com.czl.console.backend.*.config",
        "com.czl.console.backend.*.dao"})
public class CzlApplication {

    public static void main(String[] args) {
        SpringApplication.run(CzlApplication.class, args);
    }
}
