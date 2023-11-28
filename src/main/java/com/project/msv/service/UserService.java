package com.project.msv.service;

import com.project.msv.config.security.JwtTokenProvider;
import com.project.msv.domain.User;
import com.project.msv.dto.request.user.LoginUserReq;
import com.project.msv.dto.request.user.SaveUserReq;
import com.project.msv.dto.request.user.UpdateUserReq;
import com.project.msv.exception.DuplicateException;
import com.project.msv.exception.NoMatchesException;
import com.project.msv.exception.NoneException;
import com.project.msv.exception.UserException;
import com.project.msv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(SaveUserReq saveUserReq) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if(userRepository.findUserByLoginId(saveUserReq.getLoginId()).isPresent()) {
            throw new DuplicateException("유저가");
        }

        saveUserReq.setPassword(encoder.encode(saveUserReq.getPassword()));

        userRepository.save(saveUserReq.toEntity());
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public HashMap<String, Object> login(LoginUserReq loginUserReq, HttpServletResponse response) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        HashMap<String, Object> result = new HashMap<>();

        User user = userRepository.findUserByLoginId(loginUserReq.getLoginId()).orElseThrow(() -> new UserException("없는"));

        if(!encoder.matches(loginUserReq.getPassword(), user.getPassword())) {
            throw new NoMatchesException("비밀번호가");
        }

        Cookie accessToken = new Cookie("accessToken", JwtTokenProvider.generateToken(user.getId(), JwtTokenProvider.TokenType.ACCESS));
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        accessToken.setMaxAge(60 * 60);

        Cookie refreshToken = new Cookie("refreshToken", JwtTokenProvider.generateToken(user.getId(), JwtTokenProvider.TokenType.ACCESS));
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(60 * 60 * 24 * 60);

        Cookie userId = new Cookie("userId", user.getLoginId());
        userId.setPath("/");
        userId.setMaxAge(60 * 60 * 24 * 60);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);
        response.addCookie(userId);

        result.put("accessToken", accessToken.getValue());
        result.put("refreshToken", refreshToken.getValue());
        result.put("loginId", user.getLoginId());
        result.put("role", user.getRole());
        result.put("userName", user.getName());

        return result;
    }

    @Transactional
    public void updateUser(UpdateUserReq updateUserReq, Long userId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));

        if(updateUserReq.getType().equals("password")) updateUserReq.setPassword(encoder.encode(updateUserReq.getPassword()));

        user.updateUser(updateUserReq);
    }
}
