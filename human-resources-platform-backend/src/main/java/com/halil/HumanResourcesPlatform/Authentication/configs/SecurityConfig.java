package com.halil.HumanResourcesPlatform.Authentication.configs;


import com.halil.HumanResourcesPlatform.Authentication.security.CustomAuthenticationEntryPoint;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.security.filters.JWTAuthFilter;
import com.halil.HumanResourcesPlatform.Authentication.security.filters.UsernamePasswordFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new UsernamePasswordFilter(authenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthFilter(authenticationProvider, pathsConfig), UsernamePasswordFilter.class)
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
