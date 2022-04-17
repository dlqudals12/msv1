package com.project.msv.domain;

import com.project.msv.domain.baseEntity.Create;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Charge extends Create {

    @Id @GeneratedValue
    @Column(name = "charge_id")
    private Long id;

    private int money;
    private String bank;
    private String name;
    private String banknum;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Charge(int money, String bank, String name, Status status,String banknum, Member member) {
        this.money = money;
        this.bank = bank;
        this.name = name;
        this.status = status;
        this.member = member;
        this.banknum = banknum;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}