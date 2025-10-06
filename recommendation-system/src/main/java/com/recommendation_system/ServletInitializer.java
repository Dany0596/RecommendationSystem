package com.recommendation_system;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for the Recommendation System application.
 * 
 * @author Daniele Vencato
 * @since 2024-06
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RecommendationSystemApplication.class);
	}
}
