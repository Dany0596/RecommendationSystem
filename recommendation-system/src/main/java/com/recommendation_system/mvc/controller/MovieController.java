package com.recommendation_system.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recommendation_system.mvc.controller.service.MovieRepository;
import com.recommendation_system.mvc.model.entity.Movie;
import java.util.List;

/**
 * Controller for managing movies.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieRepository movieRepository;  // Repository for Movie entity.

    /**
     * Constructor with dependency injection.
     * 
     * @param movieRepository   Repository for Movie entity.
     */
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Get all movies.
     * 
     * @return  List of all movies.
     */
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Search movies by title keyword.
     * 
     * @param keyword   Keyword to search in movie titles.
     * @return          List of movies with titles containing the keyword.
     */
    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String keyword) {
        return movieRepository.findByTitleContaining(keyword);
    }

    /**
     * Add a new movie.
     * 
     * @param movie     Movie object to be added.
     * @return          The saved movie object.
     */
    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }
}