package com.project.msv.dto;

import com.project.msv.domain.Member;
import com.project.msv.domain.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberSessionDto implements Serializable {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private String street;
    private int zipcode;

    public MemberSessionDto(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.zipcode = member.getAddress().getZipcode();
        this.street = member.getAddress().getStreet();
        this.phone = member.getPhone();
    }
}
