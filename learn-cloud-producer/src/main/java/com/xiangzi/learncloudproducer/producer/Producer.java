package com.xiangzi.learncloudproducer.producer;

import com.xiangzi.learncloudproducer.config.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String msg, long time) {
        //rabbit默认为毫秒级
        long times = time * 1000;
        MessagePostProcessor processor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(String.valueOf(times));
                return message;
            }
        };
        LOGGER.info("准备发送消息");

        amqpTemplate.convertAndSend(RabbitmqConfig.my_demo_exchange, RabbitmqConfig.my_demo_dead_letter_routing_key, msg, processor);
    }
}
