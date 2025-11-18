package com.github.Blankal;

import com.github.Blankal.GenerateText;    // Generate Text based off Text and/or Image Input
import com.github.Blankal.ScreenCapture;    // Screenshot Frames for Feeding into AI
import com.github.Blankal.config;


public class Main {
    static final String MODEL_TYPE = config.MODEL_TYPE;  // Use to set GOOGLE model type for generation
    static final String IMAGE_TYPE = config.IMAGE_TYPE;
    public static void main(String[] args)
    {
        try
        {
            Thread.sleep(2000);
            GenerateText.generateFeedback(
                MODEL_TYPE,
                "How can I make images sent to you clearer? Should I take it out of a base64 format and convert to jpeg or something?" 
                //ScreenCapture.getFrame()
            );
        }
        catch(Exception e)
        {
            System.out.println("Error in main loop: " + e);
        }
    }
}
