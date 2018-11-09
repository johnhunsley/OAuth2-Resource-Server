package com.johnhunsley.oauth2.resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResourceController {

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }
}
