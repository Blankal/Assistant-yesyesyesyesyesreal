package com.github.Blankal;

import com.github.Blankal.GeminiAPI;  // Generate text based off text and/or image input (Google)
import com.github.Blankal.OpenAI_API;  // Generate Text based off Text and/or image input (OpenAI)
import com.github.Blankal.OmniRequest;  // Used to let the AI model see via a Json array
import com.github.Blankal.Tools;  // Tools for the ai to interact with computer
import java.util.Scanner;
import static com.github.Blankal.ScreenCapture.getFrame;  // Generates base64 screenshots to be analyzed by OmniParse
import static com.github.Blankal.config.getModelType;
import static com.github.Blankal.config.getUserPrompt;
import static com.github.Blankal.config.getSystemPrompt;
import static com.github.Blankal.config.getInstructions;
import static com.github.Blankal.config.getModelBrand;

public class Main 
{

    public static void OpenAI_Task_Resolver(String prompt, int maxLoops)
    {
        try
        {
           Tools tool = new Tools(OpenAI_API.generateStaticFeedback(prompt));

            
            if(maxLoops > 0)
            {
                maxLoops -= 1;
                OpenAI_Task_Resolver(prompt, maxLoops);
                System.out.println("\n\n\n");
            }
        }
        catch (Exception e){ System.out.println("OpenAI_Task_Resolver_Error: " + e); }
    }
    
    public static void main(String[] args)
    {
        try
        {
            OpenAI_API.generateStaticFeedback(getUserPrompt());
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
