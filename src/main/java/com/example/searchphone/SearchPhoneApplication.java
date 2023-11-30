package com.example.searchphone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 扫描所有的包路径
@SpringBootApplication(scanBasePackages = "com")
public class SearchPhoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchPhoneApplication.class, args);
    }

}
