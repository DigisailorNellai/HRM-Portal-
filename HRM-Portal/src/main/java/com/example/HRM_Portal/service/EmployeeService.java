package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.Employee;
import com.example.HRM_Portal.entity.OurUsers;
import com.example.HRM_Portal.repository.EmployeeRepository;
import com.example.HRM_Portal.service.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees(UUID businessId) {
        OurUsers ourUsers = ourUserDetailsService.getUserByBusinessId(businessId);
        return employeeRepository.findByOurUsers(ourUsers);
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id, UUID businessId) {
        OurUsers ourUsers = ourUserDetailsService.getUserByBusinessId(businessId);
        return employeeRepository.findByIdAndOurUsers(id, ourUsers).orElse(null);
    }

    @Transactional
    public Employee createOrUpdateEmployee(Employee employee, UUID businessId) {
        OurUsers ourUsers = ourUserDetailsService.getUserByBusinessId(businessId);
        employee.setOurUsers(ourUsers);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id, UUID businessId) {
        OurUsers ourUsers = ourUserDetailsService.getUserByBusinessId(businessId);
        employeeRepository.deleteByIdAndOurUsers(id, ourUsers);
    }

    @Transactional
    public Employee addCollectedDocuments(Long id, List<String> newDocuments, UUID businessId) {
        Employee employee = getEmployeeById(id, businessId);
        if (employee != null) {
            employee.getCollectedDocuments().addAll(newDocuments);
            return employeeRepository.save(employee);
        }
        throw new RuntimeException("Employee not found");
    }

    @Transactional
    public Employee removeCollectedDocument(Long id, String documentName, UUID businessId) {
        Employee employee = getEmployeeById(id, businessId);
        if (employee != null) {
            boolean removed = employee.getCollectedDocuments().remove(documentName);
            if (removed) {
                return employeeRepository.save(employee);
            } else {
                throw new RuntimeException("Document not found");
            }
        }
        throw new RuntimeException("Employee not found");
    }
}
