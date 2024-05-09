package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.mapstruct.ConversationMapper;
import org.restaurant.model.ConversationEntity;
import org.restaurant.repository.ConversationRepository;
import org.restaurant.request.ConversationRequest;
import org.restaurant.response.ConversationResponse;
import org.restaurant.response.RestaurantConversationsResponse;
import org.restaurant.util.ConversationUtil;
import org.restaurant.validator.ObjectsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService{
    private final ConversationRepository conversationRepository;
    private final ObjectsValidator<ConversationRequest> conversationRequestObjectsValidator;
    private final ConversationUtil conversationUtil;
    @Override
    public ResponseEntity<?> postConversation(ConversationRequest conversationRequest) {
        conversationRequestObjectsValidator.validate(conversationRequest);
        ConversationEntity conversationEntity = ConversationMapper.INSTANCE.conversationRequestToConversationEntity(conversationRequest);
        conversationEntity.setArchived(false);
        conversationRepository.save(conversationEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<List<ConversationResponse>> getAllConversations(String token) {
        Long userId = conversationUtil.getUsersId(token);
        List<ConversationResponse> conversationResponses = conversationRepository
                .findByUserId(userId)
                .stream()
                .map(ConversationMapper.INSTANCE::conversationEntityToConversationResponse)
                .toList();

        return ResponseEntity.ok(conversationResponses);
    }
    @Override
    public ResponseEntity<Set<RestaurantConversationsResponse>> getAllRestaurantsWithConversationByUser(String token) {
        Long userId = conversationUtil.getUsersId(token);
        Set<Long> restaurantIds = conversationRepository.findDistinctRestaurantIdsByUserId(userId);
        Set<RestaurantConversationsResponse> restaurantNames = conversationUtil.getRestaurantNames(restaurantIds);
        return ResponseEntity.ok(restaurantNames);
    }

}
