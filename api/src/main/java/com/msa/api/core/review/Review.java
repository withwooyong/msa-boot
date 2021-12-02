package com.msa.api.core.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Review {
  private int productId;
  private int reviewId;
  private String author;
  private String subject;
  private String content;
  private String serviceAddress;
}
