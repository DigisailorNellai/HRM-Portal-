package com.example.HRM_Portal.entity;

import lombok.Data;

@Data
public class Status {
    private String response;


    // Default Constructor (required by Lombok for @Data)
    public Status() {}

    // Constructor with all fields except 'city' and 'role' (optional)
    public Status(String response) {
        this.response = response;

    }


}