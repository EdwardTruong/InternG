package com.example.cache_example_with_ehcache.domain.service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;

import com.example.cache_example_with_ehcache.appilication.dto.LoginRequest;
import com.example.cache_example_with_ehcache.appilication.dto.UserDto;
import com.example.cache_example_with_ehcache.appilication.dto.UserRegistert;

public interface UserService {
    // Optional<UserDto> findById(Long id);
    Optional<UserDto> findById(Long id);

    UserDto save(UserRegistert register);

    int passwordRegisterdErrors(String password);

    // boolean emailExist(String email);

    // UserEntity findByEmail(String email);

    // String activeAccount(UserEntity userEntity);

    String login(LoginRequest login) throws io.jsonwebtoken.io.IOException,
            UnrecoverableKeyException, KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException,
            java.io.IOException;
}
