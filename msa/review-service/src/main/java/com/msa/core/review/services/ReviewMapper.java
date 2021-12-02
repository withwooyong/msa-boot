package com.msa.core.review.services;

import com.msa.api.core.review.Review;
import com.msa.core.review.persistence.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author WOOYONG
 * @since 2021-12-02
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper {

  @Mappings({
          @Mapping(target = "serviceAddress", ignore = true)
  })
  Review entityToApi(ReviewEntity entity);

  @Mappings({
          @Mapping(target = "id", ignore = true),
          @Mapping(target = "version", ignore = true)
  })
  ReviewEntity apiToEntity(Review api);

  List<Review> entityListToApiList(List<ReviewEntity> entity);

  List<ReviewEntity> apiListToEntityList(List<Review> api);
}