package com.msa.core.product.services;

import com.msa.api.core.product.Product;
import com.msa.core.product.persistence.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mappings({@Mapping(target = "serviceAddress", ignore = true)})
  Product entityToApi(ProductEntity entity);

  @Mappings({@Mapping(target = "id", ignore = true), @Mapping(target = "version", ignore = true)})
  ProductEntity apiToEntity(Product api);
}
