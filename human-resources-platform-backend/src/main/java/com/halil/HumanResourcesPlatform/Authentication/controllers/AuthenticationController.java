package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.*;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.services.LinkedinOauthService;
import com.halil.HumanResourcesPlatform.Authentication.services.SeleniumService;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AuthenticationController {

    private final AuthenticationProvider authenticationProvider;
    private final LinkedinOauthService linkedinOauthService;
    private final CandidateRepository candidateRepository;
    private final HrSpecialistRepository hrSpecialistRepository;
    private final SeleniumService seleniumService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    AuthenticationController(AuthenticationProvider authenticationProvider,
                             CandidateRepository candidateRepository,
                             HrSpecialistRepository hrSpecialistRepository,
                             LinkedinOauthService linkedinOauthService,
                             SeleniumService seleniumService
    ) {
        this.authenticationProvider = authenticationProvider;
        this.candidateRepository = candidateRepository;
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.linkedinOauthService = linkedinOauthService;
        this.seleniumService = seleniumService;
    }

    @PostMapping("/sign-in")
    public JwtDto signIn(@Valid @RequestBody LdapSignInDto ldapSignInDto) {
        HrSpecialist hrSpecialist = hrSpecialistRepository.findByUsername(ldapSignInDto.username()).get(0);
        String jwt = authenticationProvider.createToken(hrSpecialist.getHrSpecialistId(), Roles.HR_SPECIALIST);
        return new JwtDto(jwt, Roles.HR_SPECIALIST.toString(), hrSpecialist.getHrSpecialistId());
    }

    @PostMapping("/linkedin/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDtoWithMessage linkedinSignIn(@Valid @RequestBody LinkedinSignInDto linkedinSignInDto) {
        String accessToken = linkedinOauthService.getAccessTokenFromLinkedin(linkedinSignInDto.code());
        Candidate candidate = linkedinOauthService.createCandidateFromLinkedinOauth(accessToken);
        String message;
        if (candidateRepository.existsCandidateByLinkedinId(candidate.getLinkedinId())) {
            candidate = candidateRepository.findCandidateByLinkedinId(candidate.getLinkedinId());
            message = "Exists";
        } else {
            candidateRepository.save(candidate);
            message = "Created";
        }
        String jwt = authenticationProvider.createToken(candidate.getCandidateId(), Roles.CANDIDATE);
        return new JwtDtoWithMessage(jwt, Roles.CANDIDATE.toString(), candidate.getCandidateId(), message);
    }

    @PostMapping("/linkedin/build")
    @ResponseStatus(HttpStatus.CREATED)
    public void buildProfile(@Valid @RequestBody LinkedinBuildProfileDto linkedinBuildProfileDto) {
        Candidate candidate = candidateRepository.findById(linkedinBuildProfileDto.candidate_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
        candidate.setProfileUrl(linkedinBuildProfileDto.profile_url());
        candidate = seleniumService.fillCandidateDataFromLinkedin(candidate);
        candidateRepository.save(candidate);
    }
}
