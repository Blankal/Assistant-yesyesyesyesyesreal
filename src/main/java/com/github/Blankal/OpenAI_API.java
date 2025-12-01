package com.github.Blankal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import static com.github.Blankal.config.getModelType;
import static com.github.Blankal.config.getInstructions;


    public class OpenAI_API {    

        public static void generateStaticFeedback(String prompt) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

            String toPost = """
            {
                "model":"%s",
                "prompt":"%s",
                "stream":false
            }
            """.formatted("llama3.2", prompt);

            System.out.println("POST:" + toPost);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(toPost, StandardCharsets.UTF_8))
                .build();
            HttpResponse<String> response = client.send(request,BodyHandlers.ofString());
            System.out.println("RESPONSE: " + response.body());
            
        }

        public static void generateStaticFeedback(String prompt, String image) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

                String toPost = """
                {
                    "model":"%s",
                    "prompt":"%s",
                    "stream":false
                }
                """.formatted(getModelType(), prompt  + "Image (Base64): " + image);

                System.out.println("POST:" + toPost);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(toPost, StandardCharsets.UTF_8))
                .build();
            HttpResponse<String> response = client.send(request,BodyHandlers.ofString());
            System.out.println("RESPONSE: " + response.body());
            
        }
    }
