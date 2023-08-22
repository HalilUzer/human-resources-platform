package com.halil.HumanResourcesPlatform.Jobs.repositories;

import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.projections.ApplicantProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    Optional<ApplicantProjection> getApplicantsByJobId(UUID jobId);

    List<JobsProjection> getJobsByStatus(Status status);

    Optional<Job> getJobByJobId(UUID jobId);

    Optional<JobProjection> getJobProjectionByJobId(UUID jobId);


   /* @Query(value = "CREATE EVENT IF NOT EXISTS changeStatus\n" +
            "ON SCHEDULE AT ':year-:month-:day :hour::minute:00'" +
            "DO\n" +
            "UPDATE jobs WHERE job_id=:job_id SET status=:status")
    void createChangeStatusEvent(@Param("job_id") String job_id, @Param("status") Integer status, @Param("hour") Integer hour, @Param("month") Integer month, @Param("year") Integer year,
                                 @Param("day") Integer day, @Param("minute") Integer minute);*/


}
