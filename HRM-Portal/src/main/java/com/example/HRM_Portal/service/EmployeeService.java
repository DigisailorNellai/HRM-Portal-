package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee createOrUpdateEmployee(Employee employee);
    void deleteEmployee(Long id);
}
