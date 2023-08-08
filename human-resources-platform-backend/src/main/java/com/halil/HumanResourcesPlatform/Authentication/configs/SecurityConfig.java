package com.halil.HumanResourcesPlatform.Authentication.configs;


import com.halil.HumanResourcesPlatform.Authentication.security.CustomAuthenticationEntryPoint;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.security.filters.JWTAuthFilter;
import com.halil.HumanResourcesPlatform.Authentication.security.filters.UsernamePasswordFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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
                    cors.setAllowedOrigins(List.of("*"));
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(List.of("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
                    cors.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization"));
                    return cors;
                }
                ))
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new UsernamePasswordFilter(authenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthFilter(authenticationProvider, pathsConfig), UsernamePasswordFilter.class)
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        (requests) ->
                                requests.requestMatchers("/sign-in").authenticated()

                );

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
