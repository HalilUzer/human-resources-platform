package com.halil.HumanResourcesPlatform.Authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          UserAuthenticationProvider userAuthenticationProvider) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new UsernamePasswordFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthFilter(userAuthenticationProvider), UsernamePasswordFilter.class)
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        (requests) ->
                                requests.requestMatchers("/sign-in", "/").permitAll()
                                        .requestMatchers("/api").authenticated()
                                        .anyRequest().denyAll()
                );

        return httpSecurity.build();
    }
}
