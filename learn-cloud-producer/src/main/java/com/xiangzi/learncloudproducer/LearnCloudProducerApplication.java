package com.xiangzi.learncloudproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LearnCloudProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnCloudProducerApplication.class, args);
    }

}
