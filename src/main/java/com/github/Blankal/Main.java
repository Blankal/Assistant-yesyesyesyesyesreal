package com.github.Blankal;

import com.github.Blankal.GenerateText;    // Generate Text based off Text and/or Image Input
import com.github.Blankal.ScreenCapture;    // Screenshot Frames for Feeding into AI

public class Main {
    static final String MODEL_TYPE = "gemini-2.5-flash";  // Use to set model type for generation
    static final String IMAGE_TYPE = "jpeg";
    public static void main(String[] args)
    {
        try
        {
            Thread.sleep(2000);
            GenerateText.generateFeedback(MODEL_TYPE,"After processing and decoding, what can you see from this image?", ScreenCapture.getFrame());
        }
        catch(Exception e)
        {
            System.out.println("Error in main loop: " + e);
        }
    }
}
