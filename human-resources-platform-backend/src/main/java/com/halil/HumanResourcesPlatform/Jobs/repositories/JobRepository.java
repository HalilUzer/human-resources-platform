package com.halil.HumanResourcesPlatform.Jobs.repositories;

import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.projections.ApplicantProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    public Optional<ApplicantProjection> getApplicantsByJobId(UUID jobId);
    public List<JobsProjection> getJobsByStatus(Status status);

    public Optional<Job> getJobByJobId(UUID jobId);

    public Optional<JobProjection> getJobProjectionByJobId(UUID jobId);


}
