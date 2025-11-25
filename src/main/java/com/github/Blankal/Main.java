package com.github.Blankal;

import com.github.Blankal.GeminiAPI;  // Generate text based off text and/or image input (Google)
import com.github.Blankal.OpenAI_API;  // Generate Text based off Text and/or image input (OpenAI)
import static com.github.Blankal.ScreenCapture.getFrame;  // Screenshot Frames for Feeding into AI
import static com.github.Blankal.config.getModelType;
import static com.github.Blankal.config.getModelBrand;
import static com.github.Blankal.Tools.*;


public class Main {
    public static void main(String[] args)
    {
        try
        {
            Thread.sleep(5000);  // Debug so you have time to leave VSCode before screenshot occurs
            switch(getModelBrand().toLowerCase())  // Parse choice of AI brand to verify that Agents use proper API
            {
                case "google" ->
                    GeminiAPI.generateFeedback(
                        getModelType(),
                        "Create an example os the json dataset, json dataset only and nothing else" ,
                        getFrame()
                    );
                case "openai", "open ai" -> 
                    OpenAI_API.generateFeedback(
                        getModelType(), 
                        "filler input"
                        );
                default ->
                throw new IllegalArgumentException("Invalid brand chosen");
            }
        }
        catch(Exception e)
        //    Tools tools = new Tools("");
            
            System.out.println("Error in main loop: " + e.getMessage());
            System.out.println(e);
            System.out.print(e.toString());

        }
    }
}
