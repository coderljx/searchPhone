package com.example.searchphone.APi;

import com.example.searchphone.utils.FileContent;
import com.example.searchphone.utils.NetwokELement;
import com.example.searchphone.utils.Response;
import com.example.searchphone.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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


    @Value("${elbShell.path}")
    private String elbShellPath;



    /**
     * 获取所有日志的
     */
    @GetMapping("/getAllIp/{type}")
    @ResponseBody
    public Response<?> getAllEsipIp(@PathVariable("type") int type) {
        List<String> ip;
        List<FileContent> resultList;
        try {
            ip = NetwokELement.getIp(type);
            NetwokELement.shell(elbShellPath);
            resultList = NetwokELement.forEachFile(ip,elbLoagPath);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response<>(resultList);
    }

}
