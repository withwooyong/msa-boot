package com.msa.core.product;

import com.msa.api.core.product.Product;
import com.msa.core.product.persistence.ProductEntity;
import com.msa.core.product.services.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */

public class MapperTests {

  private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

  @Test
  public void mapperTests() {

    assertNotNull(mapper);

    Product api = new Product(1, "n", 1, "sa");

    ProductEntity entity = mapper.apiToEntity(api);

    assertEquals(api.getProductId(), entity.getProductId());
    assertEquals(api.getProductId(), entity.getProductId());
    assertEquals(api.getName(), entity.getName());
    assertEquals(api.getWeight(), entity.getWeight());

    Product api2 = mapper.entityToApi(entity);

    assertEquals(api.getProductId(), api2.getProductId());
    assertEquals(api.getProductId(), api2.getProductId());
    assertEquals(api.getName(),      api2.getName());
    assertEquals(api.getWeight(),    api2.getWeight());
    assertNull(api2.getServiceAddress());
  }
}
