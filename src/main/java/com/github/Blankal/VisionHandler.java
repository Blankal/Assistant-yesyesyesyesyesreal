package com.github.Blankal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VisionHandler {

    public static void initialize(String[] args)
    {
        try
        {
            ProcessBuilder pb = new ProcessBuilder();
        }
        catch (Exception e)
        {
            System.out.println("Error intializing OmniParser: " + e);
        }
    }
    
    /**
     * Handler for obtaining OmniParser output (Omni only runs in python)
     * 
     * @return Outputs a JSON string containing names of detected screen elements and their positions
    */
    public static String getOmniParse()
    {
        try
        {
            ProcessBuilder pb = new ProcessBuilder("python", "-u", "Omni/Parser/Path");
            Process OmniParserProcess = pb.start();

            // Reads input from OmniParser
            BufferedReader input = new BufferedReader(new InputStreamReader(OmniParserProcess.getInputStream()));
            StringBuilder output = new StringBuilder();
            String textLine;

            while ((textLine = input.readLine()) != null)  // Appends until no more output
            {
                output.append(textLine + ",\n");
            }

            int exitCode = OmniParserProcess.waitFor();  // Hold program until OmniParser can output
            System.out.println("OmniParser exit code: " + exitCode);
            input.close();
            return output.toString();
        }
        catch (Exception e)
        {
            System.out.println("Error in OmniParser execution: " + e);
            return null;
        }
    }
}
