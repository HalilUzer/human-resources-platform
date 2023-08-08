package com.halil.HumanResourcesPlatform.Applications.controllers;


import com.halil.HumanResourcesPlatform.Applications.dtos.ChangeApplicationStatusDto;
import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Applications.repositories.ApplicationRepository;
import com.halil.HumanResourcesPlatform.Applications.services.EmailService;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class ApplicationController {


    private final ApplicationRepository applicationRepository;
    private final AuthenticationProvider authenticationProvider;

    private final EmailService emailService;

    public ApplicationController(ApplicationRepository applicationRepository,
                                 AuthenticationProvider authenticationProvider,
                                 EmailService emailService) {
        this.applicationRepository = applicationRepository;
        this.authenticationProvider = authenticationProvider;
        this.emailService = emailService;
    }

    @PutMapping("/application/{applicationId}/status")
    public void changeApplicationStatus(@PathVariable UUID applicationId, @Valid @RequestBody ChangeApplicationStatusDto applicationStatusDto, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        Application application = this.applicationRepository.findById(applicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        Job job = application.getJob();
        UUID hrSpecialistId = authenticationProvider.getId(token);
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        application.setStatus(applicationStatusDto.status());
        applicationRepository.save(application);
    }
}
