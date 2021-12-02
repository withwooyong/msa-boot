package com.msa.core.recommendation.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
public interface RecommendationRepository extends CrudRepository<RecommendationEntity, String> {
  List<RecommendationEntity> findByProductId(int productId);
}
