package com.example.searchphone.APi;

import com.example.searchphone.utils.FileContent;
import com.example.searchphone.utils.NetwokELement;
import com.example.searchphone.utils.Response;
import com.example.searchphone.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/esip")
@CrossOrigin
public class ESIP {
    private final Logger mylog = LoggerFactory.getLogger(ESIP.class);

    @Value("${esipLog.path}")
    private String esipLoagPath;


    /**
     * 获取所有日志的
     */
    @GetMapping("/getAllIp/{type}")
    @ResponseBody
    public Response<?> getAllEsipIp(@PathVariable("type") int type) {
        List<String> ip;
        List<FileContent> resultList = new ArrayList<>();
        try {
            ip = NetwokELement.getIp(type);
            File files = new File(esipLoagPath);
            File[] files1 = files.listFiles();
            if (files1 != null) {
                for (File file : files1) {
                    for (String i : ip) {
                        if (file.getName().contains("(" + i.substring(11) + ")")) {
                            FileContent fileContent = new FileContent();
                            fileContent.setIp(i);
                            fileContent.setName(file.getName());
                            fileContent.setDate(TimeUtil.ParseDate(new Date(file.lastModified()), 2));
                            resultList.add(fileContent);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response<>(resultList);
    }


    /**
     * 获取esip日志的信息
     */
    @GetMapping("/gets")
    @ResponseBody
    public Response<?> getEsips() {
        Map<String, List<File>> stringListMap = NetwokELement.foreachFiles(esipLoagPath);
        Map<String, List<FileContent>> result = new HashMap<>();
        stringListMap.keySet().forEach(item -> {
            List<File> files = stringListMap.get(item);
            List<FileContent> fileResult = new ArrayList<>();
            for (File file : files) {
                FileContent fileContent = FileContent.parseFile(file, item);
                fileResult.add(fileContent);
            }
            result.put(item, fileResult);
        });
        return new Response<>(result);
    }

    /**
     * 根据具体的ip（最后两位ip地址） 来查询这台机器对应的esip日志文件
     *
     * @param ip
     * @return
     */
    @GetMapping("/get/{ip}")
    @ResponseBody
    public Response<?> getEsip(@PathVariable("ip") String ip) {
        Map<String, List<File>> stringListMap = NetwokELement.foreachFiles(esipLoagPath);
        Map<String, List<FileContent>> result = new HashMap<>();

        List<File> files = stringListMap.get(ip);
        List<FileContent> fileResult = new ArrayList<>();
        if (files != null && files.size() > 0) {
            for (File file : files) {
                FileContent fileContent = FileContent.parseFile(file, ip);
                fileResult.add(fileContent);
            }
        }
        result.put(ip, fileResult);

        return new Response<>(result);
    }

    /**
     * 根据ip 名称来查询这个文件的内容等信息
     *
     * @param ip
     * @param fileName
     * @return
     */
    @GetMapping("/getName/{ip}")
    @ResponseBody
    public Response<?> getEsipName(@PathVariable("ip") String ip,
                                   @RequestParam("fileName") String fileName) {
        File file = new File(esipLoagPath + File.separator + fileName);
        FileContent fileContent = FileContent.parseFile(file, ip);

        return new Response<>(fileContent);
    }


}
