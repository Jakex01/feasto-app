package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.model.Role;
import org.restaurant.model.UserCredentialEntity;
import org.restaurant.repository.UserCredentialRepository;
import org.restaurant.request.AuthenticationRequest;
import org.restaurant.request.RegisterRequest;
import org.restaurant.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
    var user = UserCredentialEntity.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();

        userCredentialRepository.save(user);

    var jwtToken =  jwtService.generateToken(user);

    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user  = userCredentialRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken =  jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
