package com.recommendation_system.mvc.controller.service;

import com.recommendation_system.mvc.model.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for calculating movie recommendations using Cosine Similarity.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
@Service
public class CosineSimilarityService {
    private static final int TOP_USER_SIMILARITY = 5;   // Number of top similar users to consider.

    // Repository for accessing ratings data.
    private final RatingRepository ratingRepository;

    /**
     * Constructor with dependency injection.
     * 
     * @param ratingRepository   Repository for Rating entity.
     */
    public CosineSimilarityService(RatingRepository ratingRepository) {
        this.ratingRepository   = ratingRepository;
    }

    /**
     * Calculate cosine similarity between two users based on their ratings.
     * 
     * @param user          Ratings of user (movieId -> rating).
     * @param otherUser     Ratings of other user (movieId -> rating).
     * @return              Cosine similarity score.
     */
    private double cosineSimilarity(Map<Long, Double> user, Map<Long, Double> otherUser) {
        Set<Long> commonMovies      = new HashSet<>(user.keySet());

        // Find movies rated by both users.
        commonMovies.retainAll(otherUser.keySet());

        //  If no common movies, similarity is 0.
        if(commonMovies.isEmpty())
            return 0.0;

        double dotProduct           = 0.0;
        double userNorm             = 0.0;
        double otherUserNorm        = 0.0;

        // Calculate dot product and norms
        for(Long movieId : commonMovies) {
            double userRate         = user.get(movieId);
            double otherUserRate    = otherUser.get(movieId);
            dotProduct              += userRate * otherUserRate;
        }

        // Calculate norms for each user
        for(double userRate : user.values())
            userNorm                += userRate * userRate;
        for(double otherUserRate : otherUser.values())
            otherUserNorm           += otherUserRate * otherUserRate;

        // Avoid division by zero
        if(userNorm == 0 || otherUserNorm == 0)
            return 0.0;

        // Return cosine similarity
        return dotProduct / (Math.sqrt(userNorm) * Math.sqrt(otherUserNorm));
    }

    /**
     * Recommend movies for a target user based on ratings from similar users.
     * 
     * @param targetUserId      ID of the target user.
     * @param topN              Number of top recommendations to return.
     * @return                  List of recommended movie IDs.
     */
    public List<Long> recommendMovies(Long targetUserId, int topNFilms) {
        if(targetUserId == null || topNFilms <= 0)
            return Collections.emptyList();
        List<Rating> allRatings                  = ratingRepository.findAll();

        // Group ratings by userId.
        Map<Long, Map<Long, Double>> userRatings = allRatings.stream()
                                                              .collect(Collectors.groupingBy(   Rating::getUserId,
                                                                                                Collectors.toMap(Rating::getMovieId, Rating::getRating, (a, b) -> b)));
        // Retrieve ratings of the target user.
        Map<Long, Double> targetRatings          = userRatings.get(targetUserId);
        if(targetRatings == null)
            return Collections.emptyList();
        
        //  Calculate similarities with other users.
        Map<Long, Double> similarities          = new HashMap<Long, Double>();
        for(Map.Entry<Long, Map<Long, Double>> entry : userRatings.entrySet()) {
            Long otherUserId                    = entry.getKey();   
            if(!targetUserId.equals(otherUserId)) {
                double sim                      = this.cosineSimilarity(targetRatings, entry.getValue());
                similarities.put(otherUserId, sim);
            }
        }

        // Get top 10 most similar users.
        List<Long> mostSimilarUsers             = similarities.entrySet().stream()
                                                                            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                                                                            .limit(CosineSimilarityService.TOP_USER_SIMILARITY)
                                                                            .map(Map.Entry::getKey)
                                                                            .toList();

        Set<Long> seenMovies                    = targetRatings.keySet();
        Map<Long, Double> candidateScores       = new HashMap<Long, Double>();

        // Aggregate scores from similar users for unseen movies.
        for(Long similarUserId : mostSimilarUsers) {
            double sim                          = similarities.get(similarUserId);
            Map<Long, Double> otherRatings      = userRatings.get(similarUserId);

            // Consider only movies not yet rated by the target user.
            for(Map.Entry<Long, Double> entry : otherRatings.entrySet()) {
                Long movieId                    = entry.getKey();
                if(!seenMovies.contains(movieId))

                    // Weighted score by similarity
                    candidateScores.merge(movieId, sim * entry.getValue(), Double::sum);
                
            }
        }

        // Return top N recommended movie IDs.
        return candidateScores.entrySet().stream()
                                            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                                            .limit(topNFilms)
                                            .map(Map.Entry::getKey)
                                            .toList();
    }
}