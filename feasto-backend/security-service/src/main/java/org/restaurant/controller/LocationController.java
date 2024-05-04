package org.restaurant.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.restaurant.request.LocationRequest;
import org.restaurant.response.LocationResponse;
import org.restaurant.service.LocationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

    private final LocationServiceImpl locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(
            @RequestBody LocationRequest locationRequest
    ) {
        return locationService.createLocation(locationRequest);
    }
    @PatchMapping
    public ResponseEntity<?> updateLocation(
            @RequestParam @NonNull Long id,
            Authentication principal
    ){
        return locationService.updateLocation(id, principal);
    }
}
