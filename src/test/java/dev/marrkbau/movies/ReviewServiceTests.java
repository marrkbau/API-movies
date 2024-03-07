package dev.marrkbau.movies;

import dev.marrkbau.movies.Movie;
import dev.marrkbau.movies.Review;
import dev.marrkbau.movies.repositories.ReviewRepository;
import dev.marrkbau.movies.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ReviewServiceTests {

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private MongoTemplate mongoTemplate;

	@InjectMocks
	private ReviewService reviewService;

	@Test
	public void testCreateReview() {
		// Mock data
		String reviewBody = "Great movie!";
		String imdbId = "tt1234567";
		LocalDateTime now = LocalDateTime.now();

		// Mock dependencies
		Review review = new Review(reviewBody, now, now);
		when(reviewRepository.insert(any(Review.class))).thenReturn(review);

		ArgumentCaptor<Query> queryCaptor = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> updateCaptor = ArgumentCaptor.forClass(Update.class);

		// Call the method
		Map<String, String> payload = new HashMap<>();
		payload.put("reviewBody", reviewBody);
		payload.put("imdbId", imdbId);
		reviewService.createReview(reviewBody, imdbId);

		// Verify interactions
		verify(reviewRepository, times(1)).insert(any(Review.class));
		verify(mongoTemplate, times(1)).updateFirst(queryCaptor.capture(), updateCaptor.capture(), eq(Movie.class));

		// Check the values passed to the update method
		Query capturedQuery = queryCaptor.getValue();
		assertNotNull(capturedQuery);
		assertEquals("{\"imdbId\" : \"" + imdbId + "\"}", capturedQuery.getQueryObject().toJson());

		Update capturedUpdate = updateCaptor.getValue();
		assertNotNull(capturedUpdate);
		// Add assertions for update content if needed
	}

}

