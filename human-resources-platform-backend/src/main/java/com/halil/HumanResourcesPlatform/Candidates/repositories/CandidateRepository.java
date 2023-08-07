package com.halil.HumanResourcesPlatform.Candidates.repositories;

import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateApplicationsProjection;
import com.halil.HumanResourcesPlatform.Candidates.projections.GetCandidateProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    public Candidate findCandidateByLinkedinId(String linkedinId);
    public boolean existsCandidateByLinkedinId(String linkedinId);

    public Optional<GetCandidateProfileProjection> findProfileByCandidateId(UUID candidateId);

    public GetCandidateApplicationsProjection findCandidateApplicationProjectionByCandidateId(UUID candidateId);

}
