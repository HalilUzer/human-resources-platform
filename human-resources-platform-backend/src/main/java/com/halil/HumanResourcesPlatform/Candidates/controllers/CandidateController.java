package com.halil.HumanResourcesPlatform.Candidates.controllers;

import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class CandidateController {

    private final CandidateRepository candidateRepository;


    CandidateController(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }
    @GetMapping("/candidate/{candidateId}")
    public Candidate getCandidateProfile(@PathVariable UUID candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
    }
}
