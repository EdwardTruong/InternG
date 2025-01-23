// package com.example.cache_example_with_ehcache.infrastructure.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import
// org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import
// org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// import
// com.example.cache_example_with_ehcache.infrastructure.jwt.filters.AuthTokenFilter;

// // import
// com.example.cache_example_with_ehcache.infrastructure.jwt.filters.AuthTokenFilter;

// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import lombok.extern.slf4j.Slf4j;

// @Configuration
// @EnableWebSecurity
// @Slf4j
// @FieldDefaults(makeFinal = true, level = AccessLevel.PACKAGE)
// @AllArgsConstructor
// public class SecurityConfigurer {

// UserDetailsServiceImpl userDetailsService;

// @Bean
// public AuthTokenFilter authenticationJwtTokenFilter() {
// return new AuthTokenFilter();
// }

// @Bean
// public DaoAuthenticationProvider authenticationProvider() {
// DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
// authProvider.setUserDetailsService(userDetailsService);
// authProvider.setPasswordEncoder(passwordEncoder());
// return authProvider;
// }

// @Bean
// public AuthenticationManager
// authenticationManager(AuthenticationConfiguration authConfig) throws
// Exception {
// return authConfig.getAuthenticationManager();
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

// http.csrf(csrf -> csrf.disable())
// .authorizeHttpRequests(requests -> requests
// .requestMatchers("/api/auth/**").permitAll()
// .requestMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN")
// .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
// // .addFilterBefore
// .anyRequest().authenticated())
// // .formLogin(form -> form
// // .loginProcessingUrl("/login")
// // .defaultSuccessUrl("/home", true)
// // .permitAll())

// .logout(logout -> {
// logout.invalidateHttpSession(true);
// logout.logoutRequestMatcher(new
// AntPathRequestMatcher("/logout")).permitAll();
// // logout.logoutSuccessUrl("/");
// logout.deleteCookies("JSESSIONID");
// });
// http.authenticationProvider(authenticationProvider());
// http.addFilterBefore(authenticationJwtTokenFilter(),
// UsernamePasswordAuthenticationFilter.class);
// return http.build();
// }

// }
