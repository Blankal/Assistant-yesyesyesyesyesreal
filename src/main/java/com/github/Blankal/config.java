package com.github.Blankal;
import java.util.HashMap;
import java.util.Map;
import java.awt.Toolkit;

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

    private static final String MODEL_TYPE = "llama3.2";  // ex: "gemini-2.5-flash" or LOCAL_MODELS.get("LLaVA")
    private static final String MODEL_BRAND = "OpenAI";  // ex: "Google", "OpenAI"
    private static final int[] SCREEN_DIMENSIONS = { Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height };  // Only reads monitor 1 :(
    private static final String IMAGE_TYPE = "png";  // Use to set image type for screenshot(Lossy formats may mess with AI vision)
    private static final String INSTRUCTIONS =  // Instructions used to orient the model. DO NOT CHANGE unless you know what you're doing.
        "You are given all necessary context: the input is a JSON array of UI elements detected from a screenshot of the current computer screen (OmniParser output) with added mouseClickCoordinate for clicking; treat it as screen UI data and do not ask for more context or clarification—if a task cannot be completed from the JSON and the user’s instruction alone, output exactly INSUFFICIENT_INFORMATION followed by a short list of what specific missing detail is required; each element may include type, bbox ([x1,y1,x2,y2] pixels), interactive (true/false), content, and mouseClickCoordinate ([x,y] pixels); use only these fields, do not invent elements/coordinates, and follow the user’s task strictly without extra commentary.";
    private static final String OMNI_PARSER_ADDRESS = "http://127.0.0.1:8001/";  // Can only be changed from "omniparserserver.py" file

    // CONFIG GETTERS
    public static String getModelType()
    {
        return MODEL_TYPE;
    }
    public static String getModelBrand()
    {
        return MODEL_BRAND;
    }
    public static int[] getScreenDimensions()
    {
        return SCREEN_DIMENSIONS;
    }
    public static String getImageType()
    {
        return IMAGE_TYPE;
    }
    public static String getInstructions()
    {
        return INSTRUCTIONS;
    }
    public static String getOmniParserAddress()
    {
        return OMNI_PARSER_ADDRESS;
    }
}
