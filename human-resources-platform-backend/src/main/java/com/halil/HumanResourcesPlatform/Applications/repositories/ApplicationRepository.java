package com.halil.HumanResourcesPlatform.Applications.repositories;

import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsApplicationByJobAndCandidate(Job job, Candidate candidate);
}
