package com.example.HRM_Portal.repository;

import com.example.HRM_Portal.entity.HolidayEntity;
import org.hibernate.sql.exec.spi.JdbcParameterBindings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayEntity,Long>{




}