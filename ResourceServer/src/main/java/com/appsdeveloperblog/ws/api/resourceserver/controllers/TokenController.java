package com.appsdeveloperblog.ws.api.resourceserver.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author : zikoz
 * @created : 09 Jul, 2021
 */

@RestController
@RequestMapping("/token")
public class TokenController {

    @GetMapping
    public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt){
        return Collections.singletonMap("principal", jwt);
//        return jwt.getTokenValue();
    }

    @GetMapping(path = "/2")
    public Jwt getToken2(@AuthenticationPrincipal Jwt jwt){
        return jwt;
    }
}
