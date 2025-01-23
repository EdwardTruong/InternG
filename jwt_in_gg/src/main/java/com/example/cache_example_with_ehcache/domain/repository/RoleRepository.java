package com.example.cache_example_with_ehcache.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cache_example_with_ehcache.appilication.utils.ERole;
import com.example.cache_example_with_ehcache.domain.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
