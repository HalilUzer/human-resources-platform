package com.halil.HumanResourcesPlatform.Jobs.controllers;

import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.HrSpecialists.projections.PostedJobsProjectory;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Jobs.dtos.ChangeJobStatusDto;
import com.halil.HumanResourcesPlatform.Jobs.dtos.CreateJobDto;
import com.halil.HumanResourcesPlatform.Jobs.dtos.JobIdDto;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.projections.ApplicantProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobsProjection;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import com.halil.HumanResourcesPlatform.Jobs.services.JobService;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController

public class JobController {

    private final AuthenticationProvider authenticationProvider;
    private final JobRepository jobRepository;
    private final JobService jobService;

    private final HrSpecialistRepository hrSpecialistRepository;

    private final Logger logger = LoggerFactory.getLogger(JobController.class);


    JobController(
            JobRepository jobRepository,
            JobService jobService,
            AuthenticationProvider authenticationProvider,
            HrSpecialistRepository hrSpecialistRepository
    ) {
        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.authenticationProvider = authenticationProvider;
        this.hrSpecialistRepository = hrSpecialistRepository;
    }


    @Operation(summary = "Post a job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)})
    @PostMapping("/job")
    @ResponseStatus(HttpStatus.CREATED)
    public JobIdDto createJob(@Valid @RequestBody CreateJobDto createJobDto, @RequestHeader(name = "Authorization") String token) {
        UUID hrSpecialistId = authenticationProvider.getId(token);
        LocalDateTime until = jobService.parseToLocalDateTime(createJobDto.until(), createJobDto.is_permanent());
        Job job = jobService.saveJob(hrSpecialistId, createJobDto.title(), createJobDto.job_description(),
                createJobDto.status(), until, createJobDto.technical_skills(), createJobDto.personal_skills(), createJobDto.is_permanent());
        return new JobIdDto(job.getJobId());
    }

    @Operation(summary = "Get all jobs")
    @GetMapping("/jobs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<JobsProjection> getAllJobs() {
        List<JobsProjection> activeJobs = jobRepository.getJobsByStatus(Status.ACTIVE);
        return activeJobs;
    }

    @Operation(summary = "Get job info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Job didnt found",
                    content = @Content)})
    @GetMapping("/job/{jobId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public JobProjection getJob(@PathVariable UUID jobId) {
        jobService.getJob(jobId);
        return jobService.getJobProjection(jobId);
    }

    @Operation(summary = "Change job status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Job didnt found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Datetime not after 1 hour from current time"),
            @ApiResponse(responseCode = "400", description = "Invalid date time")})
    @PutMapping("/job/{jobId}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeStatus(@Valid @RequestBody ChangeJobStatusDto changeJobStatusDto, @PathVariable UUID jobId,
                             @RequestHeader(name = "Authorization") String token) {
        UUID hrSpecialistId = authenticationProvider.getId(token);
        jobService.changeJobStatus(jobId, hrSpecialistId, changeJobStatusDto.status(), changeJobStatusDto.until(),
                changeJobStatusDto.is_permanent());
    }

    @Operation(summary = "Get applicants of a job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Job didnt found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping("/job/{jobId}/applicants")
    public ApplicantProjection getApplicants(@PathVariable UUID jobId,
                                             @RequestHeader(name = "Authorization") String token) {
        UUID hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Job didnt found"));
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Date date = new Date();

        return jobService.getApplicantsByJobId(jobId);
    }

    @Operation(summary = "Get jobs posted by hr specialist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Hr specialist not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @GetMapping("/jobs/{hrSpecialistId}")
    public PostedJobsProjectory getPosts(@PathVariable UUID hrSpecialistId) {
        this.hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Hr specialist not found"));
        PostedJobsProjectory postedJobs = this.hrSpecialistRepository.findJobsByHrSpecialistId(hrSpecialistId);
        return postedJobs;
    }

}
