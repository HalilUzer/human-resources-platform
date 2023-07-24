package com.halil.HumanResourcesPlatform.Authentication.security.filters;

import com.halil.HumanResourcesPlatform.Authentication.configs.PathsConfig;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

public class JWTAuthFilter extends OncePerRequestFilter {

    private final AuthenticationProvider provider;

    private final PathsConfig pathsConfig;


    public JWTAuthFilter(AuthenticationProvider provider, PathsConfig pathsConfig) {
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
                (pathsConfig.pathAndMethodMatcher(pathsConfig.candidatePaths, request.getServletPath(), request.getMethod()) ||
                        pathsConfig.pathAndMethodMatcher(pathsConfig.hrSpecialistPaths, request.getServletPath(), request.getMethod()))) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(
                            provider.validateToken(authElements[1], request.getServletPath())
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
