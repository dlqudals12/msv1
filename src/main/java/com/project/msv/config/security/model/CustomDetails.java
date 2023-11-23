package com.project.msv.config.security.model;

import com.project.msv.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CustomDetails {

    private Long idx;
    private String loginId;
    private Role role;
}
