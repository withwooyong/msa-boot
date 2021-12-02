package com.msa.api.core.product;

import lombok.*;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
  private int productId;
  private String name;
  private int weight;
  private String serviceAddress;
}
