package com.example.HRM_Portal.entity;

import lombok.Data;

@Data
public class Entity {
    private String email;
    private String name;
    private String password;

    // Default Constructor (required by Lombok for @Data)
    public Entity() {}

    // Constructor with all fields except 'city' and 'role' (optional)
    public Entity(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    // Getter for full name
    public String getFullName() {
        return name;  // Assuming 'name' represents full name in this context
    }
}