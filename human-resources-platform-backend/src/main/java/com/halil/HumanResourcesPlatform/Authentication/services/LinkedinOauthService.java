package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.Authentication.configs.LinkedinOauthConfigProperties;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetAccessTokenLinkedinRequestDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetAccessTokenLinkedinResponeDto;
import com.halil.HumanResourcesPlatform.Authentication.dtos.GetLiteProfileFromLinkedinDto;
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

    public LinkedinOauthService(LinkedinOauthConfigProperties oauthConfigProperties) {
        this.oauthConfigProperties = oauthConfigProperties;
    }

    public String getAccessTokenFromLinkedin(String code) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://www.linkedin.com/oauth/v2/accessToken";
        GetAccessTokenLinkedinRequestDto requestDto = new GetAccessTokenLinkedinRequestDto(
                "authorization_code",
                code,
                oauthConfigProperties.clientId(),
                oauthConfigProperties.clientSecret(),
                oauthConfigProperties.redirectUri()
        );

        @Valid GetAccessTokenLinkedinResponeDto getLinkedinAccessTokenDto = webClient.build()
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(GetAccessTokenLinkedinResponeDto.class)
                .block();

        return getLinkedinAccessTokenDto.access_token();
    }

    public void getProfileFromLinkedin(String accessToken) {
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://api.linkedin.com/v2/me";

        @Valid GetLiteProfileFromLinkedinDto getLiteProfileFromLinkedinDto = webClient.build()
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(GetLiteProfileFromLinkedinDto.class)
                .block();

        logger.info(getLiteProfileFromLinkedinDto.toString());
    }
}
