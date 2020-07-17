package com.xiangzi.learncloudproducer.controller;

import com.xiangzi.learncloudproducer.producer.Producer;
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
    private Producer producer;

    @RequestMapping("/sendMessage")
    public String testSend(@RequestParam("message") String message, @RequestParam("time") Long time) {
        producer.send(message, time);
        return "success";
    }
}
