package com.halil.HumanResourcesPlatform.Applications.services;

import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;
import com.halil.HumanResourcesPlatform.Applications.repositories.ApplicationRepository;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class ApplicationService {


    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private CandidateRepository candidateRepository;

    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    ApplicationService(ApplicationRepository applicationRepository,
                       JobRepository jobRepository,
                       CandidateRepository candidateRepository){
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }
    public void createApplication(Candidate candidate, Job job){
        if (job.getStatus().equals(Status.PASSIVE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cant apply to passive job");
        }

        for (Application application : job.getApplications()) {
            if (application.getCandidate().equals(candidate)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Already applied");
            }
        }

        if(candidate.isBlackListed() == true){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Candidate is black listed");
        }
        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus(ApplicationStatus.ON_EVALUATION);
        job.pushApplication(application);
        application.setJob(job);
        candidate.pushApplication(application);
        application.setCandidate(candidate);
        jobRepository.save(job);
        candidateRepository.save(candidate);
        applicationRepository.save(application);
        logger.info("Candidate:" + candidate.getCandidateId() + "applied Job: " + job.getJobId());
    }

    public void changeApplicationStatus(Application application, ApplicationStatus newStatus){
        application.setStatus(newStatus);
        applicationRepository.save(application);
        logger.info("Application: " + application.getApplicationId() + "status changed to" + newStatus.name());
    }

}
