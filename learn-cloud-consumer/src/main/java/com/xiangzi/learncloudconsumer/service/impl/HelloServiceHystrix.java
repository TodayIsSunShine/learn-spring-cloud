package com.xiangzi.learncloudconsumer.service.impl;

import com.xiangzi.learncloudconsumer.service.HelloService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description
 * author:zhangxx
 * Date:2019/9/17
 * Time:13:57
 */
@Component
public class HelloServiceHystrix implements HelloService {
    @Override
    public String hello(@RequestParam("name") String name) {
        return "hello" + name + ", this message send failure";
    }
}
