package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.JwtDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LinkedinSignInDto;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LdapSignInDto;
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


@RestController
public class AuthenticationController {

    private final AuthenticationProvider authenticationProvider;
    private final LinkedinOauthService linkedinOauthService;
    private final CandidateRepository candidateRepository;
    private final HrSpecialistRepository hrSpecialistRepository;

    private final SeleniumService seleniumService;
    Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);

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
        String jwt = authenticationProvider.createToken(hrSpecialist.getId(), Roles.HR_SPECIALIST);
        return new JwtDto(jwt, Roles.HR_SPECIALIST.toString());
    }



    @PostMapping("/linkedin/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtDto linkedinSignIn(@Valid @RequestBody LinkedinSignInDto linkedinSignInDto) {
        String accessToken = linkedinOauthService.getAccessTokenFromLinkedin(linkedinSignInDto.code());
        Candidate candidate = linkedinOauthService.createCandidateFromLinkedinOauth(accessToken);
        candidate.setProfileUrl(linkedinSignInDto.profile_url());
        if (candidateRepository.existsCandidateByLinkedinId(candidate.getLinkedinId())) {
            candidate = candidateRepository.findCandidateByLinkedinId(candidate.getLinkedinId());
        } else {
            candidate = seleniumService.fillCandidateDataFromLinkedin(candidate);
            candidateRepository.save(candidate);
        }
        String jwt = authenticationProvider.createToken(candidate.getCandidateId(), Roles.CANDIDATE);
        return new JwtDto(jwt, Roles.CANDIDATE.toString());
    }

}
