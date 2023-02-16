package com.game.core.oauth.filter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class DefaultAuthenticationStrategy implements GetAuthenticationStrategy, SetAuthenticationStrategy{

    @Override
    public Authentication get(HttpServletRequest request) {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public void set(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}