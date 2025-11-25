package com.github.Blankal;

import java.awt.Robot;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;  // will convert String output from llm to json dataset

/* 
In order for LLM agent be able to work with computer environment it will return 
action or sequence  of actions dataset structure in json in following  format:

the Tool class will be created in order to parse through the json 
and identify the sequence in which it's suppose to work
*/

public class Tools {

    private Map<String, Object> actions;
    private Robot robot;
    
    public Tools(String response){
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            robot = new Robot();
        } catch (java.awt.AWTException e) {
            System.out.println("Failed to initialize Robot: " + e);
        }
        
        try{ 
        actions = mapper.readValue(response, Map.class); // string json to Map 
        } catch (Exception e){
            System.out.println("Failed to convert response to json dataset: " + e);
        }
        System.out.println("Parsed actions: " + actions);
        System.out.println(actions.get("action"));
        
        for(Map.Entry<String, Object> value: actions.entrySet()){
            System.out.println(value);
        }
    }

    public static void main(String[] args){
        String testStringToJson = """
            {
                "action": "click",
                "coordinates": {
                "x": 150,
                "y": 300
                }
            },
            {
                "action": "type",
                "text": "Hello, World!"
            }


            """;

        Tools tools = new Tools(testStringToJson);
        
    }

}