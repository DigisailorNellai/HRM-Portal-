package com.example.HRM_Portal.repository;

import com.example.HRM_Portal.entity.TaskManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskManagementRepository extends JpaRepository<TaskManagementEntity,Long>{
        }