package com.recommendation_system.mvc.controller;

import com.recommendation_system.mvc.controller.service.CosineSimilarityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for movie recommendations using cosine similarity.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    // Service for cosine similarity recommendations.
    private final CosineSimilarityService cosineSimilarityService;

    /**
     * Constructor with dependency injection.
     * 
     * @param cosineSimilarityService   Service for cosine similarity recommendations.
     */
    public RecommendationController(CosineSimilarityService cosineSimilarityService) {
        this.cosineSimilarityService = cosineSimilarityService;
    }

    /** 
     * Get movie recommendations for a user based on cosine similarity.
     * 
     * @param userId    ID of the user to get recommendations for.
     * @param top       Number of top recommendations to return (default is 5).
     * @return          List of recommended movie IDs.
     */
    @GetMapping("/cosine/{userId}")
    public List<Long> getRecommendations(   @PathVariable Long userId,
                                            @RequestParam(defaultValue = "5") int top) {
        return cosineSimilarityService.recommendMovies(userId, top);
    }

    /**
     * Get hybrid recommendations for a user.
     * 
     * @param userId    ID of the user to get recommendations for.
     * @param top       Number of top recommendations to return (default is 5).
     * @return          List of recommended movie IDs.
     */
       @GetMapping("/hybrid/{userId}")
    public List<Long> getHybridRecommendations( @PathVariable Long userId,
                                                @RequestParam(defaultValue = "5") int top) {
        return cosineSimilarityService.recommendMoviesHybrid(userId, top);
    }
}