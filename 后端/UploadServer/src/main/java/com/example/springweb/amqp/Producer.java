package com.example.springweb.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    private static final String QUEUE_NAME = "file_limit_queue_name";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String msg) {
        System.out.println("【生产者】发送消息：" + msg);
        rabbitTemplate.convertAndSend(QUEUE_NAME, msg);
    }
}
