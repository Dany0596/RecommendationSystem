package com.recommendation_system.mvc.controller.service;

import com.recommendation_system.mvc.model.entity.Movie;
import com.recommendation_system.mvc.model.entity.Rating;

import jakarta.annotation.PostConstruct;

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
    private static final int TOP_USER_SIMILARITY    = 5;   // Number of top similar users to consider.
    private static final double CF_WEIGHT           = 0.7; // Weight for collaborative filtering.
    private Map<Long, double[]> movieFeatures       = new HashMap<Long, double[]>();    // Movie features for content-based filtering (movieId -> feature vector).

    // Repository for accessing ratings data.
    private final RatingRepository ratingRepository;        // Repository for accessing ratings data.
    private final MovieRepository movieRepo;                // Repository for accessing movie data.

    /**
     * Constructor with dependency injection.
     * 
     * @param ratingRepository   Repository for Rating entity.
     * @param movieRepo           Repository for Movie entity.
     */
    public CosineSimilarityService(RatingRepository ratingRepository, MovieRepository movieRepo) {
        this.ratingRepository    = ratingRepository;
        this.movieRepo           = movieRepo;
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

    /**
     * Initialize movie features from genres for content-based filtering.
     * This method is called after the service is constructed.
     */
    @PostConstruct
    public void initMovieFeatures() {
        List<Movie> movies              = movieRepo.findAll();
        Set<String> allGenres           = movies.stream()
                                                    .flatMap(m -> Arrays.stream(m.getGenres().split("\\|")))
                                                    .collect(Collectors.toSet());
        List<String> genreList          = new ArrayList<String>(allGenres);

        // Create binary feature vectors for each movie based on genres.
        for(Movie mov : movies) {

            // Initialize feature vector.
            double[] vector             = new double[genreList.size()];
            String[] genres             = mov.getGenres().split("\\|");

            // Set genre presence in the feature vector.
            for (String gen : genres) {
                int idx                 = genreList.indexOf(gen);
                if (idx >= 0)
                    vector[idx]  = 1d;
            }
            movieFeatures.put(mov.getMovieId(), vector);
        }
    }

    /**
     * Calculate cosine similarity between two feature vectors.
     *
     * @param vec1  the first feature vector
     * @param vec2  the second feature vector
     * @return      the cosine similarity between the two vectors
     */
    private double vectorCosineSimilarity(double[] vec1, double[] vec2) {
        double dot  = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            dot     += vec1[i] * vec2[i];
            normA   += vec1[i] * vec1[i];
            normB   += vec2[i] * vec2[i];
        }
        return (normA == 0 || normB == 0) ? 0 : dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * Hybrid recommendation combining collaborative and content-based filtering.
     *
     * @param userId    ID of the target user.
     * @param topN      Number of top recommendations to return.
     * @return          List of recommended movie IDs.
     */
    public List<Long> recommendMoviesHybrid(Long userId, int topN) {
        if(userId == null || topN <= 0)
            return Collections.emptyList();
        
        // Collaborative Filtering.
        List<Long> cfRecommendations    = this.recommendMovies(userId, topN * 2);

        // Content-Based Filtering.
        Map<Long, Double> cbScores      = new HashMap<Long, Double>();
        for (Long movieIdA : cfRecommendations) {
            double[] featuresA          = movieFeatures.get(movieIdA);
            if (featuresA == null)
                continue;

            // Calculate similarity with all other movies.
            for (Map.Entry<Long, double[]> entry : movieFeatures.entrySet()) {
                Long movieIdB           = entry.getKey();
                if (movieIdA.equals(movieIdB))
                    continue;

                double sim              = this.vectorCosineSimilarity(featuresA, entry.getValue());
                cbScores.merge(movieIdB, sim, Double::sum);
            }
        }

        // Combine CF and CB scores.
        Map<Long, Double> hybridScores = new HashMap<>();
        for (Long movieId : movieFeatures.keySet()) {

            // CF score is 1 if in CF recommendations, else 0.
            double cfScore              = cfRecommendations.contains(movieId) ? 1.0 : 0.0;

            // CB score from content-based filtering.
            double cbScore              = cbScores.getOrDefault(movieId, 0.0);

            // Weighted combination of CF and CB scores.
            double finalScore           = CosineSimilarityService.CF_WEIGHT * cfScore + (1 - CosineSimilarityService.CF_WEIGHT) * cbScore;
            hybridScores.put(movieId, finalScore);
        }

        // Return top N recommendations.
        return hybridScores.entrySet().stream()
                                        .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                                        .limit(topN)
                                        .map(Map.Entry::getKey)
                                        .toList();
    }
}