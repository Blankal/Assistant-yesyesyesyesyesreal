package com.github.Blankal;

import java.awt.Toolkit;

import static com.github.Blankal.OmniRequest.getOmniParseOutput;
import static com.github.Blankal.ScreenCapture.getFrame;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class config {

    private static final String MODEL_TYPE = "llama3.1";  // ex: "gemini-2.5-flash, llama3.2"
    private static final String MODEL_BRAND = "OpenAI";   // ex: "Google", "OpenAI"
    private static final String USER_PROMPT = "||| USER PROMPT :" +" move a mouse in the middle of the screen";
    private static final String THINK_LEVEL = "high";  // choices range from "high", "medium" and "low", lower levels = dumber but less processing time
    private static final int[] SCREEN_DIMENSIONS = {
            Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height
    };
    private static final String IMAGE_TYPE = "png";
    private static final String SYSTEM_PROMPT =
            " SYSTEM PROMPT You are an autonomous computer-control agent that can call tools; respond with ONLY one JSON object that matches the enforced schema (no markdown, no extra text, no extra keys). " +
            "Put at most ONE tool call per turn inside the JSON field \"action\" using exactly one tool name and the exact argument type; " +
            "if no tool is needed set \"action\":{} and continue updating state fields. " +
            "Never invent tools or screen elements; use only ToolList and ScreenElements provided. " +
            "Prefer urlBrowse for known URLs; use browse only for search queries; use wait after navigation when needed.|||"+
            "This is a JSON of all the elements currnetly on the screen -->> "+ getOmniParseOutput(getFrame());

    private static final String OMNI_PARSER_ADDRESS = "http://127.0.0.1:8001/";

    // CONFIG GETTERS
    public static String getModelType() { return MODEL_TYPE; }
    public static String getModelBrand() { return MODEL_BRAND; }
    public static final String getUserPrompt() { return USER_PROMPT; }
    public static int[] getScreenDimensions() { return SCREEN_DIMENSIONS; }
    public static String getImageType() { return IMAGE_TYPE; }
    public static String getSystemPrompt() { return SYSTEM_PROMPT; }
    public static String getOmniParserAddress() { return OMNI_PARSER_ADDRESS; }
    public static String getThinkLevel() { return THINK_LEVEL; }

    /**
     * Maps all of the tools to a JSON format for ai to be fed.
     * @return Returns a JSON full of all of the tools and info about them for the AI
     */
    public static JsonArray getTools(){




        /*
        tool (one entry in the tools array)
        └── type: "function"
        └── function
            ├── name: "browse"
            ├── description: "..."
            └── parameters
                ├── type: "object"
                ├── properties
                │   └── search
                │       ├── type: "string"
                │       └── description: "..."
                └── required
                    └── [ "search" ]
        */



        JsonArray tools = new JsonArray();// will add all the tools in here
        

            // BROWSE TOOL 
            JsonObject browseToolEntry = new JsonObject();
        browseToolEntry.addProperty("type", "function");

        JsonObject browseFnObj = new JsonObject();
        browseFnObj.addProperty("name", "browse");
        browseFnObj.addProperty("description", "Search the web/browser using a plain text query (not necessarily a URL).");

        JsonObject browseParamsObj = new JsonObject();
        browseParamsObj.addProperty("type", "object");

        JsonObject browseQuerySchema = new JsonObject();
        browseQuerySchema.addProperty("type", "string");
        browseQuerySchema.addProperty("description", "Search query text.");

        JsonObject browsePropsObj = new JsonObject();
        browsePropsObj.add("search", browseQuerySchema);
        browseParamsObj.add("properties", browsePropsObj);

        JsonArray browseRequiredArr = new JsonArray();
        browseRequiredArr.add("search"); // MUST match properties key
        browseParamsObj.add("required", browseRequiredArr);

        browseFnObj.add("parameters", browseParamsObj);
        browseToolEntry.add("function", browseFnObj);

        tools.add(browseToolEntry);

        // =========================
        // openUrl (strict URL open)
        // =========================
        JsonObject openUrlToolEntry = new JsonObject();
        openUrlToolEntry.addProperty("type", "function");

        JsonObject openUrlFnObj = new JsonObject();
        openUrlFnObj.addProperty("name", "openUrl"); // IMPORTANT: unique name (not "browse")
        openUrlFnObj.addProperty("description", "Open a specific URL in the browser (must start with http:// or https://).");

        JsonObject openUrlParamsObj = new JsonObject();
        openUrlParamsObj.addProperty("type", "object");

        JsonObject urlSchema = new JsonObject();
        urlSchema.addProperty("type", "string");
        urlSchema.addProperty("description", "Full URL to open, including http:// or https://.");

        JsonObject openUrlPropsObj = new JsonObject();
        openUrlPropsObj.add("url", urlSchema);
        openUrlParamsObj.add("properties", openUrlPropsObj);

        JsonArray openUrlRequiredArr = new JsonArray();
        openUrlRequiredArr.add("url"); // MUST match properties key
        openUrlParamsObj.add("required", openUrlRequiredArr);

        openUrlFnObj.add("parameters", openUrlParamsObj);
        openUrlToolEntry.add("function", openUrlFnObj);

        tools.add(openUrlToolEntry);

        // =======================================================================================================================================================================
        // INPUT TEXT TOOL (Ollama tool schema)
        JsonObject inputText = new JsonObject();
        inputText.addProperty("type", "function");

        // function block
        JsonObject inputTextFn = new JsonObject();
        inputTextFn.addProperty("name", "inputText");
        inputTextFn.addProperty(
            "description",
            "String input allowed. UTF-8 encoding only, used to type at cursor position"
        );

        // parameters block
        JsonObject inputTextParams = new JsonObject();
        inputTextParams.addProperty("type", "object");

        // schema for the "text" argument
        JsonObject textSchema = new JsonObject();
        textSchema.addProperty("type", "string");
        textSchema.addProperty("description", "The exact text to type at the cursor position.");

        // parameters.properties must be keyed by arg name
        JsonObject inputTextProps = new JsonObject();
        inputTextProps.add("text", textSchema);
        inputTextParams.add("properties", inputTextProps);

        // parameters.required must be an array
        JsonArray inputTextRequired = new JsonArray();
        inputTextRequired.add("text");
        inputTextParams.add("required", inputTextRequired);

        inputTextFn.add("parameters", inputTextParams);
        inputText.add("function", inputTextFn);

     
        tools.add(inputText);

        // =======================================================================================================================================================================
        /// BROWSE YOUTUBE TOOL (Ollama tool schema) [page:0]
        JsonObject browseYoutubeToolEntry = new JsonObject();
        browseYoutubeToolEntry.addProperty("type", "function");

        // function block (name MUST stay browseYoutube)
        JsonObject browseYoutubeFn = new JsonObject();
        browseYoutubeFn.addProperty("name", "browseYoutube");
        browseYoutubeFn.addProperty(
            "description",
            "String input allowed. UTF-8 encoding only. Open YouTube search results for a query."
        );

        // parameters block
        JsonObject browseYoutubeParams = new JsonObject();
        browseYoutubeParams.addProperty("type", "object");

        // argument schema (argument name can be "search" or whatever you want)
        JsonObject searchSchema = new JsonObject();
        searchSchema.addProperty("type", "string");
        searchSchema.addProperty("description", "YouTube search query text.");

        // properties object keyed by arg name
        JsonObject browseYoutubeProps = new JsonObject();
        browseYoutubeProps.add("search", searchSchema);
        browseYoutubeParams.add("properties", browseYoutubeProps);

        // required array
        JsonArray browseYoutubeRequired = new JsonArray();
        browseYoutubeRequired.add("search");
        browseYoutubeParams.add("required", browseYoutubeRequired);

        // attach
        browseYoutubeFn.add("parameters", browseYoutubeParams);
        browseYoutubeToolEntry.add("function", browseYoutubeFn);

        // add to tools array
        tools.add(browseYoutubeToolEntry);
        

        // =======================================================================================================================================================================
        //moveMouse
        JsonObject moveMouseToolEntry = new JsonObject();
        moveMouseToolEntry.addProperty("type", "function");

        JsonObject moveMouseFn = new JsonObject();
        moveMouseFn.addProperty("name", "moveMouse");
        moveMouseFn.addProperty("description", "STRICTLY coordinates [x,y], moves mouse to coordinates");

        JsonObject moveMouseParams = new JsonObject();
        moveMouseParams.addProperty("type", "object");

        JsonObject coordsSchema = new JsonObject();
        coordsSchema.addProperty("type", "array");
        coordsSchema.addProperty("minItems", 2);
        coordsSchema.addProperty("maxItems", 2);
        JsonObject coordItem = new JsonObject();
        coordItem.addProperty("type", "integer");
        coordsSchema.add("items", coordItem);

        JsonObject props = new JsonObject();
        props.add("coords", coordsSchema);
        moveMouseParams.add("properties", props);

        JsonArray req = new JsonArray();
        req.add("coords");
        moveMouseParams.add("required", req);

        moveMouseFn.add("parameters", moveMouseParams);
        moveMouseToolEntry.add("function", moveMouseFn);

        tools.add(moveMouseToolEntry);

        // =======================================================================================================================================================================
        // HOLDMOUSE
        JsonObject holdMouseToolEntry = new JsonObject();
        holdMouseToolEntry.addProperty("type", "function");

        JsonObject holdMouseFn = new JsonObject();
        holdMouseFn.addProperty("name", "holdMouse");
        holdMouseFn.addProperty("description", "STRICTLY 'left', 'right', 'middle', holds chosen mouse button");

        JsonObject holdMouseParams = new JsonObject();
        holdMouseParams.addProperty("type", "object");

        JsonObject holdButtonSchema = new JsonObject();
        holdButtonSchema.addProperty("type", "string");
        holdButtonSchema.addProperty("description", "Mouse button to hold: left, right, or middle.");

        JsonArray holdButtonEnum = new JsonArray();
        holdButtonEnum.add("left");
        holdButtonEnum.add("right");
        holdButtonEnum.add("middle");
        holdButtonSchema.add("enum", holdButtonEnum);

        JsonObject holdMouseProps = new JsonObject();
        holdMouseProps.add("button", holdButtonSchema);
        holdMouseParams.add("properties", holdMouseProps);

        JsonArray holdMouseRequired = new JsonArray();
        holdMouseRequired.add("button");
        holdMouseParams.add("required", holdMouseRequired);

        holdMouseFn.add("parameters", holdMouseParams);
        holdMouseToolEntry.add("function", holdMouseFn);

        tools.add(holdMouseToolEntry);



        // =======================================================================================================================================================================
        // RELEASE MOUSE 
        JsonObject releaseMouseToolEntry = new JsonObject();
        releaseMouseToolEntry.addProperty("type", "function");

        JsonObject releaseMouseFn = new JsonObject();
        releaseMouseFn.addProperty("name", "releaseMouse");
        releaseMouseFn.addProperty("description", "STRICTLY 'left', 'right', 'middle', stops holding chosen mouse button");

        JsonObject releaseMouseParams = new JsonObject();
        releaseMouseParams.addProperty("type", "object");

        JsonObject releaseButtonSchema = new JsonObject();
        releaseButtonSchema.addProperty("type", "string");
        releaseButtonSchema.addProperty("description", "Mouse button to release: left, right, or middle.");

        JsonArray releaseButtonEnum = new JsonArray();
        releaseButtonEnum.add("left");
        releaseButtonEnum.add("right");
        releaseButtonEnum.add("middle");
        releaseButtonSchema.add("enum", releaseButtonEnum);

        JsonObject releaseMouseProps = new JsonObject();
        releaseMouseProps.add("button", releaseButtonSchema);
        releaseMouseParams.add("properties", releaseMouseProps);

        JsonArray releaseMouseRequired = new JsonArray();
        releaseMouseRequired.add("button");
        releaseMouseParams.add("required", releaseMouseRequired);

        releaseMouseFn.add("parameters", releaseMouseParams);
        releaseMouseToolEntry.add("function", releaseMouseFn);

        tools.add(releaseMouseToolEntry);




        // =======================================================================================================================================================================
        //SCROLL MOUSE
        JsonObject scrollMouseToolEntry = new JsonObject();
        scrollMouseToolEntry.addProperty("type", "function");

        // function block (name MUST stay scrollMouse)
        JsonObject scrollMouseFn = new JsonObject();
        scrollMouseFn.addProperty("name", "scrollMouse");
        scrollMouseFn.addProperty(
            "description",
            "STRICTLY integer. Positive scrolls down. Negative scrolls up."
        );

        // parameters block
        JsonObject scrollMouseParams = new JsonObject();
        scrollMouseParams.addProperty("type", "object");

        // schema for the "notches" argument
        JsonObject notchesSchema = new JsonObject();
        notchesSchema.addProperty("type", "integer");
        notchesSchema.addProperty("description", "Mouse wheel notches. Positive=down, Negative=up.");

        // properties keyed by arg name
        JsonObject scrollMouseProps = new JsonObject();
        scrollMouseProps.add("notches", notchesSchema);
        scrollMouseParams.add("properties", scrollMouseProps);

        // required array
        JsonArray scrollMouseRequired = new JsonArray();
        scrollMouseRequired.add("notches");
        scrollMouseParams.add("required", scrollMouseRequired);

        // attach
        scrollMouseFn.add("parameters", scrollMouseParams);
        scrollMouseToolEntry.add("function", scrollMouseFn);

        // add to tools array
        tools.add(scrollMouseToolEntry);


        //=======================================================================================================================================================================

        // CLICKMOUSE
        JsonObject clickMouseToolEntry = new JsonObject();
        clickMouseToolEntry.addProperty("type", "function");

        JsonObject clickMouseFn = new JsonObject();
        clickMouseFn.addProperty("name", "clickMouse");
        clickMouseFn.addProperty("description", "STRICTLY 'left', 'right' or 'middle'. Clicks mouse button of choice");

        JsonObject clickMouseParams = new JsonObject();
        clickMouseParams.addProperty("type", "object");

        JsonObject clickButtonSchema = new JsonObject();
        clickButtonSchema.addProperty("type", "string");
        clickButtonSchema.addProperty("description", "Mouse button to click.");

        JsonArray clickButtonEnum = new JsonArray();
        clickButtonEnum.add("left");
        clickButtonEnum.add("right");
        clickButtonEnum.add("middle");
        clickButtonSchema.add("enum", clickButtonEnum);

        JsonObject clickMouseProps = new JsonObject();
        clickMouseProps.add("button", clickButtonSchema);
        clickMouseParams.add("properties", clickMouseProps);

        JsonArray clickMouseRequired = new JsonArray();
        clickMouseRequired.add("button");
        clickMouseParams.add("required", clickMouseRequired);

        clickMouseFn.add("parameters", clickMouseParams);
        clickMouseToolEntry.add("function", clickMouseFn);

        tools.add(clickMouseToolEntry);



        //=======================================================================================================================================================================
        //MESSAGE
        JsonObject messageToolEntry = new JsonObject();
        messageToolEntry.addProperty("type", "function");

        // function block (name MUST stay message)
        JsonObject messageFn = new JsonObject();
        messageFn.addProperty("name", "message");
        messageFn.addProperty(
            "description",
            "Used to communicate with user. Message will be printed for user awareness and communication. STRICTLY used to print toDO status and message to user."
        );

        // parameters block
        JsonObject messageParams = new JsonObject();
        messageParams.addProperty("type", "object");

        // schema for the "text" argument
        JsonObject msgSchema = new JsonObject();
        msgSchema.addProperty("type", "string");
        msgSchema.addProperty("description", "Text to display/print to the user.");

        // properties keyed by arg name
        JsonObject messageProps = new JsonObject();
        messageProps.add("text", msgSchema);
        messageParams.add("properties", messageProps);

        // required array
        JsonArray messageRequired = new JsonArray();
        messageRequired.add("text");
        messageParams.add("required", messageRequired);

        // attach
        messageFn.add("parameters", messageParams);
        messageToolEntry.add("function", messageFn);

        // add to tools array
        tools.add(messageToolEntry);

        //=======================================================================================================================================================================
        // WAIT TOOL (Ollama tool schema)
        JsonObject waitToolEntry = new JsonObject();
        waitToolEntry.addProperty("type", "function");

        // function block (name MUST stay wait)
        JsonObject waitFn = new JsonObject();
        waitFn.addProperty("name", "wait");
        waitFn.addProperty(
            "description",
            "integer input in milliseconds. USE TO MAKE PROGRAM REST FOR SPECIFIC AMOUNT OF TIME, like loading web pages."
        );

        // parameters block
        JsonObject waitParams = new JsonObject();
        waitParams.addProperty("type", "object");

        // schema for the "ms" argument
        JsonObject msSchema = new JsonObject();
        msSchema.addProperty("type", "integer");
        msSchema.addProperty("description", "Milliseconds to wait/sleep.");

        // properties keyed by arg name
        JsonObject waitProps = new JsonObject();
        waitProps.add("ms", msSchema);
        waitParams.add("properties", waitProps);

        // required array
        JsonArray waitRequired = new JsonArray();
        waitRequired.add("ms");
        waitParams.add("required", waitRequired);

        // attach
        waitFn.add("parameters", waitParams);
        waitToolEntry.add("function", waitFn);

        // add to tools array
        tools.add(waitToolEntry);

        return tools;

    }

    /**
     * Create API request intended for Ollama/OpenAI/Qwen/Phi models
     * @param prompt Prompt for mega string
     * @return JsonObject with full input for specifically OpenAI prompting
     */
    
    public static JsonObject getSchema(String prompt) 
    {
        try {
            JsonObject schema = new JsonObject();
            schema.addProperty("type", "object");

            JsonObject properties = new JsonObject();

            // context: string
            JsonObject context = new JsonObject();
            context.addProperty("type", "string");
            properties.add("context", context);

            // history: array of string
            JsonObject history = new JsonObject();
            history.addProperty("type", "array");
            JsonObject historyItem = new JsonObject();
            historyItem.addProperty("type", "string");
            history.add("items", historyItem);
            properties.add("history", history);

            // completedStepsList: array of string
            JsonObject completedStepsList = new JsonObject();
            completedStepsList.addProperty("type", "array");
            JsonObject completedStepItem = new JsonObject();
            completedStepItem.addProperty("type", "string");
            completedStepsList.add("items", completedStepItem);
            properties.add("completedStepsList", completedStepsList);

            // taskToDoList: array of { task: string, status: enum }
            JsonObject taskToDoList = new JsonObject();
            taskToDoList.addProperty("type", "array");

            JsonObject taskToDoItem = new JsonObject();
            taskToDoItem.addProperty("type", "object");
            taskToDoItem.addProperty("additionalProperties", false);

            JsonObject taskToDoProperties = new JsonObject();

            JsonObject taskField = new JsonObject();
            taskField.addProperty("type", "string");
            taskToDoProperties.add("task", taskField);

            JsonObject status = new JsonObject();
            status.addProperty("type", "string");
            JsonArray statusEnum = new JsonArray();
            statusEnum.add("not started");
            statusEnum.add("in progress");
            statusEnum.add("complete");
            status.add("enum", statusEnum);
            taskToDoProperties.add("status", status);

            taskToDoItem.add("properties", taskToDoProperties);

            JsonArray todoReq = new JsonArray();
            todoReq.add("task");
            todoReq.add("status");
            taskToDoItem.add("required", todoReq);

            taskToDoList.add("items", taskToDoItem);
            properties.add("taskToDoList", taskToDoList);

            // taskCompleted: boolean
            JsonObject taskCompleted = new JsonObject();
            taskCompleted.addProperty("type", "boolean");
            properties.add("taskCompleted", taskCompleted);

            // action: object, with at most one tool call
            JsonObject action = new JsonObject();
            action.addProperty("type", "object");
            action.addProperty("maxProperties", 1);

            // Values can be: string (most tools), integer (wait/scroll), coords array ([x,y])
            JsonObject actionValueSchema = new JsonObject();
            JsonArray actionValueOneOf = new JsonArray();

            JsonObject valString = new JsonObject();
            valString.addProperty("type", "string");
            actionValueOneOf.add(valString);

            JsonObject valInt = new JsonObject();
            valInt.addProperty("type", "integer");
            actionValueOneOf.add(valInt);

            JsonObject valCoords = new JsonObject();
            valCoords.addProperty("type", "array");
            valCoords.addProperty("minItems", 2);
            valCoords.addProperty("maxItems", 2);
            JsonObject coordItem = new JsonObject();
            coordItem.addProperty("type", "integer");
            valCoords.add("items", coordItem);
            actionValueOneOf.add(valCoords);

            actionValueSchema.add("oneOf", actionValueOneOf);
            action.add("additionalProperties", actionValueSchema);

            properties.add("action", action);

            schema.add("properties", properties);

            // Top-level must-haves for response
            JsonArray requirements = new JsonArray();
            requirements.add("context");
            requirements.add("history");
            requirements.add("completedStepsList");
            requirements.add("taskToDoList");
            requirements.add("taskCompleted");
            requirements.add("action");
            schema.add("required", requirements);

            // IMPORTANT: boolean false (not string "false")
            schema.addProperty("additionalProperties", false);

            return schema;

        } catch (Exception e) 
        {
            System.out.println("ERROR CREATING SCHEMA: " + e);
            return new JsonObject();
        }
    }

    public static JsonObject getUserMessage() {
    JsonObject userMsgObj = new JsonObject();
    userMsgObj.addProperty("role", "user");
    userMsgObj.addProperty(
        "content",
        getUserPrompt() + ". Return ONLY valid JSON matching the provided schema. No extra text."
    );
    return userMsgObj;
    
    
    }

    public static JsonObject getSystemMessage() {
    JsonObject sysMsgObj = new JsonObject();
    sysMsgObj.addProperty("role", "system");
    sysMsgObj.addProperty("content", getSystemPrompt());
    return sysMsgObj;
    }

    public static JsonArray buildMessages() {
        JsonArray messagesArr = new JsonArray();
        messagesArr.add(getSystemMessage());
        messagesArr.add(getUserMessage());
        return messagesArr;
    }



    /**
     * Unifies all JSON structures required for making the request
     * @return JsonObject for http request to OpenAI API interaction
     */
    public static JsonObject getRequestParams()
    {
        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("model", getModelType());
        // jsonPayload.addProperty("think", getThinkLevel());
        jsonPayload.add("tools", getTools());
        jsonPayload.addProperty("system", getSystemPrompt());
        jsonPayload.add("messages", buildMessages());
        jsonPayload.addProperty("stream", false);
        jsonPayload.addProperty("keep_alive","5m");
        jsonPayload.addProperty("logprobs",false);

        return jsonPayload;
    }
}
