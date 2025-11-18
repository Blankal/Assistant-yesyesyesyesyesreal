package com.github.Blankal;

import com.google.genai.Client;  // AI Client
import com.google.genai.types.GenerateContentResponse;  // Generative AI
import org.opencv.core.Core;  // OpenCV

public class GenerateTextFromTextInput {
  // The client gets the API key from the environment variable `GOOGLE_API_KEY`
  public static final Client client = new Client();

  public static void generateFeedback(String textInstruction) 
  {
    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            textInstruction,
            null);

    System.out.println(response.text());
  }

  public static void generateFeedback(String instruction, String image)  // Image in 
  {
    GenerateContentResponse response =
        client.models.generateContent(
            "gemini-2.5-flash",
            "How can I allow you to view images through java code?",
            null);

    System.out.println(response.text());
  }
}