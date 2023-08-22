package com.halil.HumanResourcesPlatform.Candidates.controllers;

import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidates.dtos.BlackListCandidateDto;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateApplicationsProjection;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateProfileProjection;
import com.halil.HumanResourcesPlatform.Candidates.projections.IsCandidateBlackListedProjection;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.Candidates.services.CandidateService;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController

public class CandidateController {

    private final CandidateRepository candidateRepository;

    private final CandidateService candidateService;

    private final AuthenticationProvider authenticationProvider;

    private final HrSpecialistRepository hrSpecialistRepository;


    CandidateController(CandidateRepository candidateRepository, CandidateService candidateService,
                        AuthenticationProvider authenticationProvider,
                        HrSpecialistRepository hrSpecialistRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateService = candidateService;
        this.authenticationProvider = authenticationProvider;
        this.hrSpecialistRepository = hrSpecialistRepository;
    }

    @Operation(summary = "Get candidate info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content)})
    @GetMapping("/candidate/{candidateId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GetCandidateProfileProjection getCandidateProfile(@PathVariable UUID candidateId) {
        return candidateRepository.findProfileByCandidateId(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }

    @Operation(summary = "Get candidate's applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content)})
    @GetMapping("/candidate/{candidateId}/applications")
    public GetCandidateApplicationsProjection getCandidateApplications(@PathVariable UUID candidateId) {
        return candidateRepository.findCandidateApplicationProjectionByCandidateId(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }


    @Operation(summary = "Black list candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content)})
    @PutMapping("/candidate/{candidateId}/black-list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void blackListCandidate(@PathVariable UUID candidateId){
        candidateService.blackListCandidate(candidateId);
    }

    @Operation(summary = "Is candidate black listed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content)})
    @GetMapping("/candidate/{candidateId}/black-list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public IsCandidateBlackListedProjection isCandidateBlackListed(@PathVariable UUID candidateId){
        return candidateRepository.findIsCandidateBlackListedProjectionByCandidateId(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }

}
