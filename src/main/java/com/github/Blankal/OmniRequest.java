package com.github.Blankal;
import java.net.URI;
import java.net.http.HttpClient;  // For HTTP requests to APIs
import java.net.http.HttpRequest;  // For HTTP requests to APIs
import java.net.http.HttpResponse;  // For HTTP response handling from APIs
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;  // For Json serialization/deserialization
import com.google.gson.JsonElement;  // For taking Json elements from response
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;  // For Json handling
import static com.github.Blankal.config.getOmniParserAddress;
import static com.github.Blankal.config.getScreenDimensions;
import static com.github.Blankal.ScreenCapture.getFrame;  // get B64 Images for OmniParser requests

public class OmniRequest 
{
    public static HttpClient client;
    public static HttpRequest parseRequest;
    public static HttpRequest pingRequest;

    static
    {
        try
        {
            client = HttpClient.newHttpClient();
        } 
        catch(Exception e){ System.out.println("Error initializing HTTP client: " + e); }

        try
        {
            pingRequest = HttpRequest.newBuilder()
                .uri(URI.create(getOmniParserAddress() + "ping/"))
                .GET()
                .build();
        }
        catch (Exception e){ System.out.println(e); }
    }

    /** 
     * Sends a ping request to the OmniParser localhost server to check if it's active.
     */
    public static void pingOmniParser()
    {
        try
        {
            System.out.println("Ping Request to OmniParser: " + pingRequest.uri());
            HttpResponse<String> response = client.send(pingRequest, BodyHandlers.ofString());
            System.out.println("Response Body: " + response.body());
            JsonObject jsonResponse = null;
            if (response.statusCode() == 200 && response.body() != null && !response.body().isBlank()) {
                jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
            }

            if (jsonResponse != null && jsonResponse.has("message")) {
                System.out.println("OmniParser Status: " + jsonResponse.get("message").getAsString());
            } else {
                System.out.println("Ping did not return expected JSON. Body was: " + response.body());
            }
        }
        catch (Exception e){ System.out.println("Error sending ping request: " + e); } 
    }

    /**
     * Sends a parse request to OmniParser and retrieves the parsed content list.
     * @return String representation of the parsed_content_list in JSON string format
     */
    public static String getOmniParseOutput(String b64Image)
    {

        try
        {
            // Payload to send to OmniParser
            JsonObject body = new JsonObject();
            body.addProperty("base64_image", b64Image);
            String jsonBody = new Gson().toJson(body);

            parseRequest = HttpRequest.newBuilder()
                .uri(URI.create(getOmniParserAddress() + "parse/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();
        }
        catch (Exception e){ System.out.println("Error creating OmniParser 'parse' request: " + e); }

        try
        {
            HttpResponse<String> response = client.send(parseRequest, BodyHandlers.ofString());
            String bodyString = response.body();
            JsonObject jsonResponse = new Gson().fromJson(bodyString, JsonObject.class);
            JsonElement content_list = jsonResponse.get("parsed_content_list");

            for(JsonElement element : content_list.getAsJsonArray())  // adds bbox coordinates for mouse inputs
            {
                JsonObject obj = element.getAsJsonObject();
                JsonArray bbox = obj.getAsJsonArray("bbox");

                double x_min = bbox.get(0).getAsDouble();
                double y_min = bbox.get(1).getAsDouble();
                double x_max = bbox.get(2).getAsDouble();
                double y_max = bbox.get(3).getAsDouble();

                int x_center = (int)(Math.round((x_min + x_max) / 2) * getScreenDimensions()[0]);
                int y_center = (int)(Math.round((y_min + y_max) / 2) * getScreenDimensions()[1]);


                JsonArray CoordinateBbox = new JsonArray();
                CoordinateBbox.add(x_center);
                CoordinateBbox.add(y_center);

                obj.add("mouseClickCoordinate", CoordinateBbox);
            }
            return content_list.getAsJsonArray().toString();
        }
        catch (Exception e)
        {
            System.out.println("\u001B[31m\" + Error recieving OmniParser response: " + e + "\u001B[0m");
            return null;
        }
    }
}
