    package com.github.Blankal;

    import static com.github.Blankal.config.getInstructions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

    public class LLama {    
        public static void main(String[] args) throws IOException, InterruptedException{
            HttpClient client = HttpClient.newHttpClient(); 

                String toPost = """
                {
                    "model":"llama3.2",
                    "prompt":"Why the sky is blue?",
                    "stream":false
                }
                """;

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(toPost, StandardCharsets.UTF_8))
                .build();
            HttpResponse<String> response = client.send(request,BodyHandlers.ofString());
            
        }
    }
