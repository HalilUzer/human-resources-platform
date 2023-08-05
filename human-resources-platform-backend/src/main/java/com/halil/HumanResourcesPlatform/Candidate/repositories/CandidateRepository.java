package com.halil.HumanResourcesPlatform.Candidate.repositories;

import com.halil.HumanResourcesPlatform.Candidate.entites.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    public Candidate findCandidateByLinkedinId(String linkedinId);
    public boolean existsCandidateByLinkedinId(String linkedinId);

}
