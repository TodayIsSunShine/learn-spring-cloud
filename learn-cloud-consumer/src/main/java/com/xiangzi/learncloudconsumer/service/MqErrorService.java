package com.xiangzi.learncloudconsumer.service;

import org.springframework.stereotype.Service;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Service
public class MqErrorService {
    public void test() {
        throw new RuntimeException("111");
    }
}
