package com.recommendation_system.mvc.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
public class Movie {
    @Id
    private String movieId;
    private String title;
    private String genre;

    /**
     * Default constructor
     */
    public Movie() {
        this.movieId        = null;
        this.title          = null;
        this.genre          = null;
    }
    
    /**
     * Parameterized constructor.

        @param title         Movie title.
        @param genre         Movie genre.
     */
    public Movie(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    /*  DEFAULT GETTERS AND SETTERS */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}