package com.xiangzi.learnconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LearnConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnConfigClientApplication.class, args);
    }

}
