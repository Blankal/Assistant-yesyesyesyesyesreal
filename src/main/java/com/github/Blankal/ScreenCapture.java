package com.github.Blankal;

import java.awt.Robot;  // For screen capture
import java.awt.Rectangle;  // For screen dimensions
import java.awt.Toolkit;  // For screen dimensions
import java.awt.image.BufferedImage;  // For image handling
import java.io.ByteArrayOutputStream;  // For image conversion
import javax.imageio.ImageIO;  // For writing image to baos

public class ScreenCapture
{
    private static Robot robot;
    private static ByteArrayOutputStream baos;
    private static Rectangle screenBounds;
    private static final String format = "jpg";
    static  // init robot
    {
        try {
            robot = new Robot();
        } catch (java.awt.AWTException e) {
            System.out.println("Failed to initialize Robot: " + e);
        }
    }
    static  // init screen bounds
    {
        try
        {
            screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        }
        catch(Exception e)
        {
            System.out.println("Failed to get screen dimensions; " + e);
        }
    }
    static  // init byte array
    {
        try
        {
            baos = new ByteArrayOutputStream();
        }
        catch(Exception e)
        {
            System.out.println("Failed to initialize BAOS: " + e);
        }
    }

    /**
   * Creates a screenshot of the current screen state and encodes it to a base64 string.
   * @return base64 encoded string of current screen frame
   */
    public static String getFrame()
    {
        try
        {
            BufferedImage screenshot = robot.createScreenCapture(screenBounds);
            ImageIO.write(screenshot, format, baos);
            byte[] imageBytes = baos.toByteArray();  // Becomes byte array here
            baos.reset();  // Clear baos for re-use
            String base64String = java.util.Base64.getEncoder().encodeToString(imageBytes);  // Convert to base64 string
            return base64String;
        }
        catch(Exception e)
        {
            System.out.println("Error capturing screen: " + e);
            return null;
        }
    }

}