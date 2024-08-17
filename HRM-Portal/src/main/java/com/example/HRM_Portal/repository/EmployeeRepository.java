package com.example.HRM_Portal.repository;

import com.example.HRM_Portal.entity.Employee;
import com.example.HRM_Portal.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByOurUsers(OurUsers ourUsers);

    Optional<Employee> findByIdAndOurUsers(Long id, OurUsers ourUsers);

    void deleteByIdAndOurUsers(Long id, OurUsers ourUsers);
}
