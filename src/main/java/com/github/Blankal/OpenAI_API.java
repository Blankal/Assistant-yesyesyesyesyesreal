package com.github.Blankal;
import com.openai.client.OpenAIClient;  // Instantiate Client object
import com.openai.client.okhttp.OpenAIOkHttpClient;  // Instantiate Client object
import com.openai.models.realtime.RealtimeResponseCreateParams;  // For real time generation
import com.openai.models.responses.Response;  // Semi-real-time response handling
import com.openai.models.responses.ResponseCreateParams;  // Response param creation
import com.openai.models.chat.completions.messages.*;  // For picking messages from response list

public class OpenAI_API 
{
    // Set key from env
    private static String OPENAI_API_KEY;
    private static OpenAIClient client;
    static
    {
        try
        {
            OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");
        }
        catch(Exception e)
        {
            System.out.println("Failed to obtain OPENAI_API_KEY from environment variables: " + e);
            OPENAI_API_KEY = null;
        }
    }
    
    static
    {
        if(OPENAI_API_KEY != null)
        {
            try
            {
                final OpenAIClient client = OpenAIOkHttpClient.builder()
                    .apiKey(OPENAI_API_KEY)
                    .build();
            }
            catch(Exception e)
            {
                System.out.println("Could not establish client: " + e);
            }
        }
    }

    /**
     * Generates feedback from OpenAI API using text only.
     * @param model Which model to use
     * @param prompt Text prompt for the AI
     * @return AI-generated response as a String
     */
    public static String generateFeedback(String model, String prompt)
    {
        ResponseCreateParams params = ResponseCreateParams.builder()
            .input(prompt)
            .model(model)
            .build();
        Response chatCompletion = client.responses().create(params);  // Returns a list of choices of outputs
        String message = chatCompletion._text().toString();  // Is thsi right???
        return message;
    }

        
}
