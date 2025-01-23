package com.example.jwt.appilication.dto;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserClientDto {
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
}
