package com.rookit.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String QUEUEEMAIL = "queues_email";
    public static final String QUEUESMS = "queues_sms";
    public static final String EXCHANGE = "exchange_topic";
    public static final String RoutKey_EMAIL = "inform.#.email.#";
    public static final String RoutKey_SMS = "inform.#.sms.#";

    //声明交换机
    @Bean(EXCHANGE)
    public Exchange EXCHANGE() {
        //持久化交互机mq重启后交换机孩还在
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();

    }

    //声明队列
    @Bean(QUEUEEMAIL)
    public Queue QueueEmail() {
        return new Queue(QUEUEEMAIL);
    }
    @Bean(QUEUESMS)
    public Queue QueueSms() {
        return new Queue(QUEUESMS);
    }
    //绑定交换机和队列 routingkey
    @Bean
    public Binding Bing_Email_Exchange(@Qualifier(QUEUEEMAIL) Queue queue,@Qualifier(EXCHANGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(RoutKey_EMAIL).noargs();
    }
    @Bean
    public Binding Bing_Sms_Exchange(@Qualifier(QUEUESMS) Queue queue,@Qualifier(EXCHANGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(RoutKey_SMS).noargs();
    }

}
