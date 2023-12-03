package com.example.searchphone.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.File;

//日志的文件信息
@Data
public class FileContent {
    private String name;
    private String ip;

    private String context;

    private String date;

    public static FileContent parseFile(File file,String ip) {
        FileContent fileContent = new FileContent();
        fileContent.setName(file.getName());
        fileContent.setIp("192.168.68." + ip);
        // 日志内容只筛选最近3天的
        String agoThreeTime = TimeUtil.agoThreeTime(1);
        String data = NetwokELement.readFileToString(file);
        int indexOf = data.indexOf(agoThreeTime);
        // 如果没有找到则返回-1，那我们就不需要过滤，直接将整个日志文件读取出来
        if (indexOf <= 0) {
            indexOf = 0;
        }
        fileContent.setContext(data.substring(indexOf));
        return fileContent;
    }
}
