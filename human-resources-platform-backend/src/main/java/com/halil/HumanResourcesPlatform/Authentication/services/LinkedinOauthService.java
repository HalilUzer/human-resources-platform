package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.Authentication.configs.LinkedinOauthConfigProperties;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetAccessTokenLinkedinResponeDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetEmailFromLinkedin.GetEmailFromLinkedinDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetLiteProfileFromLinkedinDto;
import com.halil.HumanResourcesPlatform.Candidates.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidates.repositories.CandidateRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class LinkedinOauthService {


    private final LinkedinOauthConfigProperties oauthConfigProperties;


    private final Logger logger = LoggerFactory.getLogger(LinkedinOauthService.class);

    public LinkedinOauthService(LinkedinOauthConfigProperties oauthConfigProperties,
                                SeleniumService seleniumService,
                                CandidateRepository candidateRepository) {
        this.oauthConfigProperties = oauthConfigProperties;
    }

    public String getAccessTokenFromLinkedin(String code) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://www.linkedin.com/oauth/v2/accessToken";

        @Valid GetAccessTokenLinkedinResponeDto getLinkedinAccessTokenDto = webClient.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("code", code)
                        .with("client_id", oauthConfigProperties.clientId())
                        .with("client_secret", oauthConfigProperties.clientSecret())
                        .with("redirect_uri", oauthConfigProperties.redirectUri())
                        .with("scope", "r_liteprofile r_emailaddress"))
                .retrieve()
                .bodyToMono(GetAccessTokenLinkedinResponeDto.class)
                .block();

        return getLinkedinAccessTokenDto.access_token();
    }

    public Candidate createCandidateFromLinkedinOauth(String accessToken) {
        GetLiteProfileFromLinkedinDto getLiteProfileFromLinkedinDto = getLiteProfileFromLinkedin(accessToken);
        GetEmailFromLinkedinDto getEmailFromLinkedinDto = getEmailFromLinkedin(accessToken);

        Candidate candidate = new Candidate();
        candidate.setLinkedinId(getLiteProfileFromLinkedinDto.id());
        candidate.setName(getLiteProfileFromLinkedinDto.localizedFirstName());
        candidate.setSurname(getLiteProfileFromLinkedinDto.localizedLastName());
        candidate.setEmail(getEmailFromLinkedinDto.elements().get(0).handler().emailAddress());
        candidate.setBlackListed(false);
        return candidate;
    }

    public GetLiteProfileFromLinkedinDto getLiteProfileFromLinkedin(String accessToken) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://api.linkedin.com/v2/me";
        @Valid GetLiteProfileFromLinkedinDto getLiteProfileFromLinkedinDto = webClient.build()
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GetLiteProfileFromLinkedinDto.class)
                .block();

        return getLiteProfileFromLinkedinDto;
    }

    public GetEmailFromLinkedinDto getEmailFromLinkedin(String accessToken) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://api.linkedin.com/v2/clientAwareMemberHandles?q=members&projection=(elements*(primary,type,handle~))";
        GetEmailFromLinkedinDto getEmailFromLinkedinDto = webClient.build()
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GetEmailFromLinkedinDto.class)
                .block();


        return getEmailFromLinkedinDto;
    }


    public void revokeToken(String accessToken) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://www.linkedin.com/oauth/v2/revoke";
        webClient.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("client_id", oauthConfigProperties.clientId())
                        .with("client_secret", oauthConfigProperties.clientSecret())
                        .with("token", accessToken))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
