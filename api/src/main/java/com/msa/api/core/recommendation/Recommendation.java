package com.msa.api.core.recommendation;

import lombok.*;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Recommendation {
  private int productId;
  private int recommendationId;
  private String author;
  private int rate;
  private String content;
  private String serviceAddress;
}
