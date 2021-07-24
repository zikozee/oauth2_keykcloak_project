package com.appsdeveloperblog.ws.clients.photoappwebclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PhotoAppWebClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoAppWebClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient(ClientRegistrationRepository clientRegistrationRepository,
                               OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository){

        //ClientRegistrationRepository get all registered users
        //OAuth2AuthorizedClientRepository gets all authorized users

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, oAuth2AuthorizedClientRepository);

        oauth2.setDefaultOAuth2AuthorizedClient(true);

        //this ensures every Http request sent using this webclient has the access token auto inserted
        //TODO DANGER when sending to a third party service, use another object or resttemplate so as not to compromise the token

        return WebClient.builder().apply(oauth2.oauth2Configuration()).build();
    }
}
