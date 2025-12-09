package com.github.Blankal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import static com.github.Blankal.config.getModelType;
import static com.github.Blankal.config.getInstructions;


import static com.github.Blankal.config.getInstructions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


    public class OpenAI_API {    

        public static void generateStaticFeedback(String prompt) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

            String toPost = """
            {
                "model":"%s",
                "prompt":"%s",
                "stream":%s,
            }
            """.formatted("llama3.2-vision:11b", prompt,false);

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

        public static void generateStaticFeedback(String prompt, String image) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

                String toPost = """
                {
                    "model": "%s",
                    "prompt": "%s",
                    "images": ["%s"],
                    "temperature": 0.2,
                    "stream": false
                }   
                """.formatted(getModelType(), prompt, image);
                    // "prompt":"%s",
                    // "stream":false,
                    // "image":["%s"]
                System.out.println("POST:" + toPost);
                System.out.println("MOdel:" + getModelType());  

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
