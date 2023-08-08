package com.halil.HumanResourcesPlatform.HrSpecialists.controllers;

import com.halil.HumanResourcesPlatform.HrSpecialists.projections.PostedJobsProjectory;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class HrSpecialistController {

    private final HrSpecialistRepository hrSpecialistRepository;

    HrSpecialistController(HrSpecialistRepository hrSpecialistRepository) {
        this.hrSpecialistRepository = hrSpecialistRepository;
    }

    @GetMapping("/hr-specialist/{hrSpecialistId}/posts")
    public PostedJobsProjectory getPosts(@PathVariable UUID hrSpecialistId) {
        this.hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hr Specialist not found"));
        PostedJobsProjectory postedJobs = this.hrSpecialistRepository.findJobsByHrSpecialistId(hrSpecialistId);
        return postedJobs;
    }
}