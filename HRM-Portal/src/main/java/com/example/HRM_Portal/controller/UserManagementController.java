package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.dto.ReqRes;
import com.example.HRM_Portal.entity.OurUsers;
import com.example.HRM_Portal.entity.Entity;
import com.example.HRM_Portal.service.UsersManagementService;
import com.example.HRM_Portal.service.RabbitMQProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RestController
public class UserManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    private UsersManagementService usersManagementService;

    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        LOGGER.info("Registration request received for email: {}", reg.getEmail());
        ReqRes response = usersManagementService.register(reg);
        if(response != null){
            Entity rabbit = new Entity(); // Assuming Entity is correct
            rabbit.setName(reg.getCompanyName());
            rabbit.setPassword(reg.getPassword());
            rabbit.setEmail(reg.getEmail());

            rabbitMQProducerService.sendJsonMessage(rabbit);
            LOGGER.info("User registered and message sent to RabbitMQ for email: {}", reg.getEmail());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        LOGGER.info("Login request received for email: {}", req.getEmail());
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    @GetMapping("/admin/get-users/{businessId}")
    public ResponseEntity<ReqRes> getUserByID(@PathVariable UUID businessId) {
        return ResponseEntity.ok(usersManagementService.getUsersById(businessId));
    }

    @PutMapping("/admin/update/{businessId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable UUID businessId, @RequestBody OurUsers reqres) {
        return ResponseEntity.ok(usersManagementService.updateUser(businessId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        LOGGER.info("Fetching profile for user with email: {}", email);
        ReqRes response = usersManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{businessId}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable UUID businessId) {
        LOGGER.info("Delete request received for user with ID: {}", businessId);
        return ResponseEntity.ok(usersManagementService.deleteUser(businessId));
    }
}
