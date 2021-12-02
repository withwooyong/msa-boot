package com.msa.core.review.services;

import com.msa.api.core.review.Review;
import com.msa.api.core.review.ReviewService;
import com.msa.core.review.persistence.ReviewEntity;
import com.msa.core.review.persistence.ReviewRepository;
import com.msa.util.exceptions.InvalidInputException;
import com.msa.util.http.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@Slf4j
@RestController
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository repository;

  private final ReviewMapper mapper;

  private final ServiceUtil serviceUtil;

  @Autowired
  public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
    this.repository = repository;
    this.mapper = mapper;
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Review createReview(Review body) {
    try {
      ReviewEntity entity = mapper.apiToEntity(body);
      ReviewEntity newEntity = repository.save(entity);

      log.debug("createReview: created a review entity: {}/{}", body.getProductId(), body.getReviewId());
      return mapper.entityToApi(newEntity);
    } catch (DataIntegrityViolationException dive) {
      throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Review Id:" + body.getReviewId());
    }
  }

  @Override
  public List<Review> getReviews(int productId) {

    if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

    List<ReviewEntity> entityList = repository.findByProductId(productId);
    List<Review> list = mapper.entityListToApiList(entityList);
    list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

    log.debug("getReviews: response size: {}", list.size());

    return list;
  }

  @Override
  public void deleteReviews(int productId) {
    log.debug("deleteReviews: tries to delete reviews for the product with productId: {}", productId);
    repository.deleteAll(repository.findByProductId(productId));
  }
}