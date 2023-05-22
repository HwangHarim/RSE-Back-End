package com.game.core.member.controller.auth;

import com.game.core.common.properties.AppProperties;
import com.game.core.common.response.dto.ResponseDto;
import com.game.core.common.response.dto.ResponseMessage;
import com.game.core.common.response.handler.ResponseHandler;
import com.game.core.config.util.CookieUtil;
import com.game.core.config.util.HeaderUtil;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.token.InvalidAccessTokenException;
import com.game.core.error.exception.token.InvalidRefreshTokenException;
import com.game.core.error.exception.token.NotExpiredTokenException;
import com.game.core.member.application.UserService;
import com.game.core.member.domain.UserRefreshToken;
import com.game.core.member.dto.AuthReqModel;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.UserRefreshTokenRepository;
import com.game.core.member.infrastructure.annotation.AuthMember;
import com.game.core.oauth.domain.RoleType;
import com.game.core.oauth.domain.UserPrincipal;
import com.game.core.oauth.token.AuthToken;
import com.game.core.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final UserService userService;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final ResponseHandler responseHandler;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @DeleteMapping
    public ResponseEntity<ResponseDto> removeUser(@AuthMember LoggedInMember loggedInMember){
        userService.removeUser(loggedInMember);
        return responseHandler.toResponseEntity(ResponseMessage.DELETE_USER,"삭제 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthReqModel authReqModel
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );

        String userId = authReqModel.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return responseHandler.toResponseEntity(ResponseMessage.CREATE_TOKEN_SUCCESS, accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto> refreshToken (HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (authToken.validate()) {
           throw new InvalidAccessTokenException(ErrorMessage.INVALID_ACCESS_TOKEN);
        }
        //invalidAccessToken
        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            throw new NotExpiredTokenException(ErrorMessage.NOT_EXPIRED_TOKEN_YET);
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (!authRefreshToken.validate()) {
            throw new InvalidRefreshTokenException(ErrorMessage.INVALID_REFRESH_TOKEN);
        }

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            throw new InvalidRefreshTokenException(ErrorMessage.INVALID_REFRESH_TOKEN);
        }

        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return responseHandler.toResponseEntity(ResponseMessage.CREATE_TOKEN_SUCCESS, newAccessToken.getToken());
    }
}