package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.*;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.services.AuthenticationService;
import com.halil.HumanResourcesPlatform.Authentication.services.LinkedinOauthService;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController

public class AuthenticationController {

    private final AuthenticationProvider authenticationProvider;
    private final CandidateRepository candidateRepository;
    private final HrSpecialistRepository hrSpecialistRepository;
    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    AuthenticationController(AuthenticationProvider authenticationProvider,
                             CandidateRepository candidateRepository,
                             HrSpecialistRepository hrSpecialistRepository,
                             AuthenticationService authenticationService
    ) {
        this.authenticationProvider = authenticationProvider;
        this.candidateRepository = candidateRepository;
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Hr specialist sign in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)})
    @PostMapping("/sign-in")
    public JwtDto signIn(@Valid @RequestBody LdapSignInDto ldapSignInDto) {
        HrSpecialist hrSpecialist = hrSpecialistRepository.findByUsername(ldapSignInDto.username()).get(0);
        String jwt = authenticationProvider.createToken(hrSpecialist.getHrSpecialistId(), Roles.HR_SPECIALIST);
        logger.info("Hr specialist: " + hrSpecialist.getHrSpecialistId() + "signed in");
        return new JwtDto(jwt, Roles.HR_SPECIALIST.toString(), hrSpecialist.getHrSpecialistId());
    }

    @Operation(summary = "Linkedin sign in")
    @PostMapping("/linkedin/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDtoWithMessage linkedinSignIn(@Valid @RequestBody LinkedinSignInDto linkedinSignInDto) {
        return authenticationService.signInWithLinkedin(linkedinSignInDto.code());
    }

    @Operation(summary = "Scrape profile data from linkedin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content)})
    @PostMapping("/linkedin/build")
    @ResponseStatus(HttpStatus.CREATED)
    public void buildProfile(@Valid @RequestBody LinkedinBuildProfileDto linkedinBuildProfileDto) {
        Candidate candidate = candidateRepository.findById(linkedinBuildProfileDto.candidate_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
        candidate.setProfileUrl(linkedinBuildProfileDto.profile_url());
        this.authenticationService.saveCandidateFromLinkedin(candidate);
    }
}
