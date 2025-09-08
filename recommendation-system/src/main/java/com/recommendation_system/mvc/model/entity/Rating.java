package com.recommendation_system.mvc.model.entity;

import java.sql.Timestamp;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class Rating {
    private String userId;
    private String movieId;
    private int rating; // e.g., 1 to 5 stars
    private Timestamp timestamp;

    /**
     * Default constructor.
     */
    public Rating() {
        this.userId     = null;
        this.movieId    = null;
        this.rating     = 0;
        this.timestamp  = null;
    }

    /**
     * Parameterized constructor.
     * 
     * @param userId        ID of the user who rated the movie.
     * @param movieId       ID of the rated movie.
     * @param rating        Rating value (e.g., 1 to 5 stars).
     * @param timestamp     Time when the rating was made.
     */
    public Rating(String userId, String movieId, int rating, Timestamp timestamp) {
        this.userId     = userId;
        this.movieId    = movieId;
        this.rating     = rating;
        this.timestamp  = timestamp;
    }

    /*  DEFAULT GETTERS AND SETTERS */

    public String getUserId() {
        return userId;
    }           

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}