package com.halil.HumanResourcesPlatform.Candidate.repositories;

import com.halil.HumanResourcesPlatform.Candidate.entites.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, UUID> {
}
