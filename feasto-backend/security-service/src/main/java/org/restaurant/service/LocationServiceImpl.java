package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.mapstruct.LocationMapper;
import org.restaurant.model.LocationEntity;
import org.restaurant.repository.LocationRepository;
import org.restaurant.request.LocationRequest;
import org.restaurant.request.UpdateLocationRequest;
import org.restaurant.response.LocationResponse;
import org.restaurant.validator.ObjectsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private final ObjectsValidator<LocationRequest> locationRequestObjectsValidator;
    @Override
    public ResponseEntity<LocationResponse> createLocation(LocationRequest request) {

        locationRequestObjectsValidator.validate(request);

        LocationEntity locationEntity = LocationMapper.INSTANCE.locationRequestToLocationEntity(request);
        locationRepository.save(locationEntity);

        LocationResponse locationResponse = LocationMapper.INSTANCE.locationEntityToLocationResponse(locationEntity);

        return ResponseEntity.ok().body(locationResponse);

    }

    @Override
    public ResponseEntity<?> updateLocation(UpdateLocationRequest updateLocationRequest) {
        return null;
    }
}
