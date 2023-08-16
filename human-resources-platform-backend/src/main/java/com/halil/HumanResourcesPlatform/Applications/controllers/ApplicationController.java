package com.halil.HumanResourcesPlatform.Applications.controllers;


import com.halil.HumanResourcesPlatform.Applications.dtos.ChangeApplicationStatusDto;
import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Applications.repositories.ApplicationRepository;
import com.halil.HumanResourcesPlatform.Applications.services.ApplicationService;
import com.halil.HumanResourcesPlatform.Applications.services.EmailService;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import com.halil.HumanResourcesPlatform.Jobs.services.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class ApplicationController {


    private final ApplicationRepository applicationRepository;
    private final AuthenticationProvider authenticationProvider;

    private final CandidateRepository candidateRepository;

    private final ApplicationService applicationService;

    private final JobRepository jobRepository;

    private final EmailService emailService;

    public ApplicationController(ApplicationRepository applicationRepository,
                                 AuthenticationProvider authenticationProvider,
                                 EmailService emailService,
                                 CandidateRepository candidateRepository,
                                 ApplicationService applicationService,
                                 JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.authenticationProvider = authenticationProvider;
        this.emailService = emailService;
        this.applicationService = applicationService;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }


    @Operation(summary = "Get candidate's applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Job not found"),
            @ApiResponse(responseCode = "403", description = "Cant apply to passive job"),
            @ApiResponse(responseCode = "403", description = "Already applied")})
    @PutMapping("/application/{applicationId}/status")
    public void changeApplicationStatus(@PathVariable UUID applicationId, @Valid @RequestBody ChangeApplicationStatusDto applicationStatusDto, @RequestHeader(name = "Authorization") String token) {
        Application application = this.applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        Job job = application.getJob();
        UUID hrSpecialistId = authenticationProvider.getId(token);
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        applicationService.changeApplicationStatus(application, applicationStatusDto.status());
    }


    @Operation(summary = "Get candidate's applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Candidate not found",
                    content = @Content),
    @ApiResponse(responseCode = "404", description = "Job not found"),
    @ApiResponse(responseCode = "403", description = "Cant apply to passive job"),
    @ApiResponse(responseCode = "403", description = "Already applied")})
    @PostMapping("/application")
    public void apply(@PathVariable UUID jobId, @RequestHeader(name = "Authorization") String token){
        UUID candidateId = authenticationProvider.getId(token);
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        applicationService.createApplication(candidate,job);
    }
}
