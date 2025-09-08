package com.recommendation_system.mvc.controller.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.recommendation_system.mvc.model.entity.Movie;

public interface MovieRepository extends MongoRepository<Movie, Long> {
    // Additional query methods can be defined here if needed

    /**
     * Find movies by title containing a specific keyword.
     * 
     * @param keyword   Keyword to search in movie titles.
     * @return          List of movies with titles containing the keyword.
     */
    List<Movie> findByTitleContaining(String keyword);

    /**
     * Find movies by genre.
     * 
     * @param genre     Genre to filter movies.
     * @return          List of movies belonging to the specified genre.
     */
    List<Movie> findByGenre(String genre);

}
