package com.halil.HumanResourcesPlatform.Candidates.repositories;

import com.halil.HumanResourcesPlatform.Candidates.entites.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<Experience, UUID> {
}
