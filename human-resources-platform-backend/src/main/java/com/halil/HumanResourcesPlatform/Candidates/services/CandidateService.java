package com.halil.HumanResourcesPlatform.Candidates.services;

import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.Jobs.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void blackListCandidate(UUID candidateId) {
        Candidate candidate = candidateRepository.findByCandidateId(candidateId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found"));
        List<Application> applications = candidate.getApplications();
        candidate.setBlackListed(true);
        for(Application application : applications){
            application.setStatus(ApplicationStatus.DENIED);
        }
        candidateRepository.save(candidate);
        logger.info("Candidate: " + candidate.getCandidateId() + " black listed");
    }
}
