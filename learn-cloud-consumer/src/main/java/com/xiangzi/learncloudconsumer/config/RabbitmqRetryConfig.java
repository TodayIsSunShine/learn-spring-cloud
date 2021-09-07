package com.xiangzi.learncloudconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Configuration
public class RabbitmqRetryConfig {

    public static final String EXCHANGE = "NORMAL_EXCHANGE";
    public static final String QUEUE = "NORMAL_QUEUE";
    public static final String ROUTING_KEY = "NORMAL_ROUTING_KEY";

    public static final String DEAD_QUEUE = "DEAD_QUEUE";
    public static final String DEAD_ROUTING_KEY = "DEAD_ROUTING_KEY";


    @Bean
    public Queue normalQueue() { //重试达到次数后进入死信队列
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_ROUTING_KEY)
                .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Binding binding(@Qualifier("exchange") DirectExchange exchange, @Qualifier("normalQueue") Queue normalQueue) {
        return BindingBuilder.bind(normalQueue).to(exchange).with(ROUTING_KEY);

    }

    @Bean
    public Queue deadNormalQueue() {
        return new Queue(DEAD_QUEUE, true, false, false);
    }

    @Bean
    public Binding deadBinding(@Qualifier("exchange") DirectExchange exchange, @Qualifier("deadNormalQueue") Queue deadNormalQueue) {
        return BindingBuilder.bind(deadNormalQueue).to(exchange).with(DEAD_ROUTING_KEY);

    }
}
