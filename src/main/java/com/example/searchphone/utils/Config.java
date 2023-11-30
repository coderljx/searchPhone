package com.example.searchphone.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Config {
    private final Logger mylog = LoggerFactory.getLogger(Config.class);

    // esip的存在目录
    private String dir = "/imsfz/esip/log";

}
