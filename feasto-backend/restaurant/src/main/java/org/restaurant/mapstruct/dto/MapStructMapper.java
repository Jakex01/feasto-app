package org.restaurant.mapstruct.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.restaurant.mapstruct.dto.RestaurantEntityDTO;
import org.restaurant.model.LocationEntity;
import org.restaurant.model.RatingEntity;
import org.restaurant.model.RestaurantEntity;
import org.restaurant.request.CreateRestaurantRequest;
import org.restaurant.request.PostLocationRequest;
import org.restaurant.request.PostRatingRequest;
import org.restaurant.response.LocationResponse;
import org.restaurant.response.RatingResponse;
import org.restaurant.response.RestaurantResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);

    RestaurantEntity requestToEntity(CreateRestaurantRequest request);


    RatingEntity requestRatingToRatingEntity(PostRatingRequest request);

    RestaurantEntityDTO restaurantToRestaurantDto(RestaurantEntity restaurantEntity);

    LocationEntity locationRequestToLocationEntity(PostLocationRequest postLocationRequest);


    List<LocationResponse> locationEntityToLocationResponse(List<LocationEntity> locationEntity);


    RestaurantResponse restaurantEntityToRestaurantResponse(RestaurantEntity restaurantEntity);
//    @Mapping(target = "rating", ignore = false)
//    RatingResponse ratingEntityToRatingResponse (RatingEntity ratingEntity);
}