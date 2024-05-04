package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.mapstruct.LocationMapper;
import org.restaurant.model.LocationEntity;
import org.restaurant.model.UserCredentialEntity;
import org.restaurant.repository.LocationRepository;
import org.restaurant.repository.UserCredentialRepository;
import org.restaurant.request.LocationRequest;
import org.restaurant.request.UpdateLocationRequest;
import org.restaurant.response.LocationResponse;
import org.restaurant.validator.ObjectsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private final UserCredentialRepository userCredentialRepository;
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
    public ResponseEntity<?> updateLocation(Long id, Authentication authentication) {
        UserCredentialEntity principal = (UserCredentialEntity) authentication.getPrincipal();
        UserCredentialEntity userEntity = userCredentialRepository.findById(principal.getId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        List<LocationEntity> locationEntities = locationRepository.findAllByUserCredentialEntity(userEntity);
        locationEntities.forEach(t->{
                    t.setCurrent(Objects.equals(t.getId(), id));
                });
        locationRepository.saveAll(locationEntities);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
