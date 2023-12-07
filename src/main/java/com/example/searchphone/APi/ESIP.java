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

    @Value("${esipShell.path}")
    private String esipShellPath;



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



}
