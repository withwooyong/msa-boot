package com.msa.core.product.services;

import com.msa.api.core.product.Product;
import com.msa.api.core.product.ProductService;
import com.msa.core.product.persistence.ProductEntity;
import com.msa.core.product.persistence.ProductRepository;
import com.msa.util.exceptions.InvalidInputException;
import com.msa.util.exceptions.NotFoundException;
import com.msa.util.http.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@Slf4j
@RestController
public class ProductServiceImpl implements ProductService {

  private final ServiceUtil serviceUtil;

  private final ProductRepository repository;

  private final ProductMapper mapper;

  @Autowired
  public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, ServiceUtil serviceUtil) {
    this.repository = repository;
    this.mapper = mapper;
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Product createProduct(Product body) {
    try {
      ProductEntity entity = mapper.apiToEntity(body);
      ProductEntity newEntity = repository.save(entity);

      log.debug("createProduct: entity created for productId: {}", body.getProductId());
      return mapper.entityToApi(newEntity);

    } catch (DuplicateKeyException dke) {
      throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId());
    }
  }

  @Override
  public Product getProduct(int productId) {

    if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

    ProductEntity entity = repository.findByProductId(productId)
            .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));

    Product response = mapper.entityToApi(entity);
    response.setServiceAddress(serviceUtil.getServiceAddress());

    log.debug("getProduct: found productId: {}", response.getProductId());

    return response;
  }

  @Override
  public void deleteProduct(int productId) {
    log.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
    repository.findByProductId(productId).ifPresent(e -> repository.delete(e));
  }
}