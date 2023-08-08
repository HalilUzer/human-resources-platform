package com.halil.HumanResourcesPlatform.HrSpecialists.repositories;


import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.projections.PostedJobsProjectory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;


public interface HrSpecialistRepository extends JpaRepository<HrSpecialist, UUID> {
    public List<HrSpecialist> findByUsername(String username);

    public PostedJobsProjectory findJobsByHrSpecialistId(UUID hrSpecialistId);
}
