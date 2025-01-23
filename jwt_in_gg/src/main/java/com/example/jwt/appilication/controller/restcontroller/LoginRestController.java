package com.example.cache_example_with_ehcache.appilication.controller.restcontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cache_example_with_ehcache.appilication.dto.LoginRequest;
import com.example.cache_example_with_ehcache.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/home")
@RestController
public class LoginRestController {

    public final static String HEADER_NAME = "Authorization";
    public final static String HEADER_VALUE = "Bearer ";
    public final static String LOGIN_SUCCESS = "Login success and jwt created : ";

    UserService userService;
    // Cũ
    // @PostMapping("/login")
    // public ResponseEntity<String> login(@Valid @RequestBody UserLogin userLogin)
    // throws io.jsonwebtoken.io.IOException, UnrecoverableKeyException,
    // KeyStoreException,
    // NoSuchAlgorithmException, CertificateException, FileNotFoundException,
    // IOException {
    // if (userLogin.getUsername().equals("admin") &&
    // userLogin.getPassword().equals("123")) {
    // HttpHeaders headers = new HttpHeaders();
    // String jwt = userService.login(userLogin);
    // headers.add(HEADER_NAME, HEADER_VALUE + jwt);
    // return new ResponseEntity<>(jwt, headers, HttpStatus.OK);
    // } else {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Đăng nhập không
    // thành công");
    // }
    // }

    // Cái của dự án cũ
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
            throws io.jsonwebtoken.io.IOException, UnrecoverableKeyException, KeyStoreException,
            NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
        String jwt = userService.login(loginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME, HEADER_VALUE + jwt);
        headers.get(HEADER_NAME);
        log.debug(LOGIN_SUCCESS + HEADER_NAME + "," + HEADER_VALUE + "," + jwt);
        return new ResponseEntity<>(jwt, headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout() {
        return "login";
    }
}