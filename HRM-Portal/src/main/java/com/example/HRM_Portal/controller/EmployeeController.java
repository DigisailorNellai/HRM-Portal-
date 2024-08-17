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
import java.util.UUID;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id, @RequestParam("businessId") String businessId) {
        Employee employee = employeeService.getEmployeeById(id, UUID.fromString(businessId));
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestParam("name") String name,
            @Valid @RequestParam("email") String email,
            @Valid @RequestParam("gender") String gender,
            @Valid @RequestParam("department") String department,
            @Valid @RequestParam("dob") String dob,
            @Valid @RequestParam("phoneNumber") String phoneNumber,
            @Valid @RequestParam("baseSalary") Double baseSalary,
            @Valid @RequestParam("address") String address,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile,
            @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCardFile,
            @RequestParam(value = "panCard", required = false) MultipartFile panCardFile,
            @RequestParam(value = "collectedDocuments", required = false) List<String> collectedDocuments,
            @RequestParam("businessId") String businessId) throws IOException {

        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDepartment(department);
        employee.setDob(dob);
        employee.setPhoneNumber(phoneNumber);
        employee.setBaseSalary(baseSalary);
        employee.setAddress(address);
        employee.setCollectedDocuments(collectedDocuments);
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
            Employee savedEmployee = employeeService.createOrUpdateEmployee(employee, UUID.fromString(businessId));
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("department") String department,
            @RequestParam("dob") String dob,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("baseSalary") Double baseSalary,
            @RequestParam("address") String address,
            @RequestParam("empId") @Pattern(regexp = "DS[0-9]{3}", message = "Employee ID must be in the format DS000 to DS999") String empId,
            @RequestParam(value = "resume", required = false) MultipartFile resumeFile,
            @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCardFile,
            @RequestParam(value = "panCard", required = false) MultipartFile panCardFile,
            @RequestParam(value = "collectedDocuments", required = false) List<String> collectedDocuments,
            @RequestParam("businessId") String businessId) throws IOException {

        Employee employee = employeeService.getEmployeeById(id, UUID.fromString(businessId));
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        employee.setEmpId(empId);
        employee.setName(name);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDepartment(department);
        employee.setDob(dob);
        employee.setPhoneNumber(phoneNumber);
        employee.setBaseSalary(baseSalary);
        employee.setAddress(address);
        employee.setCollectedDocuments(collectedDocuments);

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
            Employee updatedEmployee = employeeService.createOrUpdateEmployee(employee, UUID.fromString(businessId));
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id, @RequestParam("businessId") String businessId) {
        try {
            Employee employee = employeeService.getEmployeeById(id, UUID.fromString(businessId));
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + id);
            }

            employeeService.deleteEmployee(id, UUID.fromString(businessId));
            return ResponseEntity.ok("Employee deleted successfully with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the employee.");
        }
    }

    @DeleteMapping("/employee/{id}/collected-documents")
    public ResponseEntity<Employee> deleteCollectedDocument(
            @PathVariable Long id,
            @RequestParam("businessId") String businessId,
            @RequestParam("documentName") String documentName) {
        try {
            Employee updatedEmployee = employeeService.removeCollectedDocument(id, documentName, UUID.fromString(businessId));
            if (updatedEmployee == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam("businessId") String businessId) {
        List<Employee> employees = employeeService.getAllEmployees(UUID.fromString(businessId));
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employee/{id}/resume")
    public ResponseEntity<byte[]> viewResume(@PathVariable Long id, @RequestParam("businessId") String businessId) {
        Employee employee = employeeService.getEmployeeById(id, UUID.fromString(businessId));
        if (employee == null || employee.getResume() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=resume.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(employee.getResume());
    }

    @GetMapping("/employee/{id}/pan")
    public ResponseEntity<byte[]> viewPanCard(@PathVariable Long id, @RequestParam("businessId") String businessId) {
        Employee employee = employeeService.getEmployeeById(id, UUID.fromString(businessId));
        if (employee == null || employee.getPanCard() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=pan_card.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(employee.getPanCard());
    }

    @GetMapping("/employee/{id}/aadhar")
    public ResponseEntity<byte[]> viewAadharCard(@PathVariable Long id, @RequestParam("businessId") String businessId) {
        Employee employee = employeeService.getEmployeeById(id, UUID.fromString(businessId));
        if (employee == null || employee.getAadharCard() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=aadhar_card.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(employee.getAadharCard());
    }
}
