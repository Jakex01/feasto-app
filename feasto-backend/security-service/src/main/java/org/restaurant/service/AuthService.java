package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.model.UserCredentialEntity;
import org.restaurant.repository.UserCredentialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public String saveUser(UserCredentialEntity credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        userCredentialRepository.save(credential);
        return "worth";
    }
}
