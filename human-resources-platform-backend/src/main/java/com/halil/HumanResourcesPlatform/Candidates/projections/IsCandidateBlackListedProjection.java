package com.halil.HumanResourcesPlatform.Candidates.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface IsCandidateBlackListedProjection {
    @JsonIgnore
    UUID getCandidateId();

    boolean isIsBlackListed();

}
