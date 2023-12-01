package com.example.searchphone.APi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/esip")
@CrossOrigin
public class ESIP {
    private final Logger mylog = LoggerFactory.getLogger(ESIP.class);

    private static final String logSystem = "sys_dbgprint_";

    @GetMapping("/getEsip")
    @ResponseBody
    public void getEsip(String name) {

    }





}
