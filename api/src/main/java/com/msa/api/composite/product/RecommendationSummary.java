package com.msa.api.composite.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendationSummary {
  private int recommendationId;
  private String author;
  private int rate;
  private String content;
}
