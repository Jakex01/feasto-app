package org.restaurant.service;

import org.restaurant.request.LocationRequest;
import org.restaurant.request.UpdateLocationRequest;
import org.restaurant.response.LocationResponse;
import org.springframework.http.ResponseEntity;

public interface LocationService {
    public ResponseEntity<LocationResponse> createLocation(LocationRequest request);

    public ResponseEntity<?> updateLocation(UpdateLocationRequest updateLocationRequest);
}
