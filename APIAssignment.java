package edu.uncg;

import java.util.Scanner;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class APIAssignment {

    public static void getMusicInfo() {
        try {
            Scanner input = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter a music query: ");
            String query = input.nextLine();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://shazam.p.rapidapi.com/auto-complete?term=" + query + "&locale=en-US"))
                    .header("x-rapidapi-key", "d5dbc65664mshead682c1774a2bfp18189bjsnfc136a7d5b0d")
                    .header("x-rapidapi-host", "shazam.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            JSONObject obj = new JSONObject(response.body());
            String queries = obj.getString("hints");
            String musicName = queries.replace(",", "\r\n");
            musicName = musicName.replace("[", "");
            musicName = musicName.replace("]", "");
            musicName = musicName.replace("{", "");
            musicName = musicName.replace("}", "");
            musicName = musicName.replace(":", ": ");
            System.out.println("Music suggestions: " + musicName);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            return;
        }
    }
}
