package com.recommendation_system.mvc.model.entity;

import jakarta.persistence.*;

/**
 * Entity class representing a Movie.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long movieId;
    private String title;
    private String genres;

    /**
     * Default constructor
     */
    public Movie() {
        this.movieId        = null;
        this.title          = null;
        this.genres          = null;
    }
    
    /**
     * Parameterized constructor.

        @param title         Movie title.
        @param genres         Movie genre.
     */
    public Movie(String title, String genres) {
        this.title = title;
        this.genres = genres;
    }

    /*  DEFAULT GETTERS AND SETTERS */

    /**
     * Get title.
     * 
     * @return Movie title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     * 
     * @param title Movie title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get genre.
     * 
     * @return Movie genres.
     */
    public String getGenres() {
        return genres;
    }

    /**
     * Set genre.
     * 
     * @param genres Movie genres.
     */
    public void setGenres(String genres) {
        this.genres = genres;
    }

    /**
     * Get movie ID.
     * 
     * @return ID of the movie.
     */
    public Long getMovieId() {
        return movieId;
    }

    /**
     * Set movie ID.
     * 
     * @param movieId ID of the movie.
     */
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}