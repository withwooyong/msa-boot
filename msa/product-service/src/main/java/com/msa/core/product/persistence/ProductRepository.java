package com.msa.core.product.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {
  Optional<ProductEntity> findByProductId(int productId);
}