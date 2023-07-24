package com.halil.HumanResourcesPlatform.Authentication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halil.HumanResourcesPlatform.Authentication.configs.PathsConfig;
import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.services.AuthenticationService;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.LdapHrSpecialist;
import com.halil.HumanResourcesPlatform.LdapFiller;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import org.slf4j.Logger;

@Component
public class AuthenticationProvider {
    @Value("$security.jwt.token.secret-key:secret-key")
    private String privateKey;
    private final AuthenticationService authenticationService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

    private final ObjectMapper objectMapper;

    private PathsConfig pathsConfig;

    public AuthenticationProvider(AuthenticationService authenticationService,
                                  PathsConfig pathsConfig,
                                  ObjectMapper objectMapper) {
        this.authenticationService = authenticationService;
        this.pathsConfig = pathsConfig;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    protected void init() {
        this.privateKey = Base64.getEncoder().encodeToString(this.privateKey.getBytes());
    }

    public String createToken(String username, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        return JWT.create()
                .withIssuer(username)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public UsernamePasswordAuthenticationToken validateToken(String token, String servletPath) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        logger.info(decoded.getPayload());
        String role = decoded.getClaim("role").asString();
        pathsConfig.pathAndRoleMatcher(Roles.valueOf(role), servletPath);
        LdapHrSpecialist hrSpecialist = authenticationService.findByUsername(decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(hrSpecialist, null, Collections.emptyList());
    }

    public UsernamePasswordAuthenticationToken validateCredentials(LdapHrSpecialist hrSpecialist) {
        LdapHrSpecialist hrSpecialistAuth = authenticationService.validateCredentials(hrSpecialist);
        return new UsernamePasswordAuthenticationToken(hrSpecialistAuth, null, Collections.emptyList());
    }

}
