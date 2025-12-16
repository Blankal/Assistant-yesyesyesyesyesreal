package com.github.Blankal;

import java.net.URI;
import java.net.http.HttpClient;  // For HTTP requests to APIs
import java.net.http.HttpRequest;  // For HTTP requests to APIs
import java.net.http.HttpResponse;  // For HTTP response handling from APIs
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;  // For Json handling / orginization
import com.google.gson.JsonObject;  // For Json handling
import com.google.gson.JsonArray;  // For Json arrays
import java.io.IOException;  // For IO handling (necessary for model API calls)
import static com.github.Blankal.config.getModelType;


    public class OpenAI_API {    

        /**
         * Generates text feedback from AI model using only text input sent to an API/local server.
         * @param prompt Text prompt to instruct the model
         * @throws IOException
         * @throws InterruptedException
         */
        public static void generateStaticFeedback(String prompt) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

            JsonObject jsonPayload = new JsonObject();
            jsonPayload.addProperty("model", getModelType());
            jsonPayload.addProperty("prompt", prompt);
            jsonPayload.addProperty("stream", false);

            String toPost = new Gson().toJson(jsonPayload);

            System.out.println("POST:" + toPost);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(toPost, StandardCharsets.UTF_8))
                .build();
            HttpResponse<String> response = client.send(request,BodyHandlers.ofString());
            System.out.println("RESPONSE: " + response.body());
            JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
            System.out.println(jsonResponse.get("response"));
            
        }

        /**
         * Generates text feedback from the AI model using text and image input sent to an API/local server.
         * Mostly deprecated
         * @param prompt Text prompt to instruct the model
         * @param image Image in base64 string format
         * @throws IOException
         * @throws InterruptedException
         */
        public static void generateStaticFeedback(String prompt, String image) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

            JsonObject jsonPayload = new JsonObject();
            jsonPayload.addProperty("model", getModelType());
            jsonPayload.addProperty("prompt", prompt);
            jsonPayload.addProperty("temperature", 0.2);
            jsonPayload.addProperty("stream", false);
            JsonArray images = new JsonArray();
            images.add(image);
            jsonPayload.add("images", images);

            String toPost = new Gson().toJson(jsonPayload);
            System.out.println("POST:" + toPost.substring(0, toPost.length()-image.length()-1));
            System.out.println("Model:" + getModelType());  

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(toPost, StandardCharsets.UTF_8))
                .build();
            HttpResponse<String> response = client.send(request,BodyHandlers.ofString());
            System.out.println("RESPONSE: " + response.body());
            JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
            System.out.println(jsonResponse.get("response"));
        }
    }
