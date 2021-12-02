package com.msa.composite.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.api.core.product.Product;
import com.msa.api.core.product.ProductService;
import com.msa.api.core.recommendation.Recommendation;
import com.msa.api.core.recommendation.RecommendationService;
import com.msa.api.core.review.Review;
import com.msa.api.core.review.ReviewService;
import com.msa.util.exceptions.InvalidInputException;
import com.msa.util.exceptions.NotFoundException;
import com.msa.util.http.HttpErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {

  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  private final String productServiceUrl;
  private final String recommendationServiceUrl;
  private final String reviewServiceUrl;

  @Autowired
  public ProductCompositeIntegration(
          RestTemplate restTemplate,
          ObjectMapper mapper,

          @Value("${app.product-service.host}") String productServiceHost,
          @Value("${app.product-service.port}") int productServicePort,

          @Value("${app.recommendation-service.host}") String recommendationServiceHost,
          @Value("${app.recommendation-service.port}") int recommendationServicePort,

          @Value("${app.review-service.host}") String reviewServiceHost,
          @Value("${app.review-service.port}") int reviewServicePort
  ) {

    this.restTemplate = restTemplate;
    this.mapper = mapper;

    productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product";
    recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation";
    reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review";
  }

  @Override
  public Product createProduct(Product body) {

    try {
      String url = productServiceUrl;
      log.debug("Will post a new product to URL: {}", url);

      Product product = restTemplate.postForObject(url, body, Product.class);
      assert product != null;
      log.debug("Created a product with id: {}", product.getProductId());

      return product;
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  @Override
  public Product getProduct(int productId) {

    try {
      String url = productServiceUrl + "/" + productId;
      log.debug("Will call the getProduct API on URL: {}", url);

      Product product = restTemplate.getForObject(url, Product.class);
      assert product != null;
      log.debug("Found a product with id: {}", product.getProductId());

      return product;
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  @Override
  public void deleteProduct(int productId) {
    try {
      String url = productServiceUrl + "/" + productId;
      log.debug("Will call the deleteProduct API on URL: {}", url);

      restTemplate.delete(url);
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  @Override
  public Recommendation createRecommendation(Recommendation body) {

    try {
      String url = recommendationServiceUrl;
      log.debug("Will post a new recommendation to URL: {}", url);

      Recommendation recommendation = restTemplate.postForObject(url, body, Recommendation.class);
      assert recommendation != null;
      log.debug("Created a recommendation with id: {}", recommendation.getProductId());

      return recommendation;
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  @Override
  public List<Recommendation> getRecommendations(int productId) {

    try {
      String url = recommendationServiceUrl + "?productId=" + productId;

      log.debug("Will call the getRecommendations API on URL: {}", url);
      List<Recommendation> recommendations = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {
      }).getBody();

      assert recommendations != null;
      log.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
      return recommendations;
    } catch (Exception ex) {
      log.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public void deleteRecommendations(int productId) {
    try {
      String url = recommendationServiceUrl + "?productId=" + productId;
      log.debug("Will call the deleteRecommendations API on URL: {}", url);

      restTemplate.delete(url);
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  @Override
  public Review createReview(Review body) {

    try {
      String url = reviewServiceUrl;
      log.debug("Will post a new review to URL: {}", url);

      Review review = restTemplate.postForObject(url, body, Review.class);
      assert review != null;
      log.debug("Created a review with id: {}", review.getProductId());

      return review;
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  @Override
  public List<Review> getReviews(int productId) {

    try {
      String url = reviewServiceUrl + "?productId=" + productId;

      log.debug("Will call the getReviews API on URL: {}", url);
      List<Review> reviews = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<List<Review>>() {
      }).getBody();

      assert reviews != null;
      log.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
      return reviews;
    } catch (Exception ex) {
      log.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public void deleteReviews(int productId) {
    try {
      String url = reviewServiceUrl + "?productId=" + productId;
      log.debug("Will call the deleteReviews API on URL: {}", url);

      restTemplate.delete(url);
    } catch (HttpClientErrorException ex) {
      throw handleHttpClientException(ex);
    }
  }

  private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
    switch (ex.getStatusCode()) {

      case NOT_FOUND:
        return new NotFoundException(getErrorMessage(ex));

      case UNPROCESSABLE_ENTITY:
        return new InvalidInputException(getErrorMessage(ex));

      default:
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
  }

  private String getErrorMessage(HttpClientErrorException ex) {
    try {
      return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
    } catch (IOException ioex) {
      return ex.getMessage();
    }
  }
}
