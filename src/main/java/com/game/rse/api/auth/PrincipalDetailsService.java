package com.game.rse.api.auth;


import com.game.rse.api.auth.model.PrincipalDetails;
import com.game.rse.api.auth.user.UserProvider;
import com.game.rse.api.auth.util.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class PrincipalDetailsService implements UserDetailsService {

    private final UserProvider userProvider;

    @Autowired
    public PrincipalDetailsService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return new PrincipalDetails(userProvider.retrieveByEmail(email));
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }
}
