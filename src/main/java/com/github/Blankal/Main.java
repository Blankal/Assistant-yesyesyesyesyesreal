package com.github.Blankal;

import com.github.Blankal.GeminiAPI;  // Generate text based off text and/or image input (Google)
import com.github.Blankal.OpenAI_API;  // Generate Text based off Text and/or image input (OpenAI)
import com.github.Blankal.OmniRequest;  // Used to let the AI model see via a Json array
import static com.github.Blankal.ScreenCapture.getFrame;  // Generates base64 screenshots to be analyzed by OmniParse
import static com.github.Blankal.config.getModelType;
import static com.github.Blankal.config.getInstructions;
import static com.github.Blankal.config.getModelBrand;

public class Main 
{
    // Instructions for task you want done
    public static String prompt = " Please describe the context of the image. Please respond in JSON format.";
    public static void main(String[] args)
    {
        // Super combo of all instructions
        String promptPayload = getInstructions() + " " + prompt + " " + OmniRequest.getOmniParseOutput(getFrame());
        try
        {
            OpenAI_API.generateStaticFeedback(promptPayload);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Error in main loop: " + e);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
