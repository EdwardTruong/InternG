package com.example.jwt.appilication.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String fullName;
    String username;
    String email;
    String phoneNumber;
    Long avatarId;
    BigDecimal sortOrder;
    String description;
    Boolean allowLogin;
    int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean isSuperAdmin;

}