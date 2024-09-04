package ca.jrvs.apps.practice;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHandler {
    public static void main(String[] args) {
        String apiKey = "ed3afc8426mshe9cc386c501a3f0p1d9b0fjsn6b287178a61a";
        String symbol = "MSFT";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://azure3.p.rapidapi.com/people/page/1/"))
                .header("X-RapidAPI-KEY", apiKey)
                .header("X-RapidAPI-Host", "azure3.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
