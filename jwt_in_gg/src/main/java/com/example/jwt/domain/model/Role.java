package com.example.jwt.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_DESCRIPTION = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, length = MAX_LENGTH_NAME)
    private String name;

    @Column(name = "description", length = MAX_LENGTH_DESCRIPTION)
    private String description;

    // @OneToMany
    // List<UserPermission> Concurrent;
}
