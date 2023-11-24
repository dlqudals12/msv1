package com.project.msv.config.security;


import com.project.msv.config.security.model.CustomDetails;
import com.project.msv.domain.User;
import com.project.msv.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 토큰 검사할 url 목록
    private final String[] urls = {
            "/api/user/save_user",
            "/api/user/login",
            "/api/voca_board/list_voca_board",
            "/swagger-ui"
    };
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        if (checkTokenPass(url)) {
            try {
                String accessToken = JwtTokenProvider.getJwtFromRequest(request, "accessToken"); // request에서 jwt 토큰을 꺼낸다.
                String refreshToken = JwtTokenProvider.getJwtFromRequest(request, "refreshToken");
                if (!ObjectUtils.isEmpty(accessToken) && JwtTokenProvider.validateToken(accessToken)) {
                    Long userIdx = Long.parseLong(JwtTokenProvider.getValueFromJWT(accessToken, "idx") + ""); // jwt에서 사용자 idx를

                    Optional<User> userOp = userService.findUserById(userIdx);

                    if(userOp.isEmpty()) {
                        deleteTokens(response);
                        request.setAttribute("unauthorization", "401"); // 인증키 없음
                        response.sendError(401, "unauthorized");
                        return;
                    }

                    saveAuthentication(userOp.get());

                } else if(!ObjectUtils.isEmpty(refreshToken) && !JwtTokenProvider.validateToken(refreshToken)) {
                        Long userIdx = Long.parseLong(JwtTokenProvider.getValueFromJWT(refreshToken, "idx") + ""); // jwt에서 사용자 idx를

                        Optional<User> userOp = userService.findUserById(userIdx);

                        if(!userOp.isPresent()) {
                            deleteTokens(response);
                            request.setAttribute("unauthorization", "401"); // 인증키 없음
                            response.sendError(401, "unauthorized");
                            return;
                        }

                        addAccessTokens(userOp.get(), response);
                        saveAuthentication(userOp.get());
                } else {
                    if (ObjectUtils.isEmpty(accessToken)) {
                        request.setAttribute("unauthorization", "401"); // 인증키 없음
                        response.sendError(401, "unauthorized");
                        return;
                    }
                }
            } catch (Exception ex) {
                logger.error("Could not set user authentication in security context", ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkTokenPass(String url) {
        return  !Arrays.stream(urls).map(url::contains).toList().contains(true);

    }

    public void saveAuthentication(User user) {
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        UserAuthentication authentication = new UserAuthentication(user.getLoginId(), null, auth); // id를 인증한다.
        CustomDetails userDetails = new CustomDetails(user.getId(), user.getLoginId(), user.getRole());
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void deleteTokens(HttpServletResponse response) {
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setHttpOnly(true);
        accessToken.setMaxAge(0);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(0);


        Cookie userId = new Cookie("userId", null);
        userId.setPath("/");
        userId.setMaxAge(0);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        response.addCookie(userId);
    }

    public void addAccessTokens(User user, HttpServletResponse response) {
        Cookie accessToken = new Cookie("accessToken", JwtTokenProvider.generateToken(user.getId(), JwtTokenProvider.TokenType.ACCESS));
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        accessToken.setMaxAge(100 * 60 * 60);

        response.addCookie(accessToken);
    }

}