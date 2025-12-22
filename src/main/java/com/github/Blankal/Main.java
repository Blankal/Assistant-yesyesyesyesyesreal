package com.github.Blankal;

public class Main 
{

    public static void OpenAI_Task_Resolver(String prompt, int maxLoops)
    {
        try
        {
        OpenAI_API.generateStaticFeedback();
           System.out.println("fuckyeah it works in here");

            
            if(maxLoops > 0)
        {
            System.out.println("fuckyeah it works in here");
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
          OpenAI_API.generateStaticFeedback();
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
