package com.example.jwt.domain.service.impl;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.appilication.constant.ContantUnit.*;
import com.example.jwt.appilication.dto.LoginRequest;
import com.example.jwt.appilication.dto.UserDto;
import com.example.jwt.appilication.dto.UserRegistert;
import com.example.jwt.appilication.mapper.UserMapper;
import com.example.jwt.domain.model.Role;
import com.example.jwt.domain.model.UserEntity;
import com.example.jwt.domain.repository.RoleRepository;
import com.example.jwt.domain.repository.UserRepository;
import com.example.jwt.domain.service.UserService;
import com.example.jwt.infrastructure.jwt.TokenProvider;
import com.example.jwt.infrastructure.jwt.TokenProviderDuc;
import com.example.jwt.infrastructure.security.UserDetailsCustom;

import io.jsonwebtoken.io.IOException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder encoder;
    UserMapper userMapper;
    RoleRepository roleRepository;
    AuthenticationManager authenticationManager;
    TokenProvider tokenProvider;
    TokenProviderDuc tokenProviderDuc;

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(e -> userMapper.toDto(e));
    }

    @Override
    public UserDto save(UserRegistert register) {

        // Check if the email already exists
        if (userRepository.findByEmailAndStatus(register.getEmail(), STATUS.ACTIVE)) {
            throw new RuntimeException(MESSENGER_ERROR.USER_EXIST);
        }

        if (this.passwordRegisterdErrors(register.getPassword()) != 0) {
            throw new RuntimeException(MESSENGER_ERROR.PASSOWRD_REGISTER_ERROR);
        } // Validate the password

        Set<Role> roles = new HashSet<>();
        register.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(roles::add);

        UserEntity entity = UserEntity.builder()
                .fullName(register.getFullName())
                .username(register.getUsername())
                .password(encoder.encode(register.getPassword()))
                .email(register.getEmail())
                .phoneNumber(register.getPhoneNumber())
                .avatarId(register.getAvatarId())
                .description(register.getDescription())
                .roles(roles)
                .build();

        return userMapper.toDto(userRepository.save(entity));
    }

    @Override
    public String login(LoginRequest loginRequest) throws IOException, UnrecoverableKeyException, KeyStoreException,
            NoSuchAlgorithmException, CertificateException, java.io.IOException, java.io.IOException {

        UsernamePasswordAuthenticationToken userTryLogin = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(userTryLogin);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // String jwt = tokenProvider.generateToken(authentication, true);
        String jwt = tokenProviderDuc.createToken(authentication, true);

        UserDetailsCustom userDetails = (UserDetailsCustom) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // JwtResponse newJwt =
        // JwtResponse.builder().token(jwt).email(userDetails.getUsername()).type("Bearer")
        // .id(userDetails.getId()).roles(roles).build();

        return jwt;
    }

    /**
     * return jwt
     */
    // @Override
    // public String login(LoginRequest login)
    // throws io.jsonwebtoken.io.IOException, UnrecoverableKeyException,
    // KeyStoreException,
    // NoSuchAlgorithmException, CertificateException, IOException,
    // java.io.IOException {
    // UserDetails userLoginSuccess =
    // userDetailsService.loadUserByUsername(login.getUsername());
    // UsernamePasswordAuthenticationToken userTryLogin = new
    // UsernamePasswordAuthenticationToken(
    // userLoginSuccess.getUsername(), userLoginSuccess.getPassword());
    // Authentication authentication =
    // authenticationManager.authenticate(userTryLogin);
    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // String jwt = tokenProvider.generateJwt(authentication);
    // return jwt;
    // }

    @Override
    public int passwordRegisterdErrors(String password) {
        boolean isNumberInside = false;
        boolean isLowerCaseInside = false;
        boolean isUpperCaseInside = false;
        boolean isSpecialCharacters = false;
        int count = 0;

        for (Character ch : password.toCharArray()) {
            if (Character.isLowerCase(ch))
                isLowerCaseInside = true;
            else if (Character.isUpperCase(ch))
                isUpperCaseInside = true;
            else if (Character.isDigit(ch))
                isNumberInside = true;
            else if (PASSWORD.SPECIAL_CHACTERLIST.contains(ch))
                isSpecialCharacters = true;
        }

        if (!isNumberInside)
            count++;
        if (!isLowerCaseInside)
            count++;
        if (!isUpperCaseInside)
            count++;
        if (!isSpecialCharacters)
            count++;

        return Math.max(count, 8 - password.length());
    }

}
