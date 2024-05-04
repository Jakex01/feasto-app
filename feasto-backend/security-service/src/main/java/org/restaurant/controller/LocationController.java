package org.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.restaurant.request.LocationRequest;
import org.restaurant.request.UpdateLocationRequest;
import org.restaurant.response.LocationResponse;
import org.restaurant.service.LocationServiceImpl;
import org.springframework.http.ResponseEntity;
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
            @RequestBody UpdateLocationRequest updateLocationRequest
    ){
        return locationService.updateLocation(updateLocationRequest);
    }
}
