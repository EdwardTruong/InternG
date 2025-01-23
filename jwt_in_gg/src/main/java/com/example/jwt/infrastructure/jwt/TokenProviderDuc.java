package com.example.jwt.infrastructure.jwt;

import org.springframework.stereotype.Component;

import com.example.jwt.appilication.constant.ContantUnit.TIME;

// package vn.tcx.dw.security.jwt;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
// import javax.annotation.PostConstruct;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

// import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * Cái này của thằng ku đức đưa. Đang test cái này để xem nó có chạy được ngon
 * không nếu như không sử dụng onePerFilter
 */
@Component
@Slf4j
public class TokenProviderDuc {

    // private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    // private Key key;

    // private long tokenValidityInMilliseconds;

    // private long tokenValidityInMillisecondsForRememberMe;

    // private final JHipsterProperties jHipsterProperties;

    // public TokenProvider(JHipsterProperties jHipsterProperties) {
    // this.jHipsterProperties = jHipsterProperties;
    // }

    // @PostConstruct
    // public void init() {
    // byte[] keyBytes;
    // String secret =
    // jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
    // if (!StringUtils.isEmpty(secret)) {
    // log.warn("Warning: the JWT key used is not Base64-encoded. " +
    // "We recommend using the `jhipster.security.authentication.jwt.base64-secret`
    // key for optimum security.");
    // keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    // } else {
    // log.debug("Using a Base64-encoded JWT secret key");
    // keyBytes = Decoders.BASE64
    // .decode(jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret());
    // }
    // this.key = Keys.hmacShaKeyFor(keyBytes);
    // this.tokenValidityInMilliseconds = 1000
    // *
    // jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
    // this.tokenValidityInMillisecondsForRememberMe = 1000
    // * jHipsterProperties.getSecurity().getAuthentication().getJwt()
    // .getTokenValidityInSecondsForRememberMe();
    // }

    SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    SecretKey key = Keys.secretKeyFor(algorithm);

    public String createToken(Authentication authentication, boolean rememberMe) {
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
                // .signWith(key, SignatureAlgorithm.HS512)
                .signWith(key)
                .setExpiration(validity)
                .compact();
    }

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

    // public boolean validateToken(String authToken) {
    // try {
    // Jwts.parserBuilder().setSigningKey(key).parseClaimsJws(authToken);
    // return true;
    // } catch (JwtException | IllegalArgumentException e) {
    // log.info("Invalid JWT token.");
    // log.trace("Invalid JWT token trace.", e);
    // }
    // return false;
    // }

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
}