package com.halil.HumanResourcesPlatform.Candidates.repositories;

import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
}
