package com.project.msv.domain;

import com.project.msv.domain.baseEntity.Create;
import com.project.msv.domain.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exchange extends Create {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long id;

    private int money;
    private String bank;
    private String name;
    private String banknum;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Exchange(int money, String bank, String name, Status status, String banknum, User user) {
        this.money = money;
        this.bank = bank;
        this.name = name;
        this.status = status;
        this.user = user;
        this.banknum = banknum;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}