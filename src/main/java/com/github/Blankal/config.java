package com.github.Blankal;
import java.util.HashMap;
import java.util.Map;

public class config {

    // Key - Value pairs for supported local models (when installing your own, best practice is to add them yourself here)
    private static final Map<String, String> LOCAL_MODELS = new HashMap<String, String>()
    {
        {
            put("LLaVA", "assistant-yayyyy/src/main/java/com/localAgents/llava");
            put("OLLAMA-3.2", "assistant-yayyyy/src/main/java/com/localAgents/OLLAMA-3.2");
        }
    };

    /* 
        - Non-local models require keys, REMEMBER TO KEEP KEYS PRIVATE and that you have to set your ENV variables properly to use them
        - Free keys may be available with limited usage from OpenAI and Google on their websites
        Free models we have tested so far include:
            OpenAI: llama3.2:11b, LLaVA
            Google: gemini-1.5-flash, gemini-2.5-flash
        But you could always try to use a paid key with a larger model for faster, smarter results
    */

    private static final String MODEL_TYPE = "llama3.2-vision:11b";  // ex: "gemini-2.5-flash" or LOCAL_MODELS.get("LLaVA")
    private static final String MODEL_BRAND = "OpenAI";  // ex: "Google", "OpenAI"
    private static final String IMAGE_TYPE = "png";  // Use to set image type for screenshot(Lossy formats may mess with AI vision)
    private static final String INSTRUCTIONS =  // Instructions used to test model, final product will allow dynamic input via voice or GUI.
        "the following image is the screenshot of a computer screen. Step by step, carefully analyze the content of the image and provide STRICTED RULE a structured JSON text (captions, detected elements, text) ";
    
    // Conda config not yet implemented, no need to change these for now.
    private static final String CONDA_PATH = "C:/Users/caleb/miniconda3/conda.exe";  // Path to conda exe
    private static final String CONDA_ADDRESS = "127.0.0.1";  // IP for conda local server
    private static final String CONDA_PORT = "8000";  // Port for conda local server

    // CONFIG GETTERS
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
