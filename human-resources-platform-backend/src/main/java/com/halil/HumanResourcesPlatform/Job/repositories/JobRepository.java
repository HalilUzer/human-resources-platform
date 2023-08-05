package com.halil.HumanResourcesPlatform.Job.repositories;

import com.halil.HumanResourcesPlatform.Job.entities.Job;
import com.halil.HumanResourcesPlatform.Job.entities.Status;
import com.halil.HumanResourcesPlatform.Job.projections.ApplicationProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    public List<Job> getJobsByStatusEquals(Status status);
    ApplicationProjection findApplicantsByJobId(UUID jobId);
}
