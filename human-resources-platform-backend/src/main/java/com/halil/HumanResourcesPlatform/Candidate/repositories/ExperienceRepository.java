package com.halil.HumanResourcesPlatform.Candidate.repositories;

import com.halil.HumanResourcesPlatform.Candidate.entites.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<Experience, UUID> {
}
