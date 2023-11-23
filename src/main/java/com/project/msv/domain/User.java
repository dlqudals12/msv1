package com.project.msv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.domain.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends CUpdate{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String loginId;

    @Column(nullable = false, unique = true, length = 200)
    @JsonIgnore
    private String password;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(nullable = false)
    @Setter
    private int point;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Exchange> exchange = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Voca> voca = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<TradeVoca> tradeVocas = new ArrayList<>();



}
