package com.xiangzi.learncloudconsumer.aspect;

import com.rabbitmq.client.Channel;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Component
@Aspect
public class RabbitFatalEncounteredAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitFatalEncounteredAspect.class);


    /**
     * key name
     *
     */
    private final static String RETRY_KEY = "retry_count";

    /**
     * 最大重试 3次
     *
     */
    @Value("${spring.application.rabbitmq.listener.simple.retry.max-attempts:3}")
    private int maxAttempts;

    @AfterThrowing(throwing = "e", pointcut = "@annotation(com.xiangzi.learncloudconsumer.annotation.RabbitFatalEncountered) && args(message, channel)", argNames = "e, message, channel")
    public void doAfterThrowing(Throwable e, Message message, Channel channel) throws Throwable {
        LOGGER.error("-1", "队列监听处理失败, message: " + new String(message.getBody()), e);
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        Object retryVal = headers.get(RETRY_KEY);
        Integer retryCount = Optional.ofNullable(retryVal)
                .map(String::valueOf)
                .map(Integer::parseInt)
                .map(x -> x = x + 1)
                .orElse(1);

        messageProperties.setHeader(RETRY_KEY, retryCount);

        if (retryCount >= maxAttempts) {
            channel.basicReject(messageProperties.getDeliveryTag(), false);
        }
        else {
            throw e;
        }
    }
}
