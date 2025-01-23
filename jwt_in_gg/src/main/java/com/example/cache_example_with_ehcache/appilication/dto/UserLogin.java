package com.example.cache_example_with_ehcache.appilication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserLogin {
    @NotBlank(message = "Yêu cầu username")
    private String username;

    @NotBlank(message = "Yêu cầu password")
    private String password;

}
