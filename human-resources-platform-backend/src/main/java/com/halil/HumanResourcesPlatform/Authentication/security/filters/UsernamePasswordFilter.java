package com.halil.HumanResourcesPlatform.Authentication.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halil.HumanResourcesPlatform.Authentication.security.CachedBodyHttpServletRequest;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;

import java.io.IOException;

public class UsernamePasswordFilter extends OncePerRequestFilter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final AuthenticationProvider provider;

    public UsernamePasswordFilter(AuthenticationProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                new CachedBodyHttpServletRequest(request);
        if ("/sign-in".equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod())) {
            HrSpecialist hrSpecialist = OBJECT_MAPPER.readValue(cachedBodyHttpServletRequest.getInputStream(), HrSpecialist.class);
            try {
                SecurityContextHolder.getContext().setAuthentication(
                        provider.validateCredentials(hrSpecialist)
                );
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        filterChain.doFilter(cachedBodyHttpServletRequest, response);
    }
}
