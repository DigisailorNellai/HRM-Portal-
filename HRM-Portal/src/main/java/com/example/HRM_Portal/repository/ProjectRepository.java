package com.example.HRM_Portal.repository;

import com.example.HRM_Portal.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByBusinessId(UUID businessId);
    Optional<ProjectEntity> findByIdAndBusinessId(Long id, UUID businessId);

}

