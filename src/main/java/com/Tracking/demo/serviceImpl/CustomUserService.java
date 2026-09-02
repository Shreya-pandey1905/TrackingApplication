package com.Tracking.demo.serviceImpl;

import com.Tracking.demo.entity.User;
import com.Tracking.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userAuthEntity = userRepo.findByEmail(username).get();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(userAuthEntity.getEmail())
                .password(userAuthEntity.getPassword())
                .roles(userAuthEntity.getRole().name())
                .build();

        return userDetails;
    }
}