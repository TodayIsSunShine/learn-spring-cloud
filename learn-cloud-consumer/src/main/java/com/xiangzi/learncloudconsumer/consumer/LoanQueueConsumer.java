package com.xiangzi.learncloudconsumer.consumer;

import com.rabbitmq.client.Channel;
import com.xiangzi.learncloudconsumer.annotation.RabbitFatalEncountered;
import com.xiangzi.learncloudconsumer.config.RabbitmqRetryConfig;
import com.xiangzi.learncloudconsumer.service.MqErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.MessageRejectedWhileStoppingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Component
public class LoanQueueConsumer {

    @Autowired
    private MqErrorService mqErrorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanQueueConsumer.class);


    @RabbitFatalEncountered
    @RabbitListener(queues = "pufa.attach.queue")
    public void receiver(Message message, Channel channel) throws IOException {
        LOGGER.info("[OnMessage] [线程编号：{}，消息内容：{}]", Thread.currentThread().getId(), new String(message.getBody()));

        try {
            mqErrorService.test();
        } catch (MessageRejectedWhileStoppingException e) {
            //重回队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }
}
