package org.restaurant.service;

import org.restaurant.request.LocationRequest;
import org.restaurant.request.UpdateLocationRequest;
import org.restaurant.response.LocationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LocationService {
    public ResponseEntity<LocationResponse> createLocation(LocationRequest request);

    public ResponseEntity<?> updateLocation(Long id, Authentication principal);
}
