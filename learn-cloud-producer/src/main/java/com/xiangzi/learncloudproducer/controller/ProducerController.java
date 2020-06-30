package com.xiangzi.learncloudproducer.controller;

import com.xiangzi.learncloudproducer.producer.RabbitmqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 * author:zhangxx
 * Date:2019/9/16
 * Time:17:49
 */
@RestController
public class ProducerController {

    @Autowired
    private RabbitmqSender rabbitmqSender;

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        return "hello:" + name + ",this is first message";
    }

    @RequestMapping("/world")
    public String world(@RequestParam String name) {
        return "world:" + name + ",this is test message";
    }

    @RequestMapping("/test/send")
    public String testSend(@RequestParam("message") String message) {
        rabbitmqSender.sender(message);
        return "success";
    }
}
