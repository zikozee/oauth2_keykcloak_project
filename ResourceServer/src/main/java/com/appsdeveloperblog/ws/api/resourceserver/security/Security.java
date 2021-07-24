package com.appsdeveloperblog.ws.api.resourceserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


/**
 * @author : zikoz
 * @created : 10 Jul, 2021
 */

// securedEnabled = true enables @Secured
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/status/check")
//                .hasAuthority("SCOPE_profile") // if scope does not include profile
                .hasRole("developer")
//                .hasAuthority("ROLE_developer")
//                .hasAnyRole("developer", "user")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);

        //todo info: adding oauth2ResourceServer()
        // this will create the bearer authentication filter that will intercept http Requests
        // and extract the authorization bearer token and expects the token to be jwt >> .jwt() rather opaqueToken
        // and the token will be validated

        // for RBAC
        // KeycloakConverter class was added
        // JwtAuthenticationConverter initialized and .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }
}
