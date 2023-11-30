package com.example.searchphone.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

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

    /**
     * 对一个目录下的文件进行排序，取最新的文件进行读取
     * @param filePath  文件夹路径
     * @param fileName  需要进行排序的文件名
     */
    public void softFile(String filePath,String fileName) {
        File dirs = new File(filePath);
        List<File> sortFiles = new ArrayList<>();
        Map<Long,File> fileMap = new HashMap<>();
        // 如果当前是文件夹，则遍历所有的文件
        // 将该文件夹下需要排序的文件全部放入集合中
        if (dirs.isDirectory()) {
            File[] listFiles = dirs.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                return;
            }
            for (File file : listFiles) {
                if (file.getName().contains(fileName)) {
                    sortFiles.add(file);
                    fileMap.put(file.lastModified(),file);
                }
            }
        }

        Set<Long> longs1 = fileMap.keySet();
        for (int i = 0; i < longs1.size(); i++) {

        }
    }



}
