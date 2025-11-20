package com.github.Blankal;

import static com.github.Blankal.GeminiAPI.generateFeedback;    // Generate Text based off Text and/or Image Input
import static com.github.Blankal.ScreenCapture.getFrame;    // Screenshot Frames for Feeding into AI
import static com.github.Blankal.config.MODEL_TYPE;  // AI Model


public class Main {
    public static void main(String[] args)
    {
        try
        {
            Thread.sleep(5000);
            generateFeedback(
                MODEL_TYPE,
                "What can you read and see in this picture?  Please include coordinates of any objects identified." ,
                getFrame()
            );
        }
        catch(Exception e)
        {
            System.out.println("Error in main loop: " + e.getMessage());
        }
    }
}
