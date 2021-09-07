package com.xiangzi.learncloudconsumer.consumer;

import com.rabbitmq.client.Channel;
import com.xiangzi.learncloudconsumer.config.RabbitmqRetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Component
@RabbitListener(queues = RabbitmqRetryConfig.DEAD_QUEUE)
public class RetryDeadQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryDeadQueueConsumer.class);


    @RabbitHandler(isDefault = true)
    public void receiver(Message message, Channel channel) throws IOException {
        LOGGER.info("[OnMessage] [【死信队列】线程编号：{}，消息内容：{}]", Thread.currentThread().getId(), new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

    }
}
