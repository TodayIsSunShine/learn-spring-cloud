package com.xiangzi.learnconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient //开启服务注册发现
public class LearnConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnConfigServerApplication.class, args);
    }

}
