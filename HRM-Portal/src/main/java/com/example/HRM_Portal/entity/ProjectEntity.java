package com.example.HRM_Portal.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Project")
@Entity
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Constructor with ID
    public ProjectEntity(Long id) {
        this.id = id;

    }
//    // Constructor with ID
//    public ProjectEntityName(String name) {
//        this.name = name;
//
//    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskManagementEntity> tasks = new ArrayList<>();

}
