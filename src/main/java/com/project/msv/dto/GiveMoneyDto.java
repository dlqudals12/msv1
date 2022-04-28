package com.project.msv.dto;

import com.project.msv.domain.GiveMoney;
import com.project.msv.domain.Member;
import lombok.Builder;
import lombok.Data;

import java.util.zip.GZIPInputStream;

@Data
public class GiveMoneyDto {

    private int money;

    @Builder
    public GiveMoneyDto(int money) {
        this.money = money;
    }

    public GiveMoney toEntity(Member member) {
       return GiveMoney.builder()
                .money(money)
                .member(member)
                .build();
    }
}
