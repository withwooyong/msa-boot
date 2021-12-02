package com.msa.core.product.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document(collection="products")
public class ProductEntity {

  @Id
  private String id;

  @Version
  private Integer version;

  @Indexed(unique = true)
  private int productId;

  private String name;
  private int weight;

  public ProductEntity(int productId, String name, int weight) {
    this.productId = productId;
    this.name = name;
    this.weight = weight;
  }
}
