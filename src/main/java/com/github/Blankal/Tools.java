package com.github.Blankal;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.lang.ProcessBuilder; // will handle computer environment interactions
import java.lang.ProcessHandle; // will handle computer environment interactions
import java.lang.Process;

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
            put('“', new int[]{ KeyEvent.VK_QUOTE, 1 });
            put('’', new int[]{ KeyEvent.VK_QUOTE, 0 });
            put('”', new int[]{ KeyEvent.VK_BACK_QUOTE, 0 });
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

    public void moveMouse(int x, int y){
        robot.mouseMove(x, y);
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


        String testTextInput = "“I can’t sleep without them!” my face scrunched up, clutching piles of cushions in an uncharacteristic moment of seven-year-old stubbornness. An overnight trip without my Stitch face pillow? My dolphin pillow pets?  This was absolutely unacceptable, robbing me of the comfort and structure that was my nightly routine. Today, ten-years later, I still relish flopping backwards onto my bed, cushioned by my carefully curated collection of pillows, each encasing a different side of myself:\r\n" + //
                        
                        "Smiling beside me is my chibi avocado pillow, a celebratory gift from my parents for finally trying guacamole. Green, mushy, yucky! I once wrinkled my nose in disgust at the outward appearance of this vegetable (fruit?): before tasting the tableside guac at our favorite Mexican joint. Today, I laugh at the “avocado anecdote” looking at this pillow, but simultaneously remember the message my parents sent me; a reminder to never dismiss new opportunities at first glance. \r\n" + //
                        //
                        "Near the avocado, a ruffled teal pillow from Target sits atop its matching ruffled duvet. Struck with inspiration to redesign my room, I bought this pillow in middle school, after viewing an artsy article in American Girl Magazine’s spring spread. Design continues to fuel my burning creative spark, whether through computing or character art. From the “Coding Projects in Scratch” book, I took inspiration to tinker with block-based programming, designing my own graphics-based puzzles & platformers that provided me the fundamentals to pursue higher-level CS in Python & Java. In the pages of Christopher Hart’s “Master Guide to Drawing Anime”, I study dynamic character composition and the intricacies of cell shading, applying these illustrative techniques to my own digital art.\r\n" + //
                       //
                        "Propped against the headboard, a My Melody pillow cushions my head, a nod to my former aversion to all things “cute.” At age thirteen, the mere thought of being seen sleeping with a pink, fluffy, bunny pillow (Hello Kitty-branded, no less!) horrified me. Now, through My Melody, I embrace my whimsical side, unapologetically jamming out to bubbly idol music or squealing over the turquoise, twin-tailed virtual popstar Hatsune Miku. While tuning synthesized singing vocals for my VOCALOID cover channel on YouTube, my inner geek shines through my usually studious and composed exterior.\r\n" + //
                        //
                        "Lounging lazily beside my bed sits the master pillow, Slothogus, the 3 foot tall, jumbo stuffed sloth, doubling as my go-to living room throw pillow. As my brain rapidly flips between subjects, whether cramming fundamental forces in AP Physics 1, rapidly typing an 11-page research report for AP Seminar, or frantically debugging Java assignments for my Columbia U class, I often find myself overwhelmed by my academic “overachiever\" habits. To quiet this mental clamor, I spare a moment of rest on Slothogus’ plush shoulder. From his stitched smile, I learn to take advantage of what little downtime I have. Be like Slothogus, I inhale, letting my academic-addled anxieties dissipate as I binge a few episodes of Gilmore Girls or Great British Bake Off with my family. \r\n" + //
                         //
                        "Maybe by next year, I will have broken 1:00 in the 100 fly, finished my Girl Scout Gold Award Project, and developed the next cutting-edge software to synthesize singing. Maybe, I’ll turn in my first capstone project for Intro to Computational Linguistics. And maybe, I’ll have added another pillow to the collection, so I can take the day off and have a lie in.\r\n" + //
                        //
                        "I’ll sleep on it.";

        Tools tools = new Tools(testStringToJson);
        // tools.inputText(testTextInput);

        
        
        ProcessHandle.allProcesses().forEach(process -> {
                if(process.info().toString().toLowerCase().contains("visual studio code")){
                    System.out.println(process.info());
                    System.out.println(process.pid());
                    process.destroy();
                }

            // if(process.info().contains("chrome.exe")){
            //     System.out.println("Got it");
            // }
        });
        // });This is a more reliable and robust method for controlling programs compared to simulating user input with Robot, which depends on UI states and can be fragile.
        // ProcessBuilder pb = new ProcessBuilder("notepad.exe","file.txt");

        

    }
}