package com.msa.core.review.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer> {

  @Transactional(readOnly = true)
  List<ReviewEntity> findByProductId(int productId);
}
