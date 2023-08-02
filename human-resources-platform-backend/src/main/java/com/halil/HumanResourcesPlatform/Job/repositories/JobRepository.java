package com.halil.HumanResourcesPlatform.Job.repositories;

import com.halil.HumanResourcesPlatform.Job.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
}
