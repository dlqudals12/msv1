package com.project.msv.config.security;

import com.project.msv.dto.MemberDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login success \nLogin Info: " +  authentication.getName());

        Cookie isLogin = new Cookie("isLogin", authentication.getName());
        isLogin.setPath("/");

        response.addCookie(isLogin);
        response.sendRedirect("/memberAlert?state=login&userName=" + authentication.getName());
    }
}
