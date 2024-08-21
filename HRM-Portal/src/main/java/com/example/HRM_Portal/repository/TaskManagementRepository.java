package com.example.HRM_Portal.repository;

import com.example.HRM_Portal.entity.TaskManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskManagementRepository extends JpaRepository<TaskManagementEntity, Long> {
        List<TaskManagementEntity> findByProjectIdAndBusinessId(Long projectId, UUID businessId);
        Optional<TaskManagementEntity> findByIdAndBusinessId(Long id, UUID businessId);


}
