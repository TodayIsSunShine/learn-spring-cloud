package com.xiangzi.learncloudconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
@Configuration
public class RabbitmqConfig {


    public static final String my_demo_queue_routing_key = "my_demo_queue_routing_key";
    public static final String my_demo_exchange = "my_demo_exchange";
    public static final String my_demo_dead_letter_routing_key = "my_demo_dead_letter_routing_key";
    public static final String my_demo_queue = "my_demo_queue";
    public static final String my_demo_dead_letter_queue = "my_demo_dead_letter_queue";


    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(my_demo_exchange);
    }

    //声明死信队列
    @Bean
    public Queue delayLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", my_demo_exchange);
        arguments.put("x-dead-letter-routing-key", my_demo_queue_routing_key);
        return new Queue(my_demo_dead_letter_queue, false, false, false, arguments);
    }


    //声明死信队列绑定关系
    @Bean
    public Binding deadLetterQueue(@Autowired @Qualifier("delayLetterQueue") Queue queue, @Autowired @Qualifier("deadLetterExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(my_demo_dead_letter_routing_key);
    }

    //声明延迟队列
    @Bean
    public Queue myQueue() {
        return new Queue(my_demo_queue, true, false, false, null);
    }

    //声明延迟队列A绑定关系
    @Bean
    public Binding delayBinding(@Autowired @Qualifier("myQueue") Queue queue, @Autowired @Qualifier("deadLetterExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(my_demo_queue_routing_key);
    }
}
