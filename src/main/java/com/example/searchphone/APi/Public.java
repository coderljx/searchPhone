package com.example.searchphone.APi;


import com.example.searchphone.utils.FileContent;
import com.example.searchphone.utils.NetwokELement;
import com.example.searchphone.utils.Response;
import com.example.searchphone.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/public")
@CrossOrigin
public class Public {

    @Value("${esipLog.path}")
    private String esipLoagPath;
    @Value("${esipShell.path}")
    private String esipShellPath;

    @Value("${elbLog.path}")
    private String elbLoagPath;
    @Value("${elbShell.path}")
    private String elbShellPath;

    @Value("${mifLog.path}")
    private String mifLogPath;
    @Value("${mifShell.path}")
    private String mifShellPath;

    @Value("${scmdLog.path}")
    private String scmdLogPath;
    @Value("${scmdShell.path}")
    private String scmdShellPath;

    @Value("${lsamLog.path}")
    private String lsamLogPath;
    @Value("${lsamShell.path}")
    private String lsamShellPath;

    @Value("${vmsipLog.path}")
    private String vmsipLogPath;
    @Value("${vmsipShell.path}")
    private String vmsipShellPath;

    @Value("${ivpsLog.path}")
    private String ivpsLogPath;
    @Value("${ivpsShell.path}")
    private String ivpsShellPath;

    @Value("${isftpLog.path}")
    private String isftpLogPath;
    @Value("${isftpShell.path}")
    private String isftpShellPath;


    @Value("${rtdsLog.path}")
    private String rtdsLogPath;
    @Value("${rtdsShell.path}")
    private String rtdsShellPath;

    public String getPathFromtype(String type) {
        // 存放日志文件的路径
        String[] logPath = new String[]{esipLoagPath,elbLoagPath,mifLogPath,scmdLogPath,lsamLogPath,vmsipLogPath,ivpsLogPath,rtdsLogPath,isftpLogPath};

        String result = "";
        for (String log : logPath) {
            if (log.contains(type)) {
                result = log;
                break;
            }
        }

        return result;
    }


    @Scheduled(cron = "0 */30 * * * ?") //定时任务注解+cron表达式
    public void testScheduleTask() throws Exception {
        //        存放脚本文件的路径
//        String[] shellPath = new String[]{elbShellPath,esipShellPath,mifShellPath,scmdShellPath,lsamShellPath,vmsipShellPath,ivpsShellPath,rtdsShellPath,isftpShellPath};
        String[] shellPath = new String[]{elbShellPath,esipShellPath,mifShellPath,scmdShellPath,vmsipShellPath,ivpsShellPath,rtdsShellPath,isftpShellPath};
        for (String shell : shellPath) {
            NetwokELement.shell(shell);
        }

    }



    @GetMapping("/getAllIp/{type}")
    @ResponseBody
    public Response<?> getAllEsipIp(@PathVariable("type") String type) {
        List<String> ip;
        List<FileContent> resultList;
        try {
            ip = NetwokELement.getIp(type);
//            NetwokELement.shell(esipShellPath);
            resultList = NetwokELement.forEachFile(ip,getPathFromtype(type));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response<>(resultList);
    }



    @GetMapping("/getName/{type}/{ip}")
    @ResponseBody
    public Response<?> getEsipName(@PathVariable("type") String type,
                                   @PathVariable("ip") String ip,
                                   @RequestParam("fileName") String fileName) {
        File file = new File(getPathFromtype(type) + File.separator + fileName);
        FileContent fileContent = FileContent.parseFile(file, ip);

        return new Response<>(fileContent);
    }

}
