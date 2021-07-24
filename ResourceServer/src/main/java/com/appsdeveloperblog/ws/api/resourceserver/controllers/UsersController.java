package com.appsdeveloperblog.ws.api.resourceserver.controllers;

import com.appsdeveloperblog.ws.api.resourceserver.response.UserRest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * @author : zikoz
 * @created : 09 Jul, 2021
 */

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

    private final Environment env;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port: " + env.getProperty("local.server.port");
    }

//    @Secured("ROLE_developer")
//    @PreAuthorize("hasRole('developer')")
    @PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject") // sub
    @DeleteMapping(path = "{id}")
    public String deleteUser(@PathVariable("id") String id, @AuthenticationPrincipal Jwt jwt){
        return "Deleted user with id "+ id + " and JWT subject " + jwt.getSubject();
    }

    @PostAuthorize("returnObject.userId == #jwt.subject") // sub
    @GetMapping
    public UserRest getUser(@AuthenticationPrincipal Jwt jwt){
        return new UserRest((String) jwt.getClaims().get("given_name"), (String)jwt.getClaims().get("family_name"),
                (String) jwt.getClaims().get("sub"));
    }
}
