package com.example.searchphone.utils;

import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// 包含了所有的网元信息
@Component
public class NetwokELement {
    private final Logger mylog = LoggerFactory.getLogger(NetwokELement.class);


    //    读取每个网元的配置信息
    public static String parseData() throws Exception {
        File fileName = ResourceUtils.getFile("classpath:NetworkElement.txt");
        final String CHARSET_NAME = "UTF-8";
        List<String> content = new ArrayList<>();
        StringBuilder datas = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), CHARSET_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String s : content) {
            datas.append(s);
        }
        return datas.toString();
    }


}
