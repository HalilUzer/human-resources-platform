package com.halil.HumanResourcesPlatform.Applications.services;

import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;
import com.halil.HumanResourcesPlatform.Applications.repositories.ApplicationRepository;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {


    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private CandidateRepository candidateRepository;

    ApplicationService(ApplicationRepository applicationRepository,
                       JobRepository jobRepository,
                       CandidateRepository candidateRepository){
        this.applicationRepository = applicationRepository;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }
    public void createApplication(Candidate candidate, Job job){
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
}
