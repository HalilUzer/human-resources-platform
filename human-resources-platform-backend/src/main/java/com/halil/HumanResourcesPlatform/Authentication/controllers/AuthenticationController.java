package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.JwtDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LinkedinSignInDto;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LdapSignInDto;
import com.halil.HumanResourcesPlatform.Authentication.services.LinkedinOauthService;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class AuthenticationController {

    AuthenticationProvider authenticationProvider;

    LinkedinOauthService linkedinOauthService;

    ObjectMapper objectMapper;

    CandidateRepository candidateRepository;

    HrSpecialistRepository hrSpecialistRepository;



    AuthenticationController(AuthenticationProvider authenticationProvider,
                             LinkedinOauthService linkedinOauthService,
                             ObjectMapper objectMapper,
                             CandidateRepository candidateRepository,
    HrSpecialistRepository hrSpecialistRepository){
        this.authenticationProvider = authenticationProvider;
        this.linkedinOauthService = linkedinOauthService;
        this.objectMapper = objectMapper;
        this.candidateRepository = candidateRepository;
        this.hrSpecialistRepository = hrSpecialistRepository;
    }

    @PostMapping("/sign-in")
    public JwtDto signIn(@Valid @RequestBody LdapSignInDto ldapSignInDto){
        HrSpecialist hrSpecialist = hrSpecialistRepository.findByUsername(ldapSignInDto.username()).get(0);
        String jwt = authenticationProvider.createToken(hrSpecialist.getId(), Roles.HR_SPECIALIST);
        return new JwtDto(jwt, Roles.HR_SPECIALIST.toString());
    }


    @PostMapping("/linkedin/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto linkedinSignIn(@Valid @RequestBody LinkedinSignInDto linkedinSignInDto){
        String accessToken = linkedinOauthService.getAccessTokenFromLinkedin(linkedinSignInDto.code());
        UUID id = linkedinOauthService.createProfileFromLinkedin(accessToken);
        String jwt = authenticationProvider.createToken(id, Roles.CANDIDATE);
        return new JwtDto(jwt, Roles.CANDIDATE.toString());
    }
}
