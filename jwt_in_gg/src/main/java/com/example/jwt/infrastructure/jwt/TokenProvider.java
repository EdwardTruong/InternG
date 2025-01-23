package com.example.jwt.infrastructure.jwt;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.example.jwt.appilication.constant.ContantUnit.TIME;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {

    /*
     * Với phiêm bản cũ thì việc sử dụng SignatureAlgorithm.HS256 bên trong
     * signWith(key) của jwt sẽ không còn thành công nữa. Sẽ đưa lỗi error(s) wrong
     * key.
     * Vì vậy sẽ dùng qua SignatureAlgorithm lấy bộ mã sau đó mới sử dụng key để
     * auto gennerated
     */
    // Symmetrical Key Using genarate random key
    SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    SecretKey key = Keys.secretKeyFor(algorithm);

    // thêm từ code của Đức cho từ dự án cũ :
    private static final String AUTHORITIES_KEY = "auth";

    /** */
    // Using for login // OLD code of jwt symmetrical
    // public String generateJwt(Authentication authentication) {
    // UserDetails userAuthenticated = (UserDetails) authentication.getPrincipal();
    // return
    // Jwts.builder().setSubject((userAuthenticated.getUsername())).setIssuedAt(new
    // Date())
    // .setExpiration(new Date((new Date()).getTime() + TIME.EXPIRE_DURATION_JWT))//
    // 6h
    // .signWith(key, SignatureAlgorithm.HS256).compact();
    // }

    // generate JWT token
    public String generateToken(Authentication authentication, boolean rememberMe) {

        // mới ở đây.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + TIME.REMEMBERME);
        } else {
            validity = new Date(now + TIME.EXPIRE_DURATION_JWT);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .setExpiration(validity)
                .compact();

        // mới ở đây

    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Using for change password
    public String generateTokenFromUsername(String email) {

        log.info("Key calling form generateTokenFromUsername : " + key.toString());

        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + TIME.EXPIRE_DURATION_JWT_CHANGE_PASSWORD)) // 15m
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    // Using for change password: Giải mã JWT và lấy thông tin claims
    public String getUserNameFromJwt(String toke) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(toke).getBody().getSubject();
    }

    public boolean validateToken(String authToken)
            throws SignatureException,
            NoSuchAlgorithmException, java.io.IOException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    // thêm vào từ code của ku đức, từ dự án cũ.
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}