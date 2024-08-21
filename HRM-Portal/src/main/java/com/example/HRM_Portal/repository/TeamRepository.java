package com.example.HRM_Portal.repository;

import com.example.HRM_Portal.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findByOurUsers_BusinessId(UUID businessId);
}
