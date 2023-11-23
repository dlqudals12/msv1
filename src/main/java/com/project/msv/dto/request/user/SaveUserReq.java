package com.project.msv.dto.request.user;

import com.project.msv.domain.User;
import com.project.msv.domain.enums.Role;
import lombok.Data;

@Data
public class SaveUserReq {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Role role;

    public User toEntity() {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .role(role)
                .build();
    }
}
