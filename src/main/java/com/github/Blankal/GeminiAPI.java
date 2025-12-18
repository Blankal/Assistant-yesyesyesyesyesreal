package com.github.Blankal;

import com.google.genai.Client;  // AI Client
import com.google.genai.types.GenerateContentResponse;  // Generative AI
import static com.github.Blankal.config.getSystemPrompt;;

public class GeminiAPI {

  // Instructions for the Agent (WORK ON THIS SO IT UNDERSTANDS BEST)

  // The client gets the API key from the environment variable `GOOGLE_API_KEY` make sure to set it
  public static final Client client = new Client();

  /**
   * Creates a text response from the AI based on text instructions only.
   * @param modelName The model to use for text generation
   * @param prompt A query or instructions for the AI to respond to (text only)
   * @return AI Agent's text response
   * @see #generateFeedback(String, String) Optional image input version
   */
  public static String generateStaticFeedback(String modelName, String prompt) 
  {
    GenerateContentResponse response =
        client.models.generateContent(
            modelName,
            prompt,
            null);

    System.out.println(response.text());
    return response.text();
  }

  /**
   * Creates a text response from the AI based on text instructions only.
   * @param prompt A query or instructions for the AI to respond to (text only)
   * @param image A base64 encoded image string to provide AI Agent with context
   * @return AI Agent's text response
   * @see #generateFeedback(String, String) Optional text-only input version
   */
  public static String generateStaticFeedback(String modelName, String prompt, String image)  // Image in 
  {
    GenerateContentResponse response =
        client.models.generateContent(
            modelName,
            getSystemPrompt() + prompt + "\nImage (Base64): " + image,
            null
            );

    System.out.println(response.text());
    return response.text();
  }
}