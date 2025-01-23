package com.example.cache_example_with_ehcache.domain.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    public static final int MAX_LENGTH_USENAME = 50;
    public static final int MAX_LENGTH_EMAIL = 100;
    public static final int MAX_LENGTH_PHONE_NUMBER = 100;
    public static final int MAX_LENGTH_PASSWORD = 100;
    public static final int MAX_LENGTH_FULLTNAME = 100;
    public static final int MAX_LENGTH_DESCRIPTION = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Nationalized
    @Column(name = "full_name", nullable = false, length = MAX_LENGTH_FULLTNAME)
    String fullName;

    @NotNull
    @Column(name = "username", nullable = false, length = MAX_LENGTH_USENAME)
    String username;

    @NotNull
    @Column(name = "password", nullable = false, length = MAX_LENGTH_PASSWORD)
    String password;

    @NotNull
    @Column(name = "email", nullable = false, length = MAX_LENGTH_EMAIL)
    String email;

    @Column(name = "phone_number", length = MAX_LENGTH_PHONE_NUMBER)
    String phoneNumber;

    @JoinColumn(name = "avatar_id", nullable = true)
    Long avatarId;

    @JoinColumn(name = "avatar_id", nullable = true)
    int status;

    @NotNull
    @Column(name = "sort_order", nullable = false)
    BigDecimal sortOrder;

    @Column(name = "description", length = MAX_LENGTH_DESCRIPTION)
    String description;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "allow_login", nullable = false)
    Boolean allowLogin;

    @Column(name = "is_super_admin")
    Boolean isSuperAdmin = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    @Builder.Default
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

}