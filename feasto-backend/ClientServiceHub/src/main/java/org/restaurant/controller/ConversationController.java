package org.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.restaurant.request.ConversationRequest;
import org.restaurant.response.ConversationResponse;
import org.restaurant.response.RestaurantConversationsResponse;
import org.restaurant.service.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    @GetMapping
    public ResponseEntity<List<ConversationResponse>> getAllConversationsByRestaurantAndUser(@RequestHeader(value = "Authorization") String token){
        return conversationService.getAllConversationsByRestaurantAndUser(token);
    }
    @PostMapping
    public ResponseEntity<?> postConversation(@RequestBody ConversationRequest conversationRequest){
        return conversationService.postConversation(conversationRequest);
    }
    @GetMapping
    public ResponseEntity<Set<RestaurantConversationsResponse>> getAllRestaurantsWithConversationByUser(@RequestHeader(value = "Authorization") String token){
        return conversationService.getAllRestaurantsWithConversationByUser(token);
    }
}
