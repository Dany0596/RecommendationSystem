package com.recommendation_system.mvc.controller.service;


import java.util.List;
import com.recommendation_system.mvc.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Rating entity.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Find ratings by user ID.
     * 
     * @param userId    ID of the user.
     * @return          List of ratings for the user.
     */
    List<Rating> findByUserId(Long userId);

    /**
     * Find ratings by movie ID.
     * 
     * @param movieId    ID of the movie.
     * @return           List of ratings for the movie.
     */
    List<Rating> findByMovieId(Long movieId);
}
