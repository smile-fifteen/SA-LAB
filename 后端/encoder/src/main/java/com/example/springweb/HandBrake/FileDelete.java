package com.example.springweb.HandBrake;

import java.io.File;

public class FileDelete
{
    public void Delete(String filename)
    {
        //System.out.println(filename);
        String videopath = "X:\\bin\\video";
        String imagepath = "X:\\bin\\image";
        File raw = new File(videopath + "\\360P\\"+filename);
        if(raw.exists()) {
            System.out.println("delete: " + raw);
            raw.delete();
        }
        raw = new File(videopath + "\\720P\\"+filename);
        if(raw.exists()) {
            System.out.println("delete: " + raw);
            raw.delete();
        }
        raw = new File(videopath + "\\1080P\\"+filename);
        if(raw.exists()) {
            System.out.println("delete: " + raw);
            raw.delete();
        }
        String imgname = filename.split("\\.")[0];
        File img = new File(imagepath+"\\"+imgname+".jpg");
        if(img.exists())
        {
            System.out.println("delete: " + img);
            img.delete();
        }
        else
        {
            System.out.println("delete: " + img);
            img = new File(imagepath+"\\"+imgname+".png");
            img.delete();
        }
    }
}
