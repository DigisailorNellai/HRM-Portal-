package com.example.HRM_Portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.example.HRM_Portal.entity.OurUsers;

public interface UsersRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
}
