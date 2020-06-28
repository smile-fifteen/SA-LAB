package com.example.springweb.HandBrake;

import com.example.springweb.dao.VideoService;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;

import java.io.*;
import java.nio.file.Files;

public class handbrake extends Thread {
    String filename;
    String videoPath;
    String exe;
    String raw;
    String encoder;
    String[] p;
    String[] X;
    String[] Y;
    VideoService videoService;
    Channel channel;
    Message message;
    public handbrake(String filename, VideoService videoService, Channel channel, Message message)
    {
        this.channel = channel;
        this.message = message;
        this.filename = filename;
        videoPath = "X:\\bin\\video";
        exe = "D:\\handbrakecli\\HandBrakeCLI";
        raw = "\\raw";
        encoder = " --encoder-preset Ultrafast";
        p = new String[]{"\\360P", "\\720P", "\\1080P"};
        X = new String[]{" -X 480"," -X 1280"," -X 1920"};
        Y = new String[]{" -Y 360"," -Y 720"," -Y 1080"};
        this.videoService = videoService;
    }
    @Override
    public void run()
    {

        int level = -1;
        try {
            level = videoService.getLevel(filename.split("\\.")[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(level==-1) {
            System.out.println("level initial failed");
            return;
        }
        System.out.println("Copying...");
        File source = new File(videoPath+raw+"\\"+filename);
        File dst = new File(videoPath+p[level]+"\\"+filename);
        try {
            Files.copy(source.toPath(),dst.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FileInputStream in = null;
//        FileOutputStream out = null;
//        try {
//            in = new FileInputStream(source);
//            out = new FileOutputStream(dst);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            //assert out != null;
//            out.write(in.readAllBytes());
//            out.close();
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for(int i = level-1; i>= 0; i--)
        {
            System.out.println("encoding: " + p[i].substring(1));
            String input = videoPath+raw+"\\"+filename;
            String output = videoPath + p[i]+"\\"+filename;
            String command = exe + " -i " + input + encoder + X[i] + Y[i] + " -o " + output;
            //command = exe + " -v";
            System.out.println(command);
            //command = exe + " -v";
            Process pro = null;
            try {
                pro = Runtime.getRuntime().exec("cmd.exe /k " + command);
                new ProcessClearStream(pro.getInputStream(), "INFO", pro).start();
               new ProcessClearStream(pro.getErrorStream(), "ERROR", pro).start();
                pro.waitFor();
                System.out.println(pro.exitValue());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
