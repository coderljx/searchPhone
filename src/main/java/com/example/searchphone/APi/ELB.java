package com.example.searchphone.APi;

import com.example.searchphone.utils.FileContent;
import com.example.searchphone.utils.NetwokELement;
import com.example.searchphone.utils.Response;
import com.example.searchphone.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/elb")
@CrossOrigin
public class ELB {

    @Value("${elbLog.path}")
    private String elbLoagPath;

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
            File files = new File(elbLoagPath);
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
        File file = new File(elbLoagPath + File.separator + fileName);
        FileContent fileContent = FileContent.parseFile(file, ip);

        return new Response<>(fileContent);
    }
}
