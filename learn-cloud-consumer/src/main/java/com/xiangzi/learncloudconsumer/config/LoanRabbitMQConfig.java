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
public class LoanRabbitMQConfig {

    public static final String UNIVERSAL_DEAD_QUEUE = "pufa.universal.dead.queue";
    public static final String UNIVERSAL_DEAD_EXCHANGE = "pufa.dead.exchange";

    /***
     * 借款附件处理队列RabbitMQ配置
     *
     */
    @Configuration
    public static class LoanAttachRabbitMQConfig {

        public static final String ATTACH_FANOUT_EXCHANGE = "pufa.attach.fanout.exchange";
        public static final String ATTACH_QUEUE = "pufa.attach.queue";


        @Bean
        public Queue loanAttachQueue() { //重试达到次数后进入死信队列
            return QueueBuilder.durable(ATTACH_QUEUE)
                    .withArgument("x-dead-letter-exchange", UNIVERSAL_DEAD_EXCHANGE)
                    .build();
        }

        @Bean
        public Queue loanAttachDeadQueue() {
            return new Queue(UNIVERSAL_DEAD_QUEUE, true, false, false);
        }


        @Bean
        public FanoutExchange loanAttachFanoutExchange() {
            return new FanoutExchange(ATTACH_FANOUT_EXCHANGE);
        }

        @Bean
        public FanoutExchange loanAttachDeadFanoutExchange() {
            return new FanoutExchange(UNIVERSAL_DEAD_EXCHANGE);
        }


        @Bean
        public Binding loanAttachBinding(@Qualifier("loanAttachFanoutExchange") FanoutExchange loanAttachFanoutExchange,
                                         @Qualifier("loanAttachQueue") Queue loanAttachQueue) {
            return BindingBuilder.bind(loanAttachQueue).to(loanAttachFanoutExchange);
        }

        @Bean
        public Binding loanAttachDeadBinding(@Qualifier("loanAttachDeadFanoutExchange") FanoutExchange loanAttachDeadFanoutExchange, @Qualifier("loanAttachDeadQueue") Queue loanAttachDeadQueue) {
            return BindingBuilder.bind(loanAttachDeadQueue).to(loanAttachDeadFanoutExchange);

        }
    }

}
