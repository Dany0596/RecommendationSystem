package com.recommendation_system.mvc.model.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingId;
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

    /**
     * Get user ID.
     * 
     * @return ID of the user who rated the movie.
     */
    public String getUserId() {
        return userId;
    }           

    /**
     * Set user ID.
     * 
     * @param userId ID of the user who rated the movie.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get movie ID.
     * 
     * @return ID of the rated movie.
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * Set movie ID.
     * 
     * @param movieId ID of the rated movie.
     */
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    /**
     * Get rating.
     * 
     * @return Rating value (e.g., 1 to 5 stars).
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set rating.
     * 
     * @param rating Rating value (e.g., 1 to 5 stars).
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Get timestamp.
     * 
     * @return Time when the rating was made.
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Set timestamp.
     * 
     * @param timestamp Time when the rating was made.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}