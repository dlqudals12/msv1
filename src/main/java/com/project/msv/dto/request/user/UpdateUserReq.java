package com.project.msv.dto.request.user;

import lombok.Data;

@Data
public class UpdateUserReq {

    private String name;
    private String email;
    private String phone;
    private String password;
    private String type;
}
