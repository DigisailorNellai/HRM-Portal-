package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.OurUsers;
import com.example.HRM_Portal.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Convert OurUsers to UserDetails
        OurUsers ourUser = usersRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return User.builder()
                .username(ourUser.getEmail())  // Assuming email is used as username
                .password(ourUser.getPassword()) // Make sure password is encoded
                .roles(ourUser.getRole()) // Set roles if applicable
                .build();
    }

    public OurUsers getUserByBusinessId(UUID businessId) {
        return usersRepo.findById(businessId)
                .orElseThrow(() -> new RuntimeException("User not found with businessId: " + businessId));
    }

    public OurUsers getUserByEmail(String email) {
        return usersRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
