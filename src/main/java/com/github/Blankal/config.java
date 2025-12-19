package com.github.Blankal;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import static com.github.Blankal.ScreenCapture.getFrame;

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
    private static final String USER_PROMPT = "Please open youtube.";
    private static final int[] SCREEN_DIMENSIONS = { Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height };  // Only reads monitor 1 :(
    private static final String IMAGE_TYPE = "png";  // Use to set image type for screenshot(Lossy formats may mess with AI vision)
    private static final String SYSTEM_PROMPT =  // Instructions used to orient the model. DO NOT CHANGE unless you know what you're doing.
        "You are an autonomous computer-control agent that can call tools; respond with ONLY one JSON object that matches the enforced schema (no markdown, no extra text, no extra keys). Put at most ONE tool call per turn inside the JSON field \"action\" using exactly one tool name and the exact argument type; if no tool is needed set \"action\":{} and continue updating state fields. Never invent tools or screen elements; use only ToolList and ScreenElements provided. Prefer urlBrowse for known URLs; use browse only for search queries; use wait after navigation when needed.";
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


public static JsonObject getTools()
    {

        // root (object)
        // └── ToolList (array)
        //     ├── [0] (object)
        //     │   ├── name (string) = "browse"
        //     │   └── description (string)
        //     ├── [1] (object)
        //     │   ├── name (string) = "inputText"
        //     │   └── description (string)
        //     ├── [2] (object)
        //     │   ├── name (string) = "browseYoutube"
        //     │   └── description (string)
        //     ├── [3] (object)
        //     │   ├── name (string) = "moveMouse"
        //     │   └── description (string)
        //     ├── [4] (object)
        //     │   ├── name (string) = "holdMouse"
        //     │   └── description (string)
        //     ├── [5] (object)
        //     │   ├── name (string) = "releaseMouse"
        //     │   └── description (string)
        //     ├── [6] (object)
        //     │   ├── name (string) = "scrollMouse"
        //     │   └── description (string)
        //     ├── [7] (object)
        //     │   ├── name (string) = "clickMouse"
        //     │   └── description (string)
        //     ├── [8] (object)
        //     │   ├── name (string) = "urlBrowse"
        //     │   └── description (string)
        //     ├── [9] (object)
        //     │   ├── name (string) = "message"
        //     │   └── description (string)
        //     └── [10] (object)
        //         ├── name (string) = "wait"
        //         └── description (string)

        JsonObject tools = new JsonObject();
        JsonArray toolList = new JsonArray();

        JsonObject browse = new JsonObject();
        browse.addProperty("name", "browse");
        browse.addProperty("description", "String input allowed. UTF-8 encoding only. USE IF DO NOT HAVE ACCESS TO THE INTERNET, allows to search for things on the web");
        toolList.add(browse);

        JsonObject inputText = new JsonObject();
        inputText.addProperty("name", "inputText");
        inputText.addProperty("description", "String input allowed. UTF-8 encoding only, used to type at cursor position");
        toolList.add(inputText);

        JsonObject browseYoutube = new JsonObject();
        browseYoutube.addProperty("name", "browseYoutube");
        browseYoutube.addProperty("description", "String input allowed. UTF-8 encoding only. USE IF DO NOT HAVE ACCESS TO THE INTERNET, open youtube");
        toolList.add(browseYoutube);

        JsonObject moveMouse = new JsonObject();
        moveMouse.addProperty("name", "moveMouse");
        moveMouse.addProperty("description", "STRICTLY coordinates [x,y], moves mouse to coordinates");
        toolList.add(moveMouse);

        JsonObject holdMouse = new JsonObject();
        holdMouse.addProperty("name", "holdMouse");
        holdMouse.addProperty("description", "STRICTLY 'left', 'right', 'middle', holds chosen mouse button");
        toolList.add(holdMouse);

        JsonObject releaseMouse = new JsonObject();
        releaseMouse.addProperty("name", "releaseMouse");
        releaseMouse.addProperty("description", "STRICTLY 'left', 'right', 'middle', stops holding chosen mouse button");
        toolList.add(releaseMouse);

        JsonObject scrollMouse = new JsonObject();
        scrollMouse.addProperty("name", "scrollMouse");
        scrollMouse.addProperty("description", "STRICTLY integer. Positive Integer Input to scroll mouse down. Negative Integer Input to scroll mouse up");
        toolList.add(scrollMouse);

        JsonObject clickMouse = new JsonObject();
        clickMouse.addProperty("name", "clickMouse");
        clickMouse.addProperty("description", "STRICTLY 'left', 'right' or 'middle'. Clicks mouse button of choice");
        toolList.add(clickMouse);

        JsonObject urlBrowse = new JsonObject();
        urlBrowse.addProperty("name", "urlBrowse");
        urlBrowse.addProperty("description", "URL STRICTLY. String input. UTF-8 encoding only. USE TO OPEN A SPECIFIC URL");
        toolList.add(urlBrowse);

        JsonObject message = new JsonObject();
        message.addProperty("name", "message");
        message.addProperty("description", "Input message to be printed for user awareness and communication. STRICTLY used to print toDO status and message to user.");
        toolList.add(message);

        JsonObject wait = new JsonObject();
        wait.addProperty("name", "wait");
        wait.addProperty("description", "integer input in milliseconds. USE TO MAKE PROGRAM REST FOR SPECIFIC AMOUNT OF TIME, like loading web pages.");
        toolList.add(wait);

        tools.add("ToolList", toolList);

        return tools;
    }

    /**
     * 
     * @param prompt Prompt for mega string
     * @return JsonObject with full input for specifically OpenAI prompting
     */
    public static JsonObject getInstructions(String prompt)
    {


        // getInstructions(prompt) returns: jsonPayload (object)
        // ├── model (string) = getModelType()                       [web:121]
        // ├── prompt (string) = prompt + "..." + getTools() + "..." [web:121]
        // ├── stream (boolean) = false                              [web:121]
        // └── format (object) = schema                              [web:121]
        //     ├── type (string) = "object"
        //     ├── properties (object)
        //     │   ├── context (object)
        //     │   │   └── type (string) = "string"
        //     │   ├── "" (object)   <-- empty property name (from properties.add("", historyItem))
        //     │   │   └── type (string) = "string"
        //     │   ├── CompletedStepsList (object)
        //     │   │   ├── type (string) = "string"   <-- comment says String[] but schema says string
        //     │   │   └── items (object)
        //     │   │       └── type (string) = "string"
        //     │   └── taskCompleted (object)
        //     │       └── type (string) = "boolean"
        //     ├── required (array)
        //     │   ├── "context"
        //     │   ├── "history"
        //     │   ├── "completedStepsList"
        //     │   ├── "taskToDoList"
        //     │   └── "taskCompleted"
        //     └── additionalProperties (string) = "false"  <-- should be boolean false in JSON 

        // AI Instructions List
        try{
            JsonObject jsonPayload = new JsonObject();
            jsonPayload.addProperty("model", getModelType());
            jsonPayload.addProperty("prompt", getSystemPrompt() +  prompt + ". Here are the tools you have access to:" + getTools().toString() + ". Here is a JSON of all of the elements on screen:" + OmniRequest.getOmniParseOutput(getFrame()) + "\nReturn ONLY valid JSON matching the provided schema. No extra text.");
            jsonPayload.addProperty("stream", false);

            // AI Schema (Required response data structure)
            JsonObject schema = new JsonObject();
            schema.addProperty("type","object");

            // Properties
            JsonObject properties = new JsonObject();

            // Context
            JsonObject context = new JsonObject();
            context.addProperty("type","string");
            properties.add("context", context);

            // History
            JsonObject history = new JsonObject();
            history.addProperty("type", "string");  // String[]
            JsonObject historyItem = new JsonObject();
            historyItem.addProperty("type", "string");
            history.add("History", historyItem);

            properties.add("", historyItem);

            
            // Last completed task
            JsonObject completedStepsList = new JsonObject();
            completedStepsList.addProperty("type","string");  // String[]
            JsonObject completedSteps = new JsonObject();
            completedSteps.addProperty("type", "string");
            completedStepsList.add("items", completedSteps);

            properties.add("CompletedStepsList",completedStepsList);

            // To-Do List
            JsonObject taskToDoList = new JsonObject();
            taskToDoList.addProperty("type","array");
            JsonObject taskToDo = new JsonObject();
            taskToDo.addProperty("type","object");

            JsonObject taskToDoProperties = new JsonObject();

            // Status (sub group of To-Do)
            JsonObject status = new JsonObject();
            status.addProperty("type","string");
            JsonArray statusTypes = new JsonArray();
            statusTypes.add("not started");
            statusTypes.add("in progress");
            statusTypes.add("complete");
            status.add("types", statusTypes);
            taskToDoProperties.add("status", status);

            // Task Fulfillment Requirements (Sub group of Statuses)
            JsonArray toDoRequirements = new JsonArray();
            toDoRequirements.add("task");
            toDoRequirements.add("status");
            taskToDo.add("required", toDoRequirements);

            // Stop extra keys in toDo items
            taskToDo.addProperty("additionalProperties", false);

            // Task Complete
            JsonObject taskCompleted = new JsonObject();
            taskCompleted.addProperty("type","boolean");
            properties.add("taskCompleted",taskCompleted);

            schema.add("properties", properties);

            // Final Output Requirements
            JsonArray requirements = new JsonArray();
            requirements.add("context");
            requirements.add("history");
            requirements.add("completedStepsList");
            requirements.add("taskToDoList");
            requirements.add("taskCompleted");
            schema.add("required",requirements);

            // Final Schema
            schema.addProperty("additionalProperties", "false");
            jsonPayload.add("format", schema);

            return jsonPayload;
        }
        catch(Exception e)
        {
            System.out.println("ERROR GETTING INSTRUCTIONS" + e);
            return new JsonObject();
        }
    }
}
