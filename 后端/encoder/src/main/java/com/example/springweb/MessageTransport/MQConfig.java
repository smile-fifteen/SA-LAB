package com.example.springweb.MessageTransport;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    private static final String QUEUE_NAME = "file_limit_queue_name";
    private static final String DELETE_NAME = "delete_limit_queue_name";
    @Bean
    public Queue fileQueue()
    {
        return new Queue(QUEUE_NAME);
    }
    @Bean
    public Queue deleteQueue()
    {
        return new Queue(DELETE_NAME);
    }
}
