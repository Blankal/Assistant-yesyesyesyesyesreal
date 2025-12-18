package com.github.Blankal;
import java.util.HashMap;
import java.util.Map;
import java.awt.Toolkit;

public class config {

    /* 
        - Non-local models require keys, REMEMBER TO KEEP KEYS PRIVATE and that you have to set your ENV variables properly to use them
        - Free keys may be available with limited usage from OpenAI and Google on their websites
        Free models we have tested so far include:
            OpenAI: llama3.2:11b, LLaVA
            Google: gemini-1.5-flash, gemini-2.5-flash
        But you could always try to use a paid key with a larger model for faster, smarter results
    */

    private static final String MODEL_TYPE = "llama3.1";  // ex: "gemini-2.5-flash, llama3.2"
    private static final String MODEL_BRAND = "OpenAI";  // ex: "Google", "OpenAI"
    private static final String USER_PROMPT = "Please use the function to bring me to youtube";
    private static final int[] SCREEN_DIMENSIONS = { Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height };  // Only reads monitor 1 :(
    private static final String IMAGE_TYPE = "png";  // Use to set image type for screenshot(Lossy formats may mess with AI vision)
    private static final String SYSTEM_PROMPT =  // Instructions used to orient the model. DO NOT CHANGE unless you know what you're doing.
        "You are a planner/agent that must respond with exactly ONE valid JSON object and nothing else (no markdown, no code fences, no explanation). The JSON must match this schema and key spelling exactly: {\"context\":string,\"completedTasks\":string[],\"toDo\":[{\"task\":string,\"status\":\"not started\"|\"in progress\"|\"done\",\"details\"?:string}],\"KeyInformation\":{\"due date\":string,\"word count\":string,\"formatting requirements\":string},\"ToolList\":[{\"name\":string,\"description\":string}],\"actionHistory\":object[],\"action\":object,\"WhatToDoNext\":string,\"ScreenElements\":string,\"Finished\":boolean}. Rules: Output MUST be valid JSON (double quotes, no trailing commas). Include every top-level key shown in the schema, even if empty (use [] or \"\" or {}). Do not invent tools: ToolList must only contain tools provided by the user input. action must only use tool names from ToolList as keys. If required information is missing, set the relevant value to \"\" (or []), set \"Finished\" to false, and set \"WhatToDoNext\" to a concrete step to obtain the missing info.";
    private static final String OMNI_PARSER_ADDRESS = "http://127.0.0.1:8001/";  // Probably don't change this as it has to match the same address as omniparserserver.py's version

    // CONFIG GETTERS
    public static String getModelType()
    {
        return MODEL_TYPE;
    }
    public static String getModelBrand()
    {
        return MODEL_BRAND;
    }
    public static final String getUserPrompt()
    {
        return USER_PROMPT;
    }
    public static int[] getScreenDimensions()
    {
        return SCREEN_DIMENSIONS;
    }
    public static String getImageType()
    {
        return IMAGE_TYPE;
    }
    public static String getSystemPrompt()
    {
        return SYSTEM_PROMPT;
    }
    public static String getOmniParserAddress()
    {
        return OMNI_PARSER_ADDRESS;
    }
}
