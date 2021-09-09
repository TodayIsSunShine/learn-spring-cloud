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
public class LoanProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanProducer.class);

    public static final String EXCHANGE = "pufa.attach.fanout.exchange";


    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg) {
        LOGGER.info("发出去的消息：{}", msg);
        amqpTemplate.convertAndSend(EXCHANGE, null, msg);
    }
}
