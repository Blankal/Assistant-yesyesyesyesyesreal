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

    // Non-local models require keys
    private static final String MODEL_TYPE = "llama3.2-vision:11b";  // ex: "gemini-2.5-flash" or LOCAL_MODELS.get("LLaVA")
    private static final String MODEL_BRAND = "OpenAI";  // ex: "Google", "OpenAI"
    private static final String IMAGE_TYPE = "png";  // Use to set image type for screenshot(Lossy formats may mess with AI vision)
    private static final String INSTRUCTIONS = 
        "the following image is the screenshot of a computer screen. Step by step, carefully analyze the content of the image and provide STRICTED RULE a structured JSON text (captions, detected elements, text) ";
    private static final String CONDA_PATH = "C:/Users/caleb/miniconda3/conda.exe";  // Path to conda exe
    private static final String CONDA_ADDRESS = "127.0.0.1";  // IP for conda local server
    private static final String CONDA_PORT = "8000";  // Port for conda local server

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
    public static String getCondaPath()
    {
        return CONDA_PATH;
    }
    public static String getCondaAddress()
    {
        return CONDA_ADDRESS;
    }
    public static String getCondaPort()
    {
        return CONDA_PORT;
    }
}
