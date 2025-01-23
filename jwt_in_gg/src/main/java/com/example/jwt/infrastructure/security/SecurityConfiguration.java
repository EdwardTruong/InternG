package com.example.jwt.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jwt.infrastructure.jwt.AuthEntryPointJwt;
import com.example.jwt.infrastructure.jwt.filters.JWTFilter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @Explain : Trong class SecurityConfiguration : sẽ có lớp filter :
 * 
 *          1. AuthTokenFilter extends OncePerRequestFilter
 *          Đảm bảo bộ lọc chỉ được thực thi một lần trong một request (ngay cả
 *          khi có các forward hoặc include).
 *          Thường được sử dụng trong Spring Security cho các bộ lọc quan trọng
 *          như JwtFilter
 * 
 * 
 * 
 *          2. JWTFilter extends GenericFilterBean (Muốn tích hợp logic của bạn
 *          vào chuỗi filter của Spring Security mà không cần xử lý các chi tiết
 *          kỹ thuật phức tạp của Servlet API.)
 *          Cho phép bộ lọc được gọi nhiều lần trong một request (nếu chuỗi
 *          filter yêu cầu).
 *          Đơn giản hơn và linh hoạt.
 * 
 *          Muốn dùng 1 trong 2 thì tắt thằng còn lại
 *          *************************************************************************
 *          Nhớ sử dụng : SecurityFilterChain HttpSecurity Dòng 108 - 109
 *          http.authenticationProvider(authenticationProvider());
 *          http.addFilterBefore(authenticationJwtTokenFilter(),
 *          UsernamePasswordAuthenticationFilter.class);
 */

@Configuration
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SecurityConfiguration {

	/**
	 * This class call user when login with method loadByUsername(String username)
	 */
	UserDetailsServiceImpl userDetailsService;

	/**
	 * If jwt have errors this object will be called
	 */
	AuthEntryPointJwt unauthorizedHandler;

	// My own
	// @Bean
	// public AuthTokenFilter authenticationJwtTokenFilter() {
	// return new AuthTokenFilter();
	// }

	// // Old real object
	@Bean
	public JWTFilter authenticationJwtTokenFilter() {
		return new JWTFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/home/**").permitAll() // login and logout here
						.requestMatchers(HttpMethod.GET, "/api/home/info/**").hasAnyRole("ADMIN", "USER")
						.requestMatchers("/api/user/**").hasRole("USER")
						.requestMatchers("/api/test/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
