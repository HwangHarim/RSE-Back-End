package com.game.core.oauth.filter;

import org.springframework.security.core.Authentication;

public interface SetAuthenticationStrategy {
    void set(Authentication authentication);
}
