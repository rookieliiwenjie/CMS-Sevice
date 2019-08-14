package com.rookit.rabbitmq.Mq;

import com.rabbitmq.client.Channel;
import com.rookit.rabbitmq.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {
    @RabbitListener(queues = {RabbitMqConfig.QUEUEEMAIL})
    public void RecevieEmail(String msg, Message message, Channel channel){
        System.out.println(msg);

    }
    @RabbitListener(queues = {RabbitMqConfig.QUEUESMS})
    public void RecevieSms(String msg, Message message, Channel channel){
        System.out.println(msg);

    }
}
