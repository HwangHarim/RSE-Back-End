package com.game.core.oauth.presentation;

import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.UserRepository;
import com.game.core.member.infrastructure.annotation.AuthMember;
import com.game.core.oauth.filter.GetAuthenticationStrategy;
import com.game.core.oauth.token.AuthTokenProvider;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final GetAuthenticationStrategy getAuthenticationStrategy;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Objects.nonNull(parameter.getParameterAnnotation(AuthMember.class));
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        var httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        var loggedInUser = loggedInUser(httpServletRequest);
        if (parameter.getParameterType() == Optional.class) {
            return loggedInUser;
        }
        return loggedInUser.orElseThrow(NullPointerException::new);
    }

    private Optional<LoggedInMember> loggedInUser(HttpServletRequest request) {
        var authentication = getAuthenticationStrategy.get(request);
        var user = (UserDetails) authentication.getPrincipal();
        System.out.println(user);
        if (authentication instanceof AuthTokenProvider) {
            return Optional.of((LoggedInMember) authentication.getPrincipal());
        }
        return Optional.empty();
    }
}
