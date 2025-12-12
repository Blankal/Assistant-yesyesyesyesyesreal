package com.github.Blankal;

import java.io.File;  // For setting directory of OmniParser
import static com.github.Blankal.config.getCondaAddress;
import static com.github.Blankal.config.getCondaPort;

public class VisionHandler 
{
    /**
     * Initializes a local server for OmniParser(Python) and the main Java 
     * app to communicate by starting the CondaServer.py file.
     */
    public static void init()
    {
        try
        {
            ProcessBuilder condaServerInit = new ProcessBuilder(
            "conda", "run", "-n", "omniparser-env",
            "python", "-m", "uvicorn", "server:app",
            "--host", getCondaAddress(),
            "--port", getCondaPort()
            );
            condaServerInit.directory(new File("src/main/java/com/github/Blankal/localAgents/OmniParser-v2/OmniParser"));

            condaServerInit.redirectErrorStream(true);
            Process condaServer = condaServerInit.start();
        }
        catch (Exception e)
        {
            System.out.println("Failed to start conda server: " + e);
        }

    }
}
