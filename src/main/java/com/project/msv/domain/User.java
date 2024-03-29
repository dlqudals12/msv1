package com.project.msv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.domain.enums.Role;
import com.project.msv.dto.request.user.UpdateUserReq;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User extends CUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @Column(unique = true, nullable = false, length = 50, name = "login_id")
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

    public void updateUser(UpdateUserReq updateUserReq) {
        if (updateUserReq.getType().equals("password")) {
            this.password = updateUserReq.getPassword();
        } else {
            this.name = updateUserReq.getName();
            this.email = updateUserReq.getEmail();
            this.phone = updateUserReq.getPhone();
        }
    }


}
