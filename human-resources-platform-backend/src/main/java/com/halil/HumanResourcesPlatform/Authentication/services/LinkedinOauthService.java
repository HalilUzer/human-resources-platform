package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.Authentication.dtos.GetAccessTokenLinkedinResponeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class LinkedinOauthService {
    public String getAccessTokenFromLinkedinServer(){
        WebClient.Builder webClient = WebClient.builder();
        String url = "https://www.linkedin.com/oauth/v2/accessToken";

        GetAccessTokenLinkedinResponeDto getLinkedinAccessTokenDto = webClient.build()
                .post()
                .uri(url)
                .retrieve()
                .bodyToMono(GetAccessTokenLinkedinResponeDto.class)
                .block();

        return getLinkedinAccessTokenDto.access_token();
    }
}
