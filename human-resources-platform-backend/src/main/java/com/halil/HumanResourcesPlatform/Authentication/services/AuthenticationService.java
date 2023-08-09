package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.JwtDtoWithMessage;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.LdapHrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.LdapHrSpecialistRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final LdapHrSpecialistRepository ldapHrSpecialistRepository;

    private final SeleniumService seleniumService;

    private final LinkedinOauthService linkedinOauthService;


    private final CandidateRepository candidateRepository;

    private final AuthenticationProvider authenticationProvider;

    public AuthenticationService(LdapHrSpecialistRepository ldapHrSpecialistRepository,
                                 SeleniumService seleniumService,
                                 CandidateRepository candidateRepository,
                                 LinkedinOauthService linkedinOauthService,
                                 AuthenticationProvider authenticationProvider) {
        this.ldapHrSpecialistRepository = ldapHrSpecialistRepository;
        this.seleniumService = seleniumService;
        this.candidateRepository = candidateRepository;
        this.linkedinOauthService = linkedinOauthService;
        this.authenticationProvider = authenticationProvider;

    }



    public LdapHrSpecialist findByUsername(String username) throws RuntimeException {
        LdapHrSpecialist hrSpecialist = this.ldapHrSpecialistRepository.findByUsername(username);
        if (hrSpecialist != null) {
            return hrSpecialist;
        }
        throw new RuntimeException("Invalid Username");
    }

    public void buildCandidateFromLinkedin(Candidate candidate) {
        this.seleniumService.fillCandidateDataFromLinkedin(candidate);
        this.candidateRepository.save(candidate);
    }

    public JwtDtoWithMessage signInWithLinkedin(String code) {
        String accessToken = linkedinOauthService.getAccessTokenFromLinkedin(code);
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


}
