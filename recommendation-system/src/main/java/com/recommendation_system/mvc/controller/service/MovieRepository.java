package com.recommendation_system.mvc.controller.service;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.recommendation_system.mvc.model.entity.Movie;

/**
 * Repository interface for Movie entity.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Additional query methods can be defined here if needed

    /**
     * Find movies by title containing a specific keyword.
     * 
     * @param keyword   Keyword to search in movie titles.
     * @return          List of movies with titles containing the keyword.
     */
    List<Movie> findByTitleContaining(String keyword);

    /**
     * Find movies by genres.
     * 
     * @param genres     Genres to filter movies.
     * @return          List of movies belonging to the specified genres.
     */
    List<Movie> findByGenres(String genres);

}
