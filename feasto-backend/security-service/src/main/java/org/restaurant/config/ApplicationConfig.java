package org.restaurant.config;

import lombok.RequiredArgsConstructor;
import org.restaurant.repository.UserCredentialRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserCredentialRepository userCredentialRepository;

    @Bean
    public UserDetailsService userDetailsService(){

        return  username -> userCredentialRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));

    }

}
