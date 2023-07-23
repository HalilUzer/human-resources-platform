package com.halil.HumanResourcesPlatform.Authentication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.halil.HumanResourcesPlatform.Authentication.services.AuthenticationService;
import com.halil.HumanResourcesPlatform.HumanResourcesSpecialist.entities.HumanResourcesSpecialist;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {
    @Value("$security.jwt.token.secret-key:secret-key")
    private String privateKey;
    private final AuthenticationService authenticationService;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {
        this.privateKey = Base64.getEncoder().encodeToString(this.privateKey.getBytes());
    }

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        return JWT.create()
                .withIssuer(username)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public UsernamePasswordAuthenticationToken validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        HumanResourcesSpecialist humanResourcesSpecialist = authenticationService.findByUsername(decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(humanResourcesSpecialist, null, Collections.emptyList());
    }

    public UsernamePasswordAuthenticationToken validateCredentials(HumanResourcesSpecialist humanResourcesSpecialist) {
        HumanResourcesSpecialist humanResourcesSpecialistAuth = authenticationService.validateCredentials(humanResourcesSpecialist);
        return new UsernamePasswordAuthenticationToken(humanResourcesSpecialistAuth, null, Collections.emptyList());
    }

}
