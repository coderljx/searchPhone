package com.example.searchphone.APi;

import com.example.searchphone.LjxRedis.RedisString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping ("/search")
//@CrossOrigin
public class searchPhoneService {
    private final Logger mylog = LoggerFactory.getLogger(searchPhoneService.class);


    @Autowired
    private RedisString redisString;

    /**
     * 查询redis里的手机号是否被拦截
     *
     * @param sf    省份编号
     * @param type  103 主叫 105 被叫
     * @param phone 被查询的手机号
     */
    @ResponseBody
    @GetMapping ("/phone")
    public Map<String, String> searchPhone(@RequestParam ("sf") String sf,
                                           @RequestParam ("type") String type,
                                           @RequestParam ("phone") String phone) {

//        policyinfo:13+103+13003806988
        String key = "policyinfo:" + sf + "+" + type + "+" + phone;
        Map<String, String> phoneList = redisString.getPhoneList(key);
        return phoneList;
    }


    @ResponseBody
    @GetMapping ("/phones")
    public List<String> searchPhones(@RequestParam ("sf") String sf,
                                     @RequestParam ("type") String type,
                                     @RequestParam ("phone") String phone) {

//        policyinfo:13+103+13003806988
        String[] split = phone.split(",");
        List<String> result = new ArrayList<>();
        if (split.length > 0) {
            for (String num : split) {
                String key = "policyinfo:" + sf + "+" + type + "+" + num;
                Map<String, String> phoneList = redisString.getPhoneList(key);
                // 如果查询到这个手机号有拦截
                if (phoneList != null && phoneList.size() > 0) {
                    result.add(num);
                }
            }

        }

        return result;
    }


}
