package com.example.jwt.appilication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.appilication.dto.UserDto;
import com.example.jwt.appilication.dto.UserRegistert;
import com.example.jwt.domain.service.UserService;
import com.example.jwt.infrastructure.exception.UserNotFoundException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AdminController {
    UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("with id : " + userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("save")
    public ResponseEntity<UserDto> saveNew(@RequestBody UserRegistert register) {
        return new ResponseEntity<>(userService.save(register), HttpStatus.OK);
    }

}
