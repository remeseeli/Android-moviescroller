package com.finlandia.moviescroller;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// This class binds the list of movies to the RecyclerView
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;

    // Constructor
    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }
    // Called when new ViewHolder is created
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
    // Suppress annoying warning
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        // Set the title text, if title is missing or null display a placeholder text
        holder.title.setText(movie.getTitle());
        // Set the genre text, display "Unknown Genre" if genre is missing or empty
        holder.year.setText("Year: " + (movie.getYear() == 0 ? "Unknown" : movie.getYear()));
        // Load the poster image using the movie's poster identifier
        holder.genre.setText("Genre: " + movie.getGenre());

        // Load poster image using default resources
        int posterResource = holder.itemView.getContext().getResources().getIdentifier(
                movie.getPoster(), "drawable", holder.itemView.getContext().getPackageName());
        // If the poster image is not found, use a default placeholder image
        if (posterResource == 0) {
            posterResource = R.drawable.placeholder_image; // Default placeholder
        }

        holder.poster.setImageResource(posterResource);
    }
    // Returns number of movies in dataset
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //ViewHolder class that represents a single movie item in the RecyclerView.
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, year, genre;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            genre = itemView.findViewById(R.id.movie_genre);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }
}