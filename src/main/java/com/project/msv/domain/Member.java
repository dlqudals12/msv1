package com.project.msv.domain;

import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.domain.voca.Voca;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CUpdate{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 200)
    private String password;

    @Column(length = 50)
    private String name;

    @Column(unique = true, length = 50)
    private String email;

    @Column(unique = true, length = 50)
    private String phone;

    @Column(nullable = false)
    private int point;

    private String provider; //oauth2 플렛폼정보
    private String providerId; //oauth2 Id

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;


    //단방향
    @OneToMany(mappedBy = "member")
    List<Charge> charge = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<GiveMoney> realMonies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Voca> voca = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<Membervoca> membervocas = new ArrayList<>();

    //비지니스 로직


    @Builder(builderClassName = "OAuth2Register",builderMethodName = "oAuth2Resister")
    public Member(String username, String password, String email, int point, String provider, String providerId, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.point = point;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    @Builder
    public Member(String username, String password, String name, String email, String phone, int point, Address address, Role role) {
        super();

        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.point = point;
        this.address = address;
        this.role = role;

    }

    public void payment(int point) {
        this.point = point;
    }
}
