package com.example.searchphone;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson2.JSONObject;
import com.example.searchphone.utils.NetwokELement;
import com.example.searchphone.utils.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SearchPhoneApplicationTests {


    @Test
    void contextLoads() throws Exception {

    }


    public static void main(String[] args) throws Exception {

        File file = new File("D:\\资料\\1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        StringBuilder datas = new StringBuilder("asdasdas\n" +
                "asdasda\n" +
                "dasdasdasd");
        fileOutputStream.write(datas.toString().getBytes("UTF-8"));

        String agoThreeTime = TimeUtil.agoThreeTime(-1);
        System.out.println(agoThreeTime);
    }


}
