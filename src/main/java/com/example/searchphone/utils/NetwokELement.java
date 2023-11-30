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
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(fileName.toPath()), CHARSET_NAME))) {
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


    /**
     * 对一个目录下的文件进行排序，拿到最新的文件
     *
     * @param filePath 需要排序的文件夹
     * @param fileName 参与排序的文件名
     */
    public static String sortFileDir(String filePath, String fileName) {
        File file = new File(filePath);
        Map<Long, File> fileList = new HashMap<>();
        // 如果该目录下有文件，则将需要排序的文件写到集合中
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File item : files) {
                    if (item.getName().contains(fileName)) {
                        fileList.put(item.lastModified(), item);
                    }
                }
            }
        }
        Set<Long> sortSet = new TreeSet<>(Comparator.reverseOrder());
        sortSet.addAll(fileList.keySet());
        File file1 = fileList.get(new ArrayList<>(sortSet).get(0));
        return file1.getName();
    }


}
