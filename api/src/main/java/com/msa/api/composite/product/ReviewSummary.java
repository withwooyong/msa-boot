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
public class ReviewSummary {
  private int reviewId;
  private String author;
  private String subject;
  private String content;
}
