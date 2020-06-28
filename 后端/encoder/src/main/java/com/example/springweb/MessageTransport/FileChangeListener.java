package com.example.springweb.MessageTransport;

import com.example.springweb.HandBrake.FileDelete;
import com.example.springweb.HandBrake.handbrake;
import com.example.springweb.dao.VideoService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class FileChangeListener {
    @Autowired
    VideoService videoService;

    private static final String QUEUE_NAME = "file_limit_queue_name";
    private static final String DELETE_NAME = "delete_limit_queue_name";
    int limit = 2;
    @RabbitListener(queues = QUEUE_NAME)
    public void fileChange(String in, Message message, Channel channel) throws InterruptedException, IOException {
        channel.basicQos(limit,true);
        String[] event = in.split(" ");
        System.out.println("In Listener: " + event[0]);
        System.out.println("In Listener: " + event[1]);
        if(event[1].compareTo("ENTRY_CREATE") == 0) {
            handbrake hb = new handbrake(event[0],videoService, channel,message);
            hb.start();
            //hb.join();
        }
        else
        {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
    @RabbitListener(queues = DELETE_NAME)
    public void fileDelete(String in,Message message, Channel channel) throws IOException {
        String[] event = in.split(" ");
        new FileDelete().Delete(event[0]);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
