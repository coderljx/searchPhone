package com.example.searchphone;

import com.example.searchphone.utils.NetwokELement;
import com.example.searchphone.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

// 扫描所有的包路径
@SpringBootApplication(scanBasePackages = "com")
@EnableScheduling
public class SearchPhoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchPhoneApplication.class, args);
    }

    @Value("${esipShell.path}")
    private String esipShellPath;
    private String koko;

    /**
     * 定时执行任务，调用脚本获取不同服务器上的日志文件
     * 每小时执行一次
     * 
     * @throws Exception
     */
    // @Scheduled(cron = "0 */30 * * * ?") //定时任务注解+cron表达式
    // public void testScheduleTask() throws Exception {
    //// NetwokELement.shell(esipShellPath);
    // System.out.println(TimeUtil.ParseDate(new Date()) + " " + 123);
    // }

}
