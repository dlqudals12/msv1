package com.project.msv.domain;

import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.domain.baseEntity.Create;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "givemoney")
public class GiveMoney {

    @Id @GeneratedValue
    @Column(name = "givemoney_id")
    private Long id;

    @Column(nullable = false)
    private int money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public GiveMoney(Long id, int money, Member member) {
        this.id = id;
        this.money = money;
        this.member = member;
    }
}
