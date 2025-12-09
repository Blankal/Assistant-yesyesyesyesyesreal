package com.github.Blankal;

import com.github.Blankal.GeminiAPI;  // Generate text based off text and/or image input (Google)
import com.github.Blankal.OpenAI_API;  // Generate Text based off Text and/or image input (OpenAI)
import static com.github.Blankal.ScreenCapture.getFrame;  // Screenshot Frames for Feeding into AI
import static com.github.Blankal.config.getModelType;
import static com.github.Blankal.config.getModelBrand;


public class Main {
    public static void main(String[] args)
    {
        try
        {
            Thread.sleep(5000);  // Debug so you have time to leave VSCode before screenshot occurs
            switch(getModelBrand().toLowerCase())  // Parse choice of AI brand to verify that Agents use proper API
            {
                case "google" -> 
                    GeminiAPI.generateStaticFeedback(
                        getModelType(),
                        "What can you read and see in this picture?  Please include coordinates of any objects identified." ,
                        getFrame()  
                    );
                case "openai", "open ai" ->
                    OpenAI_API.generateStaticFeedback( 
                        "provide Strict analyzises of images in JSON format with captions, detected elements, and text. Include coordinates of detected elements.",
                        getFrame()
                        );
                default -> throw new IllegalArgumentException("Invalid brand chosen");
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Error in main loop: " + e.toString());
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
