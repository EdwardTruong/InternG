package com.example.cache_example_with_ehcache.appilication.dto;

import java.util.Set;

import com.example.cache_example_with_ehcache.appilication.utils.ERole;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegistert {
    String fullName;
    String username;
    String password;
    String email;
    String phoneNumber;
    Long avatarId;
    String description;
    Boolean allowLogin;
    int status;
    Set<ERole> roles;
}
