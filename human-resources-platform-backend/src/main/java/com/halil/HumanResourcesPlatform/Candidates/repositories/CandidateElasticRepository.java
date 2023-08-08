package com.halil.HumanResourcesPlatform.Candidates.repositories;

import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CandidateElasticRepository extends ElasticsearchRepository<Candidate, String> {
}
