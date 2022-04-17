package com.project.msv.dto;

import com.project.msv.domain.Address;
import com.project.msv.domain.Member;
import com.project.msv.domain.Role;
import lombok.*;

@Data
public class MemberDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private String street;
    private int zipcode;



    public Member toEntity() {
        Member build = Member.builder()

                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .role(role.USER)
                .address(new Address(street, zipcode))
                .build();

        return build;
    }

    @Builder
    public MemberDto(String username, String password, String name, String email, String phone, Role role, String street, int zipcode) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.street = street;
        this.zipcode = zipcode;
    }




}
