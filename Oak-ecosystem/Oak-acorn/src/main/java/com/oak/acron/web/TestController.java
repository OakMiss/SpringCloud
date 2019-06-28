package com.oak.acron.web;

import com.oak.acron.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Oak on 2018/6/28.
 * Description:
 */
@RestController
@RequestMapping(value = "/oak/acore")
public class TestController {

    @Autowired
    private TestService service;
    @GetMapping(value = "/get")
    public String test(){
        return service.test();
    }
}
