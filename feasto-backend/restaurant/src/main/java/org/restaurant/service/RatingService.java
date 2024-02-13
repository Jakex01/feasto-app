package org.restaurant.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.restaurant.mapstruct.dto.MapStructMapper;
import org.restaurant.model.RatingEntity;
import org.restaurant.repository.RatingRepository;
import org.restaurant.request.PostRatingRequest;
import org.restaurant.response.RatingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    public ResponseEntity<?> postRating(PostRatingRequest postRatingRequest) {
        ratingRepository.save(MapStructMapper.INSTANCE.requestRatingToRatingEntity(postRatingRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<List<RatingEntity>> getRatingByRestaurantId(Long restaurantId) {
       List<RatingEntity> ratingEntityList =  ratingRepository
                .findAllByRestaurantRestaurantId(restaurantId);
       return ResponseEntity.ok(ratingEntityList);
    }

    public ResponseEntity<RatingResponse> getAverageRatingByRestaurantId(Long restaurantId, double averageRating) {
//        RatingResponse ratingResponse=  ratingRepository
//                .findAllByRestaurantRestaurantId(restaurantId)
//                .stream()
//                .min(Comparator.comparingDouble(rating->
//                        rating.getRating() - averageRating))
//                .map(MapStructMapper.INSTANCE::ratingEntityToRatingResponse)
//                .orElse(null);

        return ResponseEntity.ok(null);
    }
}
