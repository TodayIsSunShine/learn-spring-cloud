package com.xiangzi.learncloudconsumer.consumer;

import com.xiangzi.learncloudconsumer.annotation.RabbitFatalEncountered;
import com.xiangzi.learncloudconsumer.config.RabbitmqRetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Component
@RabbitListener(queues = RabbitmqRetryConfig.QUEUE)
public class RetryQueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryQueueConsumer.class);


    @RabbitHandler(isDefault = true)
    @RabbitFatalEncountered
    public void receiver(Message message)  {
        LOGGER.info("[OnMessage] [线程编号：{}，消息内容：{}]", Thread.currentThread().getId(), new String(message.getBody()));

//        try {
//            mqErrorService.test();
//        } catch (MessageRejectedWhileStoppingException e) {
//            //重回队列
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//        }
        throw new RuntimeException("1111");

    }
}
