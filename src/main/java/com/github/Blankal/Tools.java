package com.github.Blankal;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;  // will convert String output from llm to json dataset

/*
In order for LLM agent be able to work with computer environment it will return 
action or sequence  of actions dataset structure in json in following  format:

the Tool class will be created in order to parse through the json 
and identify the sequence in which it's suppose to work
*/

/*
    --- possible actions with Robot ---
    robot.mouseMove(x, y); // Move mouse to (x, y)
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); // Left mouse button press
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); // Left mouse button release

    robot.keyPress(KeyEvent.VK_A); // Press 'A' key  --- will use for text input + will simulate human typing
    robot.keyRelease(KeyEvent.VK_A); // Release 'A' key

    robot.delay(100); // Delay for 100 milliseconds
    robot.mouseWheel(5); // Scroll mouse wheel down 5 notches
    robot.mouseWheel(-5); // Scroll mouse wheel up 5 notches
    robot.setAutoDelay(50); // Set a delay of 50 milliseconds between actions -- for more reliable performance
*/

public class Tools {

    private Map<String, Object> actions;
    private Robot robot;

    // Defined special keys mapping - moved outside constructor at class level
    private static final Map<Character, int[]> SPECIAL_KEYS = new HashMap<>() {
        {
            put('!', new int[]{ KeyEvent.VK_1, 1 });
            put('@', new int[]{ KeyEvent.VK_2, 1 });
            put('#', new int[]{ KeyEvent.VK_3, 1 });
            put('$', new int[]{ KeyEvent.VK_4, 1 });
            put('%', new int[]{ KeyEvent.VK_5, 1 });
            put('^', new int[]{ KeyEvent.VK_6, 1 });
            put('&', new int[]{ KeyEvent.VK_7, 1 });
            put('*', new int[]{ KeyEvent.VK_8, 1 });
            put('(', new int[]{ KeyEvent.VK_9, 1 });
            put(')', new int[]{ KeyEvent.VK_0, 1 });

            put('-', new int[]{ KeyEvent.VK_MINUS, 0 });
            put('_', new int[]{ KeyEvent.VK_MINUS, 1 });
            put('=', new int[]{ KeyEvent.VK_EQUALS, 0 });
            put('+', new int[]{ KeyEvent.VK_EQUALS, 1 });

            put('[', new int[]{ KeyEvent.VK_OPEN_BRACKET, 0 });
            put('{', new int[]{ KeyEvent.VK_OPEN_BRACKET, 1 });
            put(']', new int[]{ KeyEvent.VK_CLOSE_BRACKET, 0 });
            put('}', new int[]{ KeyEvent.VK_CLOSE_BRACKET, 1 });

            put('\\', new int[]{ KeyEvent.VK_BACK_SLASH, 0 });
            put('|', new int[]{ KeyEvent.VK_BACK_SLASH, 1 });

            put(';', new int[]{ KeyEvent.VK_SEMICOLON, 0 });
            put(':', new int[]{ KeyEvent.VK_SEMICOLON, 1 });
            
            
            put('\'', new int[]{ KeyEvent.VK_QUOTE, 0 });
            put('"', new int[]{ KeyEvent.VK_QUOTE, 1 });
            put('’', new int[]{ KeyEvent.VK_QUOTE, 0 });
            put('`', new int[]{ KeyEvent.VK_BACK_QUOTE, 0 });
            put('~', new int[]{ KeyEvent.VK_BACK_QUOTE, 1 });

            put(',', new int[]{ KeyEvent.VK_COMMA, 0 });
            put('<', new int[]{ KeyEvent.VK_COMMA, 1 });
            put('.', new int[]{ KeyEvent.VK_PERIOD, 0 });
            put('>', new int[]{ KeyEvent.VK_PERIOD, 1 });
            put('/', new int[]{ KeyEvent.VK_SLASH, 0 });
            put('?', new int[]{ KeyEvent.VK_SLASH, 1 });

            put(' ', new int[]{ KeyEvent.VK_SPACE, 0 });
            put('\r', new int[]{ KeyEvent.VK_ENTER, 0 });  // Carriage return as Enter key
            put('\n', new int[]{ KeyEvent.VK_ENTER, 0 });  // Newline as Enter key
            put('\t', new int[]{ KeyEvent.VK_TAB, 0 });
        }
    };

    public Tools(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            robot = new Robot();
        } catch (java.awt.AWTException e) {
            System.out.println("Failed to initialize Robot: " + e);
        }

        try {
            actions = mapper.readValue(response, Map.class); // string json to Map
        } catch (Exception e) {
            System.out.println("Failed to convert response to json dataset: " + e);
        }
        System.out.println("Parsed actions: " + actions);
        System.out.println(actions.get("action"));

        for (Map.Entry<String, Object> value : actions.entrySet()) {
            System.out.println(value);
        }
    }

    public boolean inputText(String text){
        robot.delay(2000); // 2 sec delay to give time to switch application

        int keyCode;
        try {
            for(char c : text.toCharArray()){
                System.out.println(c);
                int[] info = SPECIAL_KEYS.get(c);

                if(info == null){            
                    keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                    if(Character.isUpperCase(c)){
                        this.robot.keyPress(KeyEvent.VK_SHIFT); // in case of upper character
                    }
                    this.robot.keyPress(keyCode);

                    this.robot.keyRelease(keyCode);
                    if (Character.isUpperCase(c)){
                        this.robot.keyRelease(KeyEvent.VK_SHIFT);
                    }
                } 
                else{
                    if(info[1] == 1){
                        this.robot.keyPress(KeyEvent.VK_SHIFT);
                        this.robot.keyPress(info[0]);

                        this.robot.keyRelease(info[0]);
                        this.robot.keyRelease(KeyEvent.VK_SHIFT);
                    } 
                    else if(info[1]==0){
                        this.robot.keyPress(info[0]);
                        this.robot.keyRelease(info[0]);
                    }
                }
                this.robot.delay(5);
            } 
            return true;
        }
        catch(Exception e){
            System.out.println("Error during text input: " + e);
            return false;
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


        String testTextInput = "";

        Tools tools = new Tools(testStringToJson);
        tools.inputText(testTextInput);

    }
}
