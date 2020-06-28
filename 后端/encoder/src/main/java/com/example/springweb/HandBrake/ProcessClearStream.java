package com.example.springweb.HandBrake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author xdwang
 *
 * @ceate 2012-7-20 下午22:52:25
 *
 * @email xdwangiflytek@gmail.com
 *
 * @description 清除process里的InputStream和ErrorStream 缓存
 *
 */
public class ProcessClearStream extends Thread {
    private InputStream inputStream;
    private String type;
    private Process pro;
    ProcessClearStream(InputStream inputStream, String type, Process pro) {
        this.inputStream = inputStream;
        this.type = type;
        this.pro = pro;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            // 打印信息
            String line = null;
            while ((line = br.readLine()) != null) {
                //System.out.println(type + ">" + line);
                if(line.compareTo("HandBrake has exited.")==0)
                {
                    pro.destroy();
                    break;
                }
            }
            // 不打印信息
//           while (br.readLine() != null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}