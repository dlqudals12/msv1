package com.project.msv.controller;

import com.project.msv.config.security.model.CustomDetails;
import com.project.msv.dto.request.user.LoginUserReq;
import com.project.msv.dto.request.user.SaveUserReq;
import com.project.msv.dto.response.DefaultResponse;
import com.project.msv.exception.UserException;
import com.project.msv.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping(value = "/save_user")
    public DefaultResponse saveUser(@RequestBody SaveUserReq saveUserReq) {

        userService.saveUser(saveUserReq);

        return new DefaultResponse();
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping(value = "/login")
    public DefaultResponse login(LoginUserReq loginUserReq, HttpServletResponse response) {

        HashMap<String, Object> result = userService.login(loginUserReq, response);

        return new DefaultResponse(result);
    }

    @Operation(summary = "로그아웃",description = "로그아웃")
    @PostMapping(value = "/logout")
    public DefaultResponse logout(HttpServletResponse response) {

        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setHttpOnly(true);
        accessToken.setMaxAge(0);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setHttpOnly(true);
        refreshToken.setMaxAge(0);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return new DefaultResponse();
    }

    @Operation(summary = "유저의 정보", description = "유저의 정보")
    @GetMapping(value = "/user_info")
    public DefaultResponse userInfo(Authentication authentication) {
        CustomDetails user = (CustomDetails) authentication.getDetails();

        return new DefaultResponse(userService.findUserById(user.getIdx()).orElseThrow(() -> new UserException("없는")));
    }

}
