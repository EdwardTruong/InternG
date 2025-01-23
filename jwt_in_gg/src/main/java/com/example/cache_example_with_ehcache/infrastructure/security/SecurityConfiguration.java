package com.example.cache_example_with_ehcache.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.cache_example_with_ehcache.infrastructure.jwt.AuthEntryPointJwt;
import com.example.cache_example_with_ehcache.infrastructure.jwt.filters.AuthTokenFilter;
// import com.example.cache_example_with_ehcache.infrastructure.jwt.filters.JWTFilter;
import com.example.cache_example_with_ehcache.infrastructure.jwt.filters.JWTFilter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

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
