package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.entity.Employee;
import com.example.HRM_Portal.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping("/empId/{empId}")
    public ResponseEntity<Employee> getEmployeeByEmpId(@PathVariable @Pattern(regexp = "DS[0-9]{3}", message = "Employee ID must be in the format DS000 to DS999") String empId) {
        Employee employee = employeeService.getEmployeeByEmpId(empId);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestParam("name") String name,
            @Valid @RequestParam("email") String email,
            @Valid @RequestParam("department") String department,
            @Valid @RequestParam("dob") String dob,
            @Valid @RequestParam("phoneNumber") String phoneNumber,
            @Valid @RequestParam("baseSalary") Double baseSalary,
            @Valid @RequestParam("address") String address,
            @Valid @RequestParam("empId") @Pattern(regexp = "DS[0-9]{3}", message = "Employee ID must be in the format DS000 to DS999") String empId,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile,
            @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCardFile,
            @RequestParam(value = "panCard", required = false) MultipartFile panCardFile) throws IOException {

        Employee employee = new Employee();
        employee.setEmpId(empId);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setDob(dob);
        employee.setPhoneNumber(phoneNumber);
        employee.setBaseSalary(baseSalary);
        employee.setAddress(address);

        if (resumeFile != null && !resumeFile.isEmpty()) {
            employee.setResume(resumeFile.getBytes());
        }
        if (aadharCardFile != null && !aadharCardFile.isEmpty()) {
            employee.setAadharCard(aadharCardFile.getBytes());
        }
        if (panCardFile != null && !panCardFile.isEmpty()) {
            employee.setPanCard(panCardFile.getBytes());
        }

        try {
            Employee savedEmployee = employeeService.createOrUpdateEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("department") String department,
            @RequestParam("dob") String dob,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("baseSalary") Double baseSalary,
            @RequestParam("address") String address,
            @RequestParam("empId") @Pattern(regexp = "DS[0-9]{3}", message = "Employee ID must be in the format DS000 to DS999") String empId,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile,
            @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCardFile,
            @RequestParam(value = "panCard", required = false) MultipartFile panCardFile) throws IOException {

        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        employee.setEmpId(empId);
        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);
        employee.setDob(dob);
        employee.setPhoneNumber(phoneNumber);
        employee.setBaseSalary(baseSalary);
        employee.setAddress(address);

        if (resumeFile != null && !resumeFile.isEmpty()) {
            employee.setResume(resumeFile.getBytes());
        }
        if (aadharCardFile != null && !aadharCardFile.isEmpty()) {
            employee.setAadharCard(aadharCardFile.getBytes());
        }
        if (panCardFile != null && !panCardFile.isEmpty()) {
            employee.setPanCard(panCardFile.getBytes());
        }

        try {
            Employee updatedEmployee = employeeService.createOrUpdateEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
