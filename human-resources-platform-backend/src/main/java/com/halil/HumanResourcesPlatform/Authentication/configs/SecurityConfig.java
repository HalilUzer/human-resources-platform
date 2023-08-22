package com.halil.HumanResourcesPlatform.Authentication.configs;


import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.security.CustomAuthenticationEntryPoint;
import com.halil.HumanResourcesPlatform.Authentication.security.filters.JWTAuthFilter;
import com.halil.HumanResourcesPlatform.Authentication.security.filters.UsernamePasswordFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.*;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final AuthenticationProvider authenticationProvider;

    private final PathsConfig pathsConfig;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          AuthenticationProvider authenticationProvider,
                          PathsConfig pathsConfig) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.authenticationProvider = authenticationProvider;
        this.pathsConfig = pathsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                            final CorsConfiguration cors = new CorsConfiguration();
                            cors.setAllowedOrigins(List.of("http://localhost:5173"));
                            cors.setAllowedMethods(List.of("GET", "POST", "PUT"));
                            cors.setAllowedHeaders(List.of("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "X-XSRF-TOKEN"));
                            cors.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization"));
                            return cors;
                        }
                ))
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new UsernamePasswordFilter(authenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthFilter(authenticationProvider, pathsConfig), UsernamePasswordFilter.class)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        (requests) ->
                                requests.requestMatchers(
                                                "/candidate/*/applications", "/hr-specialist/*/posts", "/job", "/job/*/apply", "/job/*/status", "/job/*/applicants", "/linkedin/build", "/hr-specialist/*/posts", "/candidate/*/black-list").authenticated()
                                        .anyRequest().permitAll()
                );

        return httpSecurity.build();
    }

}
