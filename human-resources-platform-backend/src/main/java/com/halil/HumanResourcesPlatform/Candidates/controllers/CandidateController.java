package com.halil.HumanResourcesPlatform.Candidates.controllers;

import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateApplicationsProjection;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateProfileProjection;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
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


    CandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
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
    public GetCandidateApplicationsProjection getCandidateApplications(@PathVariable UUID candidateId, @RequestHeader(name = "Authorization") String token) {
        return candidateRepository.findCandidateApplicationProjectionByCandidateId(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }

}
