package com.xiangzi.learncloudproducer2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 * author:zhangxx
 * Date:2019/9/17
 * Time:11:31
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return "hello" + name + ",this is second message";
    }
}
