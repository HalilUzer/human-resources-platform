package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.JwtDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LinkedinSignInDto;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LdapSignInDto;
import com.halil.HumanResourcesPlatform.Authentication.services.LinkedinOauthService;
import com.halil.HumanResourcesPlatform.Authentication.services.SeleniumService;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import jakarta.validation.Valid;

import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class AuthenticationController {

    private final AuthenticationProvider authenticationProvider;

    private final LinkedinOauthService linkedinOauthService;

    private final ObjectMapper objectMapper;

    private final CandidateRepository candidateRepository;

    private final HrSpecialistRepository hrSpecialistRepository;

    Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

    private final SeleniumService seleniumService;

    AuthenticationController(AuthenticationProvider authenticationProvider,
                             LinkedinOauthService linkedinOauthService,
                             ObjectMapper objectMapper,
                             CandidateRepository candidateRepository,
                             HrSpecialistRepository hrSpecialistRepository,
                             ChromeDriver chromeDriver,
                             SeleniumService  seleniumService
    ) {
        this.authenticationProvider = authenticationProvider;
        this.linkedinOauthService = linkedinOauthService;
        this.objectMapper = objectMapper;
        this.candidateRepository = candidateRepository;
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.seleniumService = seleniumService;
    }

    @PostMapping("/sign-in")
    public JwtDto signIn(@Valid @RequestBody LdapSignInDto ldapSignInDto) {
        HrSpecialist hrSpecialist = hrSpecialistRepository.findByUsername(ldapSignInDto.username()).get(0);
        String jwt = authenticationProvider.createToken(hrSpecialist.getId(), Roles.HR_SPECIALIST);
        return new JwtDto(jwt, Roles.HR_SPECIALIST.toString());
    }


    @PostMapping("/linkedin/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto linkedinSignIn(@Valid @RequestBody LinkedinSignInDto linkedinSignInDto) {
        String accessToken = linkedinOauthService.getAccessTokenFromLinkedin(linkedinSignInDto.code());
        UUID id = linkedinOauthService.createProfileFromLinkedin(accessToken);
        String jwt = authenticationProvider.createToken(id, Roles.CANDIDATE);
        return new JwtDto(jwt, Roles.CANDIDATE.toString());
    }

    @GetMapping("/login-linkedin")
    public void linkedLogin() {
        seleniumService.createCandidateFromLinkedin();
    }

}
