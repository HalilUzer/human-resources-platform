package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.Authentication.configs.LinkedinOauthConfigProperties;

import com.halil.HumanResourcesPlatform.Authentication.dtos.GetAccessTokenLinkedinResponeDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetLiteProfileFromLinkedinDto;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;


@Service
public class LinkedinOauthService {


    private final LinkedinOauthConfigProperties oauthConfigProperties;

    private final HrSpecialistRepository hrSpecialistRepository;

    private final CandidateRepository candidateRepository;

    private final Logger logger = LoggerFactory.getLogger(LinkedinOauthService.class);

    public LinkedinOauthService(LinkedinOauthConfigProperties oauthConfigProperties,
                                HrSpecialistRepository hrSpecialistRepository,
                                CandidateRepository candidateRepository) {
        this.oauthConfigProperties = oauthConfigProperties;
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.candidateRepository = candidateRepository;
    }

    public String getAccessTokenFromLinkedin(String code) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://www.linkedin.com/oauth/v2/accessToken";

        @Valid GetAccessTokenLinkedinResponeDto getLinkedinAccessTokenDto = webClient.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code").
                        with("code", code)
                        .with("client_id", oauthConfigProperties.clientId())
                        .with("client_secret", oauthConfigProperties.clientSecret())
                        .with("redirect_uri", oauthConfigProperties.redirectUri()))
                .retrieve()
                .bodyToMono(GetAccessTokenLinkedinResponeDto.class)
                .block();

        return getLinkedinAccessTokenDto.access_token();
    }

    public UUID createProfileFromLinkedin(String accessToken) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://api.linkedin.com/v2/me";

        @Valid GetLiteProfileFromLinkedinDto getLiteProfileFromLinkedinDto = webClient.build()
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GetLiteProfileFromLinkedinDto.class)
                .block();

        Candidate candidate = new Candidate();
        candidate.setId(UUID.randomUUID());
        candidate.setLinkedinId(getLiteProfileFromLinkedinDto.id());
        candidate.setName(getLiteProfileFromLinkedinDto.localizedFirstName());
        candidate.setSurname(getLiteProfileFromLinkedinDto.localizedLastName());
        candidate.setHeadline(getLiteProfileFromLinkedinDto.localizedHeadline());
        try{
            candidateRepository.save(candidate);
        }
        catch (Exception e){}

        return  candidate.getId();
    }
}
