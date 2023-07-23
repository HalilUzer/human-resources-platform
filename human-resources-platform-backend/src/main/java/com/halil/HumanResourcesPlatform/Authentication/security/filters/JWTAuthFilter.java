package com.halil.HumanResourcesPlatform.Authentication.security.filters;

import com.halil.HumanResourcesPlatform.Authentication.configs.Path;
import com.halil.HumanResourcesPlatform.Authentication.configs.PathsConfig;
import com.halil.HumanResourcesPlatform.Authentication.security.UserAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter{

    private final UserAuthenticationProvider provider;

    private final PathsConfig pathsConfig;



    public JWTAuthFilter(UserAuthenticationProvider provider, PathsConfig pathsConfig) {
        this.provider = provider;
        this.pathsConfig = pathsConfig;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null &&
                pathsConfig.matcher(pathsConfig.candidePaths, request.getServletPath(), request.getMethod()) ||
        pathsConfig.matcher(pathsConfig.hrSpecialistPaths, request.getServletPath(), request.getMethod())) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(
                            provider.validateToken(authElements[1])
                    );
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
