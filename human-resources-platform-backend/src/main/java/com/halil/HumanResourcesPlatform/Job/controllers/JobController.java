package com.halil.HumanResourcesPlatform.Job.controllers;

import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Job.dtos.CreateJobDto;
import com.halil.HumanResourcesPlatform.Job.entities.Job;
import com.halil.HumanResourcesPlatform.Job.repositories.JobRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class JobController {

    private final HrSpecialistRepository hrSpecialistRepository;

    private final JobRepository jobRepository;

    JobController(HrSpecialistRepository hrSpecialistRepository,
                  JobRepository jobRepository) {
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.jobRepository = jobRepository;
    }


    @PostMapping("/job")
    @ResponseStatus(HttpStatus.CREATED)
    public void createJob(@Valid @RequestBody CreateJobDto createJobDto) {
        Job job = new Job(
                hrSpecialistRepository.findById(createJobDto.poster()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "poster not found")),
                createJobDto.technicalSkills(),
                createJobDto.personalSkills(),
                createJobDto.title(),
                createJobDto.until(),
                createJobDto.state(),
                createJobDto.job_description()
        );
        jobRepository.save(job);
    }

}
