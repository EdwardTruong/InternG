package com.example.cache_example_with_ehcache.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cache_example_with_ehcache.domain.model.UserEntity;;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndStatus(String username, int status);

    boolean findByEmailAndStatus(String username, int status);

    boolean existsByUsernameAndStatus(String username, int status);

}