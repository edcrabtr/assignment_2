package edu.uncg;
/*
Last updated: 15 Feb, 2021
This class will call on the Shazam API, prompt the user to enter a music search query, and display the most relevant results.
Authors: Emily Crabtree
*/

import java.util.Scanner;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class APIAssignment {

    //=================  GETTERS ===============
    public static void getMusicInfo() {
        try {
            // Create a scanner object and prompt user to input music search query.
            Scanner input = new Scanner(System.in);
            System.out.println("Enter a music search query: ");
            String query = input.nextLine();
            query = query.replace(" ", "%20");

            // Import Shazam API code.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://shazam.p.rapidapi.com/auto-complete?term=" + query + "&locale=en-US"))
                    .header("x-rapidapi-key", "d5dbc65664mshead682c1774a2bfp18189bjsnfc136a7d5b0d")
                    .header("x-rapidapi-host", "shazam.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            // Parse object into a usable Java JSON object.
            JSONObject obj = new JSONObject(response.body());

            // Print out the search query results.
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
        }
    }
}
