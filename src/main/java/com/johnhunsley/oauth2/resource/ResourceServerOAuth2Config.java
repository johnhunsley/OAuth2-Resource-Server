package com.johnhunsley.oauth2.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Map;

/**
 * <p>
 *     A configuration for a resource server in an OAuth flow which authenticates requests by interrogating the
 *     access token of a JWT. The JWT configuration includes a private signing key, given by the Authorization
 *     Server and provision to extract custom claims from the token.
 *
 * </p>
 */
@Configuration
@EnableResourceServer
public class ResourceServerOAuth2Config extends ResourceServerConfigurerAdapter {
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * <p>
     *     Adds the signing key and the custom claims converter to the JWT converter
     * </p>
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(customAccessTokenConverter());
        converter.setSigningKey("ab45hc392K4D7Sx2354");
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    /**
     * <p>
     *     An {@link org.springframework.security.oauth2.provider.token.AccessTokenConverter} which extracts
     *     the Custom Claims from the token and sets them as the details in the {@link Authentication} object
     * </p>
     * @return
     */
    @Bean
    public DefaultAccessTokenConverter customAccessTokenConverter() {

        return new DefaultAccessTokenConverter() {
            @Override
            public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
                OAuth2Authentication authentication = super.extractAuthentication(claims);
                authentication.setDetails(claims);
                return authentication;
            }
        };
    }
}
