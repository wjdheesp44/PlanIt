package com.gt.planit.security.config;

import com.gt.planit.security.jwt.JWTUtil;
import com.gt.planit.security.jwt.JwtAuthenticationFilter;
import com.gt.planit.security.jwt.JwtAuthorizationFilter;
import com.gt.planit.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        http.addFilterAt(jwtAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter loginFilter =
                new JwtAuthenticationFilter(authenticationManager(), jwtUtil);
        loginFilter.setFilterProcessesUrl("/login");

        JwtAuthorizationFilter authorizationFilter =
                new JwtAuthorizationFilter(jwtUtil, userDetailsService);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/auth/refresh").permitAll()
                .anyRequest().authenticated()
        );

        http.csrf(csrf -> csrf.disable());

        http.addFilter(loginFilter)
            .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
