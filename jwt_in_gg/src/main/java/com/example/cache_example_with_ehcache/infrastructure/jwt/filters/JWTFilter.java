package com.example.cache_example_with_ehcache.infrastructure.jwt.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.example.cache_example_with_ehcache.infrastructure.jwt.TokenProvider;
import com.example.cache_example_with_ehcache.infrastructure.jwt.TokenProviderDuc;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * Bị lỗi !
 * Cái này gọi ngay sau khi đã có login thành công.
 */
@Slf4j
public class JWTFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Của ku Đức gửi
    @Autowired
    TokenProviderDuc tokenProvider;

    // Của mình
    @Autowired
    TokenProvider jwtUtils;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        log.debug("Token: {}", jwt);

        // if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        // Authentication authentication = this.tokenProvider.getAuthentication(jwt);
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // }
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
