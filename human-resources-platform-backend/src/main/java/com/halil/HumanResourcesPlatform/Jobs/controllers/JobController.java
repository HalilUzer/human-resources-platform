package com.halil.HumanResourcesPlatform.Jobs.controllers;

import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Jobs.dtos.ChangeJobStatusDto;
import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;
import com.halil.HumanResourcesPlatform.Jobs.dtos.JobIdDto;
import com.halil.HumanResourcesPlatform.Jobs.projections.ApplicantProjection;
import com.halil.HumanResourcesPlatform.Applications.repositories.ApplicationRepository;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobsProjection;
import com.halil.HumanResourcesPlatform.Jobs.services.JobService;
import com.halil.HumanResourcesPlatform.Jobs.dtos.CreateJobDto;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
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
    private final ApplicationRepository applicationRepository;


    JobController(
            JobRepository jobRepository,
            JobService jobService,
            AuthenticationProvider authenticationProvider,
            CandidateRepository candidateRepository,
            HrSpecialistRepository hrSpecialistRepository,
            ApplicationRepository applicationRepository) {
        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.authenticationProvider = authenticationProvider;
        this.candidateRepository = candidateRepository;
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.applicationRepository = applicationRepository;
    }


    @PostMapping("/job")
    @ResponseStatus(HttpStatus.CREATED)
    public JobIdDto createJob(@Valid @RequestBody CreateJobDto createJobDto, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        UUID hrSpecialistId;
        hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobService.buildJobFromDto(createJobDto, hrSpecialistId);
        HrSpecialist hrSpecialist = hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hr specialist not found"));
        hrSpecialist.pushJob(job);
        hrSpecialistRepository.save(hrSpecialist);
        jobRepository.save(job);
        return new JobIdDto(job.getJobId());
    }

    @GetMapping("/jobs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<JobsProjection> getAllJobs() {
        List<JobsProjection> activeJobs = jobRepository.getJobsByStatus(Status.ACTIVE);
        return activeJobs;
    }

    @GetMapping("/job/{jobId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public JobProjection getJob(@PathVariable UUID jobId) {
        Job job = jobRepository.getJobByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        job = jobService.checkStatus(job);
        jobRepository.save(job);
        JobProjection jobProjection = jobRepository.getJobProjectionByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        return jobProjection;
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

        for(Application application :job.getApplications()){
            if(application.getCandidate().getCandidateId().equals(candidateId)){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Already applied");
            }
        }


        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus(ApplicationStatus.ON_EVALUATION);
        job.pushApplication(application);
        candidate.pushApplication(application);
        applicationRepository.save(application);
        jobRepository.save(job);
        candidateRepository.save(candidate);
    }

    @PutMapping("/job/{jobId}/status")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeStatus(@Valid @RequestBody ChangeJobStatusDto changeJobStatusDto, @PathVariable UUID jobId, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        UUID hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        job.setStatus(changeJobStatusDto.status());
        job.setUntil(changeJobStatusDto.until());
        jobRepository.save(job);
    }

    @GetMapping("/job/{jobId}/applicants")
    public ApplicantProjection getApplicants(@PathVariable UUID jobId, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        UUID hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        ApplicantProjection applicantProjection = jobRepository.getApplicantsByJobId(jobId);
        return applicantProjection;
    }

    @PostMapping("/job/{jobId}/applicant/{candidateId}")
    public void deleteApplicant(@PathVariable UUID jobId, @PathVariable UUID candidateId, @RequestHeader(name = "Authorization") String token) {
        token = token.split(" ")[1];
        UUID hrSpecialistId = authenticationProvider.getId(token);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }
}
