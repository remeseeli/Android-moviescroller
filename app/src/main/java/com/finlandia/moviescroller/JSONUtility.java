package com.finlandia.moviescroller;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JSONUtility {
    public static List<Movie> loadMovies(Context context) {
        List<Movie> movieList = new ArrayList<>();
        try {
            // Open movies.json file from assets
            InputStream is = context.getAssets().open("movies.json");
            // JSON contents are read into a byte array
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert byte array into a string
            String json = new String(buffer, StandardCharsets.UTF_8);
            // Convert string into a JSON array
            JSONArray jsonArray = new JSONArray(json);
            // Loop through JSON array and parse each movie in said array
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    // Get JSON object representing a movie
                    JSONObject obj = jsonArray.getJSONObject(i);
                    // Extract and validate properties
                    String title = obj.optString("title", "Unknown Title");
                    int year = parseYear(obj.opt("year"));
                    String genre = obj.optString("genre", "Unknown Genre");
                    String poster = obj.optString("poster", "default_poster");
                    // Add valid movie to list
                    if (!title.equals("Unknown Title") && year != 0) {
                        movieList.add(new Movie(title, year, genre, poster));
                    } else {
                        // Log error if the entry is invalid (missing year or title)
                        Log.e("JSONUtility", "Skipping invalid entry at index " + i);
                    }
                } catch (Exception e) {
                    // Log error if there is an issue parsing a movie at the current index
                    Log.e("JSONUtility", "Error parsing movie at index " + i, e);
                }
            }
        } catch (Exception e) {
            // Log error if the JSON file cannot be loaded or in case of an IO issue
            Log.e("JSONUtility", "Error loading JSON file", e);
        }
        // Return the list of parsed movies
        return movieList;
    }

    // Parses year from JSON object, returns 0 if year is invalid or cannot be parsed
    private static int parseYear(Object yearObj) {
        if (yearObj instanceof Integer) {
            return (int) yearObj;
        } else if (yearObj instanceof String) {
            try {
                return Integer.parseInt((String) yearObj);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}