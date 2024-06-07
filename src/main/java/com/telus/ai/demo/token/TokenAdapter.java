package com.telus.ai.demo.token;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class TokenAdapter {

    @Value("${oauth2client.client-id}")
    private String clientId;
    
    private final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;

    public TokenAdapter(AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager) {
        this.authorizedClientServiceAndManager = authorizedClientServiceAndManager;
    }
    
    public String getAccessToken() {
        
        log.debug(String.format("*** START TokenAdapter.getAccessToken() : %s", new Date().toString()));
        
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("client_reg_id")
                .principal(clientId)
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientServiceAndManager.authorize(authorizeRequest);

        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();
        
        log.debug(String.format("tokenValue: %s", accessToken.getTokenValue()));
        log.debug(String.format("tokenScopes: %s", accessToken.getScopes()));
        log.debug(String.format("issuedAt: %s", accessToken.getIssuedAt()));
        log.debug(String.format("expiresAt: %s", accessToken.getExpiresAt()));

        log.debug(String.format("*** END TokenAdapter.getAccessToken() : %s", new Date().toString()));

        return accessToken.getTokenValue();
    }
}
