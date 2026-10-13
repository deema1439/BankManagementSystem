package com.example.bankmanagementsystem.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity

public class Configuration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //filter
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sm->
                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .authorizeHttpRequests(auth-> auth

                        .requestMatchers("/api/v1/customer/register",
                                "/api/v1/employee/register").permitAll()


                        .requestMatchers("/api/v1/auth/get-all",
                                "/api/v1/auth/add-admin",
                                "/api/v1/auth/update-by-admin/**",
                                "/api/v1/auth/delete-by-admin/**").hasAuthority("ADMIN")


                        .requestMatchers("/api/v1/auth/update",
                                "/api/v1/auth/delete").hasAnyAuthority("CUSTOMER", "EMPLOYEE", "ADMIN")


                        .requestMatchers("/api/v1/customer/get-all").hasAnyAuthority("ADMIN", "EMPLOYEE")


                        .requestMatchers("/api/v1/customer/my-info",
                                "/api/v1/customer/update",
                                "/api/v1/customer/delete").hasAuthority("CUSTOMER")
                                 .requestMatchers("/api/v1/employee/get-all").hasAuthority("ADMIN")

                                 .requestMatchers("/api/v1/employee/my-info",
                                        "/api/v1/employee/update",
                                        "/api/v1/employee/delete").hasAuthority("EMPLOYEE")

                                .requestMatchers("/api/v1/account/get-all",
                                        "/api/v1/account/activate/**",
                                        "/api/v1/account/block/**").hasAnyAuthority("ADMIN", "EMPLOYEE")


                                .requestMatchers("/api/v1/account/create",
                                        "/api/v1/account/update/**",
                                        "/api/v1/account/delete/**",
                                        "/api/v1/account/details/**",
                                        "/api/v1/account/my-accounts",
                                        "/api/v1/account/deposit/**",
                                        "/api/v1/account/withdraw/**",
                                        "/api/v1/account/transfer/**").hasAuthority("CUSTOMER")

                        .anyRequest().authenticated()
                )
                .logout(logout->logout
                        .logoutUrl("/api/v1/auth/logout")
                        .invalidateHttpSession(true)
                )
                .httpBasic(basic->{})
                .build();//هنا السيمكولين مجموعه من التاشين


    }
























}
