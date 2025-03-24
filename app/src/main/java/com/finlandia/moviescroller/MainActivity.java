package com.finlandia.moviescroller;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

// A lot of AI (ChatGPT) was used in this assignment

public class MainActivity extends AppCompatActivity {
    // Displays list of movies
    private RecyclerView recyclerView;
    // Binds the movie data to recyclerView
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Initialize recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        // Set LayoutManager to LinearLayoutManager for vertical scrolling
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Load raw data from JSON
        List<Movie> rawMovies = JSONUtility.loadMovies(this);
        // Validate movie data
        List<Movie> validMovies = validateMovies(rawMovies);

        // Create an adapter with the validated list
        adapter = new MovieAdapter(validMovies);
        // Set the adapter to recyclerView to bind the data
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /* Used to validate the list of movies.
    If any movie has missing data, replace with default values.
     */
    private List<Movie> validateMovies(List<Movie> movies) {
        List<Movie> validMovies = new ArrayList<>();

        // Loop through each movie
        for (Movie movie : movies) {
            // Check for title
            String title = (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) ? "Unknown Title" : movie.getTitle();
            // Check if movie is made before movies were made
            int year = (movie.getYear() < 1800 || movie.getYear() > 2100) ? 0 : movie.getYear();
            // Check for genre
            String genre = (movie.getGenre() == null || movie.getGenre().trim().isEmpty()) ? "Unknown Genre" : movie.getGenre();
            // Check poster, not
            String poster = (movie.getPoster() == null || movie.getPoster().trim().isEmpty()) ? "default_poster" : movie.getPoster();
            // Add validated movie to list
            validMovies.add(new Movie(title, year, genre, poster));
        }
        // Return list of valid movies
        return validMovies;
    }

}