/*
 *  Copyright (c) 2023 DMG
 *  All Rights Reserved Worldwide.
 *
 *  THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO DMG
 *  AND CONSTITUTES A VALUABLE TRADE SECRET.
 */

package com.example.springbootsamlexample.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Piyush Kumar.
 * @since 10/02/24.
 */

@RestController
public class HelloController {


    @RequestMapping(path = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDetails(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal){


        Map<String, List<Object>> attributes = principal.getAttributes();


        System.out.println("The attributes are ::: " + attributes);
        Object email = principal.getFirstAttribute("email");
        System.out.println("The email is ::: " + email);

        List<Object> authorities = principal.getAttribute("authorities");
        System.out.println("The authorities (groups) are ::: " + authorities);

        attributes.put("firstAttributeEmail", List.of(email));
        attributes.put("authorities", authorities);

        return ResponseEntity.ok(attributes);
    }


    @RequestMapping(path = "/details2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDetailsUsingSecurityContext(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) authentication.getPrincipal();

        Map<String, List<Object>> attributes = principal.getAttributes();


        System.out.println("The attributes are ::: " + attributes);
        Object email = principal.getFirstAttribute("email");
        System.out.println("The email is ::: " + email);

        List<Object> authorities = principal.getAttribute("authorities");
        System.out.println("The authorities (groups) are ::: " + authorities);

        attributes.put("firstAttributeEmail", List.of(email));
        attributes.put("authorities", authorities);

        return ResponseEntity.ok(attributes);
    }
}
