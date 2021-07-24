package com.appsdeveloperblog.ws.clients.photoappwebclient.controllers;

import com.appsdeveloperblog.ws.clients.photoappwebclient.response.AlbumRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * @author : zikoz
 * @created : 17 Jul, 2021
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AlbumController {

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @GetMapping("/albums")
    public String getAlbum(Model model, @AuthenticationPrincipal OidcUser principal){

        // TODO info :: way to get access token value
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oAuth2Client = oAuth2AuthorizedClientService.loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());

        String jwtAccessToken = oAuth2Client.getAccessToken().getTokenValue();
        log.info("JwtAccess token {}", jwtAccessToken);

        log.info("Principal: {}", principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenContent = idToken.getTokenValue();

        log.info("idTokenContent: {}", idTokenContent);

        var albumRest = AlbumRest.builder()
                .albumId("albumOne").albumTitle("Album Title One").albumUrl("http://localhost:8082/albums/1")
                .build();

        var albumRest2 = AlbumRest.builder()
                .albumId("albumTwo").albumTitle("Album Title Two").albumUrl("http://localhost:8082/albums/2")
                .build();

        model.addAttribute("albums", List.of(albumRest, albumRest2));

        return "albums";
    }

    @GetMapping("/protectedAlbums")
    public String protectedAlbums(Model model, @AuthenticationPrincipal OidcUser principal){

        // TODO info :: way to get access token value
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oAuth2Client = oAuth2AuthorizedClientService.loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());

        String jwtAccessToken = oAuth2Client.getAccessToken().getTokenValue();
        log.info("JwtAccess token {}", jwtAccessToken);

        log.info("Principal: {}", principal);

        OidcIdToken idToken = principal.getIdToken();
        String idTokenContent = idToken.getTokenValue();

        log.info("idTokenContent: {}", idTokenContent);


        String url = "http://localhost:8082/albums"; // the album resource created
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAccessToken);

        HttpEntity<List<AlbumRest>> entity  = new HttpEntity<>(httpHeaders);

        ResponseEntity<List<AlbumRest>> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AlbumRest>>() {});

        model.addAttribute("albums", responseEntity.getBody());

        return "albums";
    }

    @GetMapping("/protectedAlbumsWithWebClient")
    public String protectedAlbums(Model model){
        //TODO check configuration in main class
        String url = "http://localhost:8082/albums"; // the album resource created

        List<AlbumRest> albums = webClient.get().uri(url).retrieve().bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>() {}).block();
        model.addAttribute("albums", albums);

        return "albums";
    }


}
