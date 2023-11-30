package com.example.searchphone;

import ch.ethz.ssh2.Connection;
import com.alibaba.fastjson2.JSONObject;
import com.example.searchphone.utils.NetwokELement;
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
import java.util.List;

@SpringBootTest
class SearchPhoneApplicationTests {



    @Test
    void contextLoads() throws Exception{
        JSONObject jsonObject = JSONObject.parseObject(NetwokELement.parseData());
        System.out.println(( (JSONObject) jsonObject.get("NE2")).get("elb"));


    }


    public static void main(String[] args) throws Exception{
//        Process exec = Runtime.getRuntime().exec("sshpass -p  bash /Users/brother/Desktop/searchPhone/src/main/java/com/example/searchphone/sh/esip.sh");
        Connection connection = new Connection("");


    }


}
