package com.msa.api.composite.product;

import lombok.*;

import java.util.List;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductAggregate {
  private int productId;
  private String name;
  private int weight;
  private List<RecommendationSummary> recommendations;
  private List<ReviewSummary> reviews;
  private ServiceAddresses serviceAddresses;
}
