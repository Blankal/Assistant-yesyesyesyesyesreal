package com.github.Blankal;

import org.opencv.videoio.VideoCapture;

public class ScreenCapture
{
    static boolean camIsActive = false;
    public static void main(String[] args)
    {
        VideoCapture cap;
        try{
            cap = new VideoCapture(0);
            camIsActive = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(cap != null)
        {
            System.out.println("Camera Opened");
        }
        else
        {
            return -1;
        }
    }

}