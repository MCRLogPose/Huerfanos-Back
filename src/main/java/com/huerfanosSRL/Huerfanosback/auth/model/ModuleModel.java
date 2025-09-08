package com.huerfanosSRL.Huerfanosback.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "modules")
public class ModuleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String nameModule;

    private String description;

    @ManyToMany(mappedBy = "modules")
    private List<RoleModel> roles;
}

