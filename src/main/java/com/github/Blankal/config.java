package com.github.Blankal;
import java.util.HashMap;
import java.util.Map;

public class config {

    
    private static final Map<String, String> LOCAL_MODELS = new HashMap<String, String>()
    {
        {
            put("LLaVA", "assistant-yayyyy/src/main/java/com/localAgents/llava");
            put("OLLAMA-3.2", "assistant-yayyyy/src/main/java/com/localAgents/OLLAMA-3.2");
        }
    };

    private static final String MODEL_TYPE = LOCAL_MODELS.get("LLaVA");  // ex: "gemini-2.5-flash" or LOCAL_MODELS.get("LLaVA")
    private static final String MODEL_BRAND = "OpenAI";  // ex: "Google", "OpenAI"
    private static final String IMAGE_TYPE = "png";  // Use to set image type for screenshot(Lossy formats may affect AI vision)
    private static final String INSTRUCTIONS = 
        "The following is a picture of the computer screen in base64 format. The picture is supposed to represent the current state of the screen. Analyze the image and provide a detailed description of its contents, including any text, objects, or notable features you can identify. If there are multiple items, please list them with their coordinates on the screen. Be as specific as possible in your description.\n\n";

    public static String getModelType()
    {
        return MODEL_TYPE;
    }
    public static String getModelBrand()
    {
        return MODEL_BRAND;
    }
    public static String getImageType()
    {
        return IMAGE_TYPE;
    }
    public static String getInstructions()
    {
        return INSTRUCTIONS;
    }
}
