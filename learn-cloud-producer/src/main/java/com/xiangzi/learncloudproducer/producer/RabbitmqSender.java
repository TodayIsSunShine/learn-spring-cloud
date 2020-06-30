package com.xiangzi.learncloudproducer.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送消息
 */
@Component
public class RabbitmqSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    //回调函数：confirm确认消息是否正确到达Broker服务器
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.err.println("correlationData:" + correlationData);
            String id = correlationData.getId();
            System.out.println("id:" + id);
            System.err.println("s:" + s);
            if (ack) {
                //处理逻辑,更新数据库消息状态值为发送成功
                System.out.println("消息发送成功了");
            } else {
                //补偿机制或者重试
                System.err.println("发送失败...");
            }
        }
    };

    //发送消息方法调用：构建自定义对象消息
    public void sender(String message) {
        //实现callback接口，消息达到broker后触发回调，确认消息是否正确到达exchange中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        String messageId = "123";
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend("order-exchange", "order.test", message, correlationData);

    }
}
