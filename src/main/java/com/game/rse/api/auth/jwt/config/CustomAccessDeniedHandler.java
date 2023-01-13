package com.game.rse.api.auth.jwt.config;

import static com.example.securitystudy.util.BaseResponseStatus.INVALID_USER_JWT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("403 ERROR");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write( "{" + "\"isSuccess\":false, "
                + "\"code\":\"" + INVALID_USER_JWT.getCode() + "\","
                + "\"message\":\"" + INVALID_USER_JWT.getMessage() + "\"}");
        response.getWriter().flush();
    }
}

