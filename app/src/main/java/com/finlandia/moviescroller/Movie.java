package com.finlandia.moviescroller;

public class Movie {
    private String title;
    private int year;
    private String genre;
    private String poster;

    public Movie(String title, int year, String genre, String poster) {
        // Attributes with checking for missing titles etc.
        this.title = (title == null || title.trim().isEmpty()) ? "Unknown Title" : title;
        this.year = (year < 1800 || year > 2100) ? 0 : year;
        this.genre = (genre == null || genre.isEmpty()) ? "Unknown Genre" : genre;
        this.poster = (poster == null || poster.isEmpty()) ? "default_poster" : poster;
    }
    // GETTERS AND SETTERS
    // Returns title
    public String getTitle() {
        return title;
    }
    // Returns year
    public int getYear() {
        return year;
    }
    // Returns genre
    public String getGenre() {
        return genre;
    }
    // Returns poster
    public String getPoster() {
        return poster;
    }
}
