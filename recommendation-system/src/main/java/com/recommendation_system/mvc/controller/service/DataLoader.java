package com.recommendation_system.mvc.controller.service;

import java.io.InputStreamReader;
import java.io.Reader;
import org.springframework.stereotype.Service;

import com.recommendation_system.mvc.model.entity.Movie;

import au.com.bytecode.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;

@Service
public class DataLoader {

    private final MovieRepository movieRepository;

    /**
     * Constructor with dependency injection.
     * 
     * @param movieRepository   Repository for Movie entity.
     */
    public DataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Load initial data from CSV file into the MongoDB database.
     */
    @PostConstruct
    public void loadData() {
        try (Reader reader = new InputStreamReader(
                getClass().getResourceAsStream("/data/movies.csv"));
             CSVReader csvReader    = new CSVReader(reader)) {

            String[] line;
            boolean firstLine       = true;

            // Skip header line.
            while ((line = csvReader.readNext()) != null) {
                if (firstLine) {
                    firstLine       = false;
                    continue;
                }
                Movie movie         = new Movie(line[1], line[2]);
                if(movieRepository != null)
                    movieRepository.save(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}