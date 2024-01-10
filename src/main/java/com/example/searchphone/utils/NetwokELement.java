package com.example.searchphone.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import javafx.scene.control.Tab;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

// 包含了所有的网元信息
@Component
public class NetwokELement {
    private final Logger mylog = LoggerFactory.getLogger(NetwokELement.class);


    // 读取每个网元的配置信息
    public static String parseData() throws Exception {
//        File file = ResourceUtils.getFile("/watch/backend/NetworkElement.txt");
//        InputStream resource = NetwokELement.class.getResourceAsStream("static/NetworkElement.txt");
        String networkElement = "C:\\Users\\Brother\\Desktop\\searchPhone\\searchPhone\\src\\main\\resources\\static\\NetworkElement.txt";
//        String networkElement = "/watch/backend/NetworkElement.txt";
        return readFileToString(new File(networkElement));
    }

    /**
     * 通过软件编号 查询该软件部署在那些机器上，返回这些机器的集合
     *
     * @param type 1 : elb
     *             2 : esip
     *             3 : list1
     *             4 : list2 (7台服务器)
     */
    public static List<String> getIp(int type) throws Exception {
        String key = "";
        if (type == 1) key = "elb";
        if (type == 2) key = "esip";
        if (type == 3) key = "list1";
        if (type == 4) key = "list2";
        JSONObject jsonObject = JSONObject.parseObject(parseData());
        Set<String> keys = jsonObject.keySet();
        List<String> result = new ArrayList<>();
        for (String item : keys) {
            JSONObject list = (JSONObject) jsonObject.get(item);
            JSONArray jsonArray = (JSONArray) list.get(key);
            jsonArray.forEach(i -> result.add((String) i));
        }
        return result;
    }

    /**
     * 直接通过json的key获取对应的服务器数组
     *
     * @param type
     * @return
     * @throws Exception
     */
    public static List<String> getIp(String type) throws Exception {
        if (type.equals("mif") || type.equals("scmd")) type = "list1";
        if (type.equals("vmsip")) type = "list2";
        if (type.equals("rtds") || type.equals("isftp")) type = "ivps";
        JSONObject jsonObject = JSONObject.parseObject(parseData());
        Set<String> keys = jsonObject.keySet();
        List<String> result = new ArrayList<>();
        for (String item : keys) {
            JSONObject list = (JSONObject) jsonObject.get(item);
            JSONArray jsonArray = (JSONArray) list.get(type);
            jsonArray.forEach(i -> result.add((String) i));
        }
        return result;
    }


    /**
     * 读取这个文件的内容 装换成字符串，用于读取日志文件
     *
     * @param file
     * @return
     */
    public static String readFileToString(File file) {
        final String CHARSET_NAME = "UTF-8";
        StringBuilder datas = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), CHARSET_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                datas.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas.toString();
    }

    /**
     * 对大文件进行截取，只取出一部分的内容
     * @param file
     * @return
     */
    public static void subFile(File file) {
        final String CHARSET_NAME = "UTF-8";
        StringBuilder datas = new StringBuilder();
        long length = file.length();
        int maxLength = 1048576 * 3;
        // 判断该文件是否大于限制的文件大小
        boolean fileSub = false;
        if (length > maxLength) fileSub = true;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), CHARSET_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 如果这个文件本身大小超过了限制，并且读取的内容也超过了，那么我们就截取
                if (datas.length() > 5000 && fileSub) {
                    datas.delete(0,datas.length());
                }
                // 清除之前的读取内容，然后我们再继续读取后面的内容
                datas.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fileSub) {
            // 对原来的文件进行覆盖，只保留最后的一部分
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(datas.toString().getBytes("UTF-8"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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


    /**
     * 遍历一个文件夹下的所有文件,解析出文件的名称
     *
     * @param filePath
     */
    public static Map<String, List<File>> foreachFiles(String filePath) {
        File file = new File(filePath);
        Map<String, List<File>> fileList = new Hashtable<>();
        if (!file.isDirectory() || !file.exists()) {
            return fileList;
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File file1 : files) {
                String name = file1.getName();
                String ip = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
                List<File> datas = fileList.get(ip);
                //map里面没有这个key 则新生成一个数据写入map
                if (datas == null || datas.size() == 0) {
                    List<File> fs = new ArrayList<>();
                    fs.add(file1);
                    fileList.put(ip, fs);
                } else {
                    datas.add(file1);
                    fileList.put(ip, datas);
                }
            }
        }
        return fileList;
    }


    /**
     * 脚本名称
     *
     * @param shellName
     */
    public static void shell(String shellName) throws Exception {
        Runtime.getRuntime().exec("bash " + " " + shellName);
    }


    /**
     * 遍历这个文件夹，找到该文件夹下 ip与传入ip匹配的文件
     *
     * @param ip
     * @param filePath
     */
    public static List<FileContent> forEachFile(List<String> ip, String filePath) throws Exception {
        List<FileContent> resultList = new ArrayList<>();
        File files = new File(filePath);
        File[] files1 = files.listFiles();
        if (files1 != null) {
            for (File file : files1) {
                for (String i : ip) {
                    if (file.getName().contains("(" + i.substring(11) + ")")) {
                        FileContent fileContent = new FileContent();
                        fileContent.setIp(i);
                        fileContent.setName(file.getName());
                        fileContent.setDate(TimeUtil.ParseDate(new Date(file.lastModified()), 1));
                        fileContent.setData(formatSize(file.length())); // 文件的大小
                        resultList.add(fileContent);
                    }
                }
            }
        }
        return resultList;
    }


    public static String formatSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        // 1b
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
            // 1m
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
            // 1g
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
            // 1t
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


}
