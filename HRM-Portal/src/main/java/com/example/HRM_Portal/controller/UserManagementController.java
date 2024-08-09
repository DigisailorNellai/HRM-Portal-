package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.dto.ReqRes;
import com.example.HRM_Portal.entity.OurUsers;
import com.example.HRM_Portal.model.User;
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

@RestController
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;
    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        ReqRes response = usersManagementService.register(reg);
        if(response != null){
            Entity rabbit = new Entity();
            rabbit.setName(reg.getName());
            rabbit.setPassword(reg.getPassword());
            rabbit.setEmail(reg.getEmail());

            rabbitMQProducerService.sendJsonMessage(rabbit);
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }


}