package com.recommendation_system.mvc.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    private Long movieId;
    private String title;
    private String genre;

    /*
     * Default constructor
     */
    public Movie() {
        this.movieId        = null;
        this.title          = null;
        this.genre          = null;
    }
    
    /*
     * Parameterized constructor.

        @param title         Movie title.
        @param genre         Movie genre.
     */
    public Movie(String title, String genre, int year, String director, String description) {
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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}