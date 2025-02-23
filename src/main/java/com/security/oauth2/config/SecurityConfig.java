package com.security.oauth2.config;

import com.security.oauth2.entity.Role;
import com.security.oauth2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        return http
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()

                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**",
                        "/js/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.
                        USER.name())
                .anyRequest().authenticated()

                .and()

                .logout()
                .logoutSuccessUrl("/")

                .and()

                .oauth2Login()
                .userInfoEndpoint()
                .userService(userService)
                .and().and().build();
    }
}
