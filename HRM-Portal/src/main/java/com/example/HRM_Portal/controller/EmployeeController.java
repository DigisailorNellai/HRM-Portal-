package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.entity.Employee;
import com.example.HRM_Portal.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @PostMapping("/employee")
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
            String resumePath = saveFile(resumeFile);
            employee.setResumePath(resumePath);
        }
        if (aadharCardFile != null && !aadharCardFile.isEmpty()) {
            String aadharCardPath = saveFile(aadharCardFile);
            employee.setAadharCardPath(aadharCardPath);
        }
        if (panCardFile != null && !panCardFile.isEmpty()) {
            String panCardPath = saveFile(panCardFile);
            employee.setPanCardPath(panCardPath);
        }

        try {
            Employee savedEmployee = employeeService.createOrUpdateEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("employee/{id}")
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
            String resumePath = saveFile(resumeFile);
            employee.setResumePath(resumePath);
        }
        if (aadharCardFile != null && !aadharCardFile.isEmpty()) {
            String aadharCardPath = saveFile(aadharCardFile);
            employee.setAadharCardPath(aadharCardPath);
        }
        if (panCardFile != null && !panCardFile.isEmpty()) {
            String panCardPath = saveFile(panCardFile);
            employee.setPanCardPath(panCardPath);
        }

        try {
            Employee updatedEmployee = employeeService.createOrUpdateEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + id);
            }

            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the employee.");
        }
    }

    @GetMapping("employee/{id}/resume")
    public ResponseEntity<byte[]> viewResume(@PathVariable Long id) throws IOException {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null || employee.getResumePath() == null) {
            return ResponseEntity.notFound().build();
        }
        Path filePath = Paths.get(employee.getResumePath());
        byte[] resume = Files.readAllBytes(filePath);
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=resume.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(resume);
    }

    @GetMapping("employee/{id}/pan")
    public ResponseEntity<byte[]> viewPanCard(@PathVariable Long id) throws IOException {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null || employee.getPanCardPath() == null) {
            return ResponseEntity.notFound().build();
        }
        Path filePath = Paths.get(employee.getPanCardPath());
        byte[] panCard = Files.readAllBytes(filePath);
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=pan_card.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(panCard);
    }

    @GetMapping("employee/{id}/aadhar")
    public ResponseEntity<byte[]> viewAadharCard(@PathVariable Long id) throws IOException {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null || employee.getAadharCardPath() == null) {
            return ResponseEntity.notFound().build();
        }
        Path filePath = Paths.get(employee.getAadharCardPath());
        byte[] aadharCard = Files.readAllBytes(filePath);
        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=aadhar_card.pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(aadharCard);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, filename);
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }
}
