package com.halil.HumanResourcesPlatform.Authentication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halil.HumanResourcesPlatform.Authentication.configs.PathsConfig;
import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.services.AuthenticationService;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.LdapHrSpecialist;
import com.halil.HumanResourcesPlatform.LdapFiller;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

import org.slf4j.Logger;

@Component
public class AuthenticationProvider {
    @Value("$security.jwt.token.secret-key:secret-key")
    private String privateKey;
    private final AuthenticationService authenticationService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

    private final ObjectMapper objectMapper;

    private final HrSpecialistRepository hrSpecialistRepository;

    private final PathsConfig pathsConfig;

    private final CandidateRepository candidateRepository;

    public AuthenticationProvider(AuthenticationService authenticationService,
                                  PathsConfig pathsConfig,
                                  ObjectMapper objectMapper,
                                  HrSpecialistRepository hrSpecialistRepository,
                                  CandidateRepository candidateRepository) {
        this.authenticationService = authenticationService;
        this.pathsConfig = pathsConfig;
        this.objectMapper = objectMapper;
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.candidateRepository = candidateRepository;


    }

    @PostConstruct
    protected void init() {
        this.privateKey = Base64.getEncoder().encodeToString(this.privateKey.getBytes());
    }

    public String createToken(UUID id, Roles role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        return JWT.create()
                .withIssuer(id.toString())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("role", role.toString())
                .sign(algorithm);
    }

    public UsernamePasswordAuthenticationToken validateToken(String token, String servletPath) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        logger.info(decoded.getPayload());
        String role = decoded.getClaim("role").asString();
        Object user;
        if(!pathsConfig.pathAndRoleMatcher(Roles.valueOf(role), servletPath)){
            throw  new RuntimeException();
        }

        if(role.equals(Roles.HR_SPECIALIST.toString())){
            Optional<HrSpecialist> hrSpecialist = hrSpecialistRepository.findById(UUID.fromString(decoded.getIssuer()));
            if(hrSpecialist.isEmpty()){
                throw new RuntimeException();
            }
            user = hrSpecialist.get();
        } else if (role.equals(Roles.CANDIDATE.toString())) {
            Optional<Candidate> candidate = candidateRepository.findById(UUID.fromString(decoded.getIssuer()));
            if(candidate.isEmpty()){
                throw new RuntimeException();
            }
            user = candidate.get();
        }
        else {
            throw new RuntimeException();
        }
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public UsernamePasswordAuthenticationToken validateCredentials(LdapHrSpecialist hrSpecialist) {
        LdapHrSpecialist hrSpecialistAuth = authenticationService.validateCredentials(hrSpecialist);
        return new UsernamePasswordAuthenticationToken(hrSpecialistAuth, null, Collections.emptyList());
    }

}
