package com.example.HRM_Portal.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Column(nullable = false)
    private UUID businessId;

    // Constructor with ID
    public ProjectEntity(Long id) {
        this.id = id;

    }
    public ProjectEntity(Long id, String name, UUID businessId) {
        this.id = id;
        this.name = name;
        this.businessId = businessId;
    }


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskManagementEntity> tasks = new ArrayList<>();

}
