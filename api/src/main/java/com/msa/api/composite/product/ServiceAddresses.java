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
public class ServiceAddresses {
  private String cmp;
  private String pro;
  private String rev;
  private String rec;
}
