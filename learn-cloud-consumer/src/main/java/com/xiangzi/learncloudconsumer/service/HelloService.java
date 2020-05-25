package com.xiangzi.learncloudconsumer.service;

import com.xiangzi.learncloudconsumer.service.impl.HelloServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "learn-cloud-producer", fallback = HelloServiceHystrix.class)
public interface HelloService {


    @RequestMapping("/hello")
    String hello(@RequestParam(value = "name") String name);

    @RequestMapping("/world")
    String world(@RequestParam(value = "name") String name);

}
