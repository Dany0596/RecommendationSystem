package com.recommendation_system.mvc.model.entity;

import jakarta.persistence.*;

/**
 * Entity class representing a Rating.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long ratingId;
    private Long userId;
    private Long movieId;
    private Double rating;
    private Long timestamp;

    /**
     * Default constructor.
     */
    public Rating() {
        this.userId     = null;
        this.movieId    = null;
        this.rating     = 0d;
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
    public Rating(Long userId, Long movieId, Double rating, Long timestamp) {
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
    public Long getUserId() {
        return userId;
    }           

    /**
     * Set user ID.
     * 
     * @param userId ID of the user who rated the movie.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Get movie ID.
     * 
     * @return ID of the rated movie.
     */
    public Long getMovieId() {
        return movieId;
    }

    /**
     * Set movie ID.
     * 
     * @param movieId ID of the rated movie.
     */
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    /**
     * Get rating.
     * 
     * @return Rating value (e.g., 1 to 5 stars).
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Set rating.
     * 
     * @param rating Rating value (e.g., 1 to 5 stars).
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Get timestamp.
     * 
     * @return Time when the rating was made.
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Set timestamp.
     * 
     * @param timestamp Time when the rating was made.
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get rating ID.
     * 
     * @return ID of the rating.
     */
    public Long getRatingId() {
        return ratingId;
    }

    /**
     * Set rating ID.
     * 
     * @param ratingId ID of the rating.
     */
    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }
}