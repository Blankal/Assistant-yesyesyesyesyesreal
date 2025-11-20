package com.github.Blankal;
import com.openai.client.OpenAIClient;  // AI Client
import com.openai.models.ChatModel;  // Chat Model
import com.openai.models.responses.Response;  // Respone Handling
import com.openai.models.responses.ResponseCreateParams;  // Response Param Creation
import static com.github.Blankal.config.MODEL_BRAND;
import static com.github.Blankal.config.MODEL_TYPE;
import static com.github.Blankal.config.LLaVA_PATH;

public class OpenAI_API 
{
    private static final String[] OPENAI_MODEL_LIST = new String[]{
        LLaVA_PATH
    };

    private static String OPENAI_API_KEY;
    static{
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

    public static String generateFeedback(String model, String prompt)
    {
        return "placeholder";
    }

        
}
