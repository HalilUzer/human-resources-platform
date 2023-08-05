package com.halil.HumanResourcesPlatform.Job.controllers;

import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidate.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidate.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Job.dtos.ChangeJobStatusDto;
import com.halil.HumanResourcesPlatform.Job.projections.ApplicationProjection;
import com.halil.HumanResourcesPlatform.Job.services.JobService;
import com.halil.HumanResourcesPlatform.Job.dtos.CreateJobDto;
import com.halil.HumanResourcesPlatform.Job.entities.Job;
import com.halil.HumanResourcesPlatform.Job.entities.Status;
import com.halil.HumanResourcesPlatform.Job.repositories.JobRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class JobController {

    private final AuthenticationProvider authenticationProvider;
    private final JobRepository jobRepository;
    private final JobService jobService;
    private final HrSpecialistRepository hrSpecialistRepository;
    private final CandidateRepository candidateRepository;

    JobController(
            JobRepository jobRepository,
            JobService jobService,
            AuthenticationProvider authenticationProvider,
            CandidateRepository candidateRepository,
            HrSpecialistRepository hrSpecialistRepository) {

        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.authenticationProvider = authenticationProvider;
        this.candidateRepository = candidateRepository;
        this.hrSpecialistRepository = hrSpecialistRepository;
    }


    @PostMapping("/job")
    @ResponseStatus(HttpStatus.CREATED)
    public void createJob(@Valid @RequestBody CreateJobDto createJobDto, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        UUID hrSpecialistId;
        hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobService.buildJobFromDto(createJobDto, hrSpecialistId);
        jobRepository.save(job);
    }

    @GetMapping("/jobs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Job> getAllJobs() {
        List<Job> activeJobs = jobRepository.getJobsByStatusEquals(Status.ACTIVE);
        return activeJobs;
    }

    @GetMapping("/job/{jobId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Job getJob(@PathVariable UUID jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        job = jobService.checkStatus(job);
        jobRepository.save(job);
        return job;
    }

    @PutMapping("/job/{jobId}/apply")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void applyJob(@PathVariable UUID jobId, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        UUID candidateId = authenticationProvider.getId(token);
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate didnt found"));
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        job = jobService.checkStatus(job);
        jobRepository.save(job);
        if (job.getStatus().equals(Status.PASSIVE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cant apply to passive job");
        }
        job.pushAppliedCandidates(candidate);
        jobRepository.save(job);
    }

    @PutMapping("/job/{jobId}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeStatus(@Valid @RequestBody ChangeJobStatusDto changeJobStatusDto, @PathVariable UUID jobId, @RequestHeader(name = "Authorization") String token){
        token = token.split(" ")[1];
        UUID hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if(!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        job.setStatus(changeJobStatusDto.status());
        job.setUntil(changeJobStatusDto.until());
        jobRepository.save(job);
    }

    @GetMapping("/job/{jobId}/applicants")
    public ApplicationProjection getApplicants(@PathVariable UUID jobId, @RequestHeader(name = "Authorization") String token){
        token = token.split(" ")[1];
        UUID hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if(!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        ApplicationProjection applicationProjection = jobRepository.findApplicantsByJobId(job.getJobId());
        return applicationProjection;
    }
}
