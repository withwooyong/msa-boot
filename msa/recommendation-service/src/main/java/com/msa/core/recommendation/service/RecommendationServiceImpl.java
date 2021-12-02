package com.msa.core.recommendation.service;

import com.msa.api.core.recommendation.Recommendation;
import com.msa.api.core.recommendation.RecommendationService;
import com.msa.core.recommendation.persistence.RecommendationEntity;
import com.msa.core.recommendation.persistence.RecommendationRepository;
import com.msa.util.exceptions.InvalidInputException;
import com.msa.util.http.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@Slf4j
@RestController
public class RecommendationServiceImpl implements RecommendationService {

  private final RecommendationRepository repository;

  private final RecommendationMapper mapper;

  private final ServiceUtil serviceUtil;

  @Autowired
  public RecommendationServiceImpl(RecommendationRepository repository, RecommendationMapper mapper, ServiceUtil serviceUtil) {
    this.repository = repository;
    this.mapper = mapper;
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Recommendation createRecommendation(Recommendation body) {
    try {
      RecommendationEntity entity = mapper.apiToEntity(body);
      RecommendationEntity newEntity = repository.save(entity);

      log.debug("createRecommendation: created a recommendation entity: {}/{}", body.getProductId(), body.getRecommendationId());
      return mapper.entityToApi(newEntity);

    } catch (DuplicateKeyException dke) {
      throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Recommendation Id:" + body.getRecommendationId());
    }
  }

  @Override
  public List<Recommendation> getRecommendations(int productId) {

    if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

    List<RecommendationEntity> entityList = repository.findByProductId(productId);
    List<Recommendation> list = mapper.entityListToApiList(entityList);
    list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

    log.debug("getRecommendations: response size: {}", list.size());

    return list;
  }

  @Override
  public void deleteRecommendations(int productId) {
    log.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
    repository.deleteAll(repository.findByProductId(productId));
  }
}
