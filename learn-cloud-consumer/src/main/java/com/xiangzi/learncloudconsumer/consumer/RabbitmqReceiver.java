package com.xiangzi.learncloudconsumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitmqReceiver {
    //配置监听的哪一个队列，同时在没有queue和exchange的情况下会去创建并建立绑定关系
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", declare = "true"),//durable： 是否持久化
            exchange = @Exchange(name = "order-exchange", declare = "true", type = "topic"),
            key = "order.*"
    )
    )

    @RabbitHandler //如果有消息过来，在消费的时候调用这个方法
    public void onMessage(Message message, Channel channel) throws IOException {
        //消费者操作
        System.out.println("---------收到消息，开始消费---------");

        /**
         * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        String messageStr = new String(message.getBody());
        System.out.println("消费者接受到的消息：" + messageStr);
        try {
            /**
             *  multiple取值为 false时，表示通知RabbitMQ当前消息被确认
             *  如果为 true则额外将比第一个参数指定的deliveryTag小的消息一并确认
             */
            boolean multiple = false;
            if (messageStr.equals("hello")) {
                long deliveryTag = message.getMessageProperties().getDeliveryTag();
                System.err.println("deliveryTag:" + deliveryTag);
                //ACK,确认一条消息已经被消费,可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
                channel.basicAck(deliveryTag, multiple);
            }

        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 如果channel.basicNack(8, true, true);表示deliveryTag=8之前未确认的消息都处理失败且将这些消息重新放回队列中。
             * 如果channel.basicNack(8, true, false);表示deliveryTag=8之前未确认的消息都处理失败且将这些消息直接丢弃。
             * 如果channel.basicNack(8, false, true);表示deliveryTag=8的消息处理失败且将该消息重新放回队列。
             * 如果channel.basicNack(8, false, false);表示deliveryTag=8的消息处理失败且将该消息直接丢弃。
             */
            //丢弃掉这个消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

        }
    }
}
