package com.example.HRM_Portal.repository;


import com.example.HRM_Portal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmpId(String empId);
}
