package com.xiangzi.learncloudconsumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "my_demo_queue")
    @RabbitHandler
    public void receive(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        System.err.println(msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
