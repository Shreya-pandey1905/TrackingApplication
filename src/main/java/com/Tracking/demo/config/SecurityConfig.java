package com.Tracking.demo.config;

import com.Tracking.demo.customJwt.JwtService;
import com.Tracking.demo.filter.JwtAuthFilter;
import com.Tracking.demo.serviceImpl.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain getSecurity(
            HttpSecurity httpSecurity,
            JwtService jwtService,
            CustomUserService customUserService,
            AuthenticationEntryPoint authenticationEntryPoint) {

        JwtAuthFilter jwtAuthFilter=new JwtAuthFilter(jwtService, customUserService);
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers(
                        "/api/super-admin/login",
                        "/api/admin/login",
                        "/api/trainer/login",
                        "/api/students/login"
                ).permitAll()
                .requestMatchers("/api/super-admin/**").hasRole("SUPER_ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/trainer/**").hasRole("TRAINER")
                .requestMatchers("/api/students/**").hasRole("STUDENT")

                .anyRequest()
                .authenticated()
        );

        httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.exceptionHandling(ex ->
                ex.authenticationEntryPoint(authenticationEntryPoint)
        );
        httpSecurity.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncrypt(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserService customUserService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider(customUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);

    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"Status\":401,\"error\":Unauthorized\","
                    +"\"message\":\"Missing or invalid bearer token\"}");
        };

    }}
