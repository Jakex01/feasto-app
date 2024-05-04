package org.restaurant.service;

import org.restaurant.request.LocationRequest;
import org.restaurant.response.LocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface LocationService {
     ResponseEntity<LocationResponse> createLocation(LocationRequest request, Authentication authentication);

     ResponseEntity<?> updateLocation(Long id, Authentication principal);
}
