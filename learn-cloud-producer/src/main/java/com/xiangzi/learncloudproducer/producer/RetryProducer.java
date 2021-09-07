package com.xiangzi.learncloudproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Service
public class RetryProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryProducer.class);

    public static final String EXCHANGE = "NORMAL_EXCHANGE";
    public static final String ROUTING_KEY = "NORMAL_ROUTING_KEY";


    @Autowired
    private AmqpTemplate amqpTemplate;


    public void send(String msg) {
        LOGGER.info("发出去的消息：{}", msg);
        amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);
    }
}
