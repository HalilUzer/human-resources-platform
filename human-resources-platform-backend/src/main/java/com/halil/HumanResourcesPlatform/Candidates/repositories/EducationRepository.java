package com.halil.HumanResourcesPlatform.Candidates.repositories;

import com.halil.HumanResourcesPlatform.Candidates.entites.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducationRepository extends JpaRepository<Education, UUID> {
}
