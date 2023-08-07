package com.halil.HumanResourcesPlatform.Candidates.controllers;

import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateApplicationsProjection;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateProfileProjection;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class CandidateController {

    private final CandidateRepository candidateRepository;

    private final AuthenticationProvider authenticationProvider;


    CandidateController(CandidateRepository candidateRepository,
                        AuthenticationProvider authenticationProvider){
        this.candidateRepository = candidateRepository;
        this.authenticationProvider = authenticationProvider;
    }
    @GetMapping("/candidate/{candidateId}")
    public GetCandidateProfileProjection getCandidateProfile(@PathVariable UUID candidateId) {
        return candidateRepository.findProfileByCandidateId(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }

    @GetMapping("/candidate/{candidateId}/applications")
    public GetCandidateApplicationsProjection getCandidateApplications(@PathVariable UUID candidateId, @RequestHeader(name = "Authorization") String token){
        token = token.split(" ")[1];
        Roles role = authenticationProvider.getRole(token);
        if(role.equals(Roles.CANDIDATE)){
            UUID requestCandidateId = authenticationProvider.getId(token);
            if(!requestCandidateId.equals(candidateId)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
        }
        return candidateRepository.findCandidateApplicationProjectionByCandidateId(candidateId);
    }

}
