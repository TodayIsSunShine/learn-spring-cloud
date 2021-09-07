package com.xiangzi.learncloudconsumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;


/**
 * 监听延迟消息
 */
@Component
public class DeadLetterQueueConsumer {

    //避免重复消费消息，服务重启后长度为0，线上使用redis或者存入表里
    private static HashSet hashSet = new HashSet();

    @RabbitListener(queues = "my_demo_queue") //用来监听队列里面的消息
    @RabbitHandler
    public void receive(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        if (!hashSet.contains(msg)) {
            System.err.println("接受到的msg：" + msg);
            hashSet.add(msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            System.err.println("重复消费了..");
            //丢弃掉
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
