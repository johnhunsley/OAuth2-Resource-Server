package com.johnhunsley.oauth2.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class ResourceController {


    /**
     * <p>
     *     Must have the granted scope 'user_info' to access the info of the authenticated Principal
     * </p>
     * @param authentication
     * @param principal
     * @return
     */
    @CrossOrigin
    @PreAuthorize("#oauth2.hasScope('user_info')") //, hasRole('ROLE_USER')"
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal user(Authentication authentication, Principal principal) {
        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
        System.out.println("User organization is " + details.get("organization"));
        return principal;
    }
}
