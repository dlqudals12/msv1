package com.project.msv.dto.charge;

import com.project.msv.domain.Status;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ChargeFindDto {
    private int money;
    private String bank;
    private String name;
    private Status status;

    @QueryProjection
    public ChargeFindDto(int money, String bank, String name, Status status) {
        this.money = money;
        this.bank = bank;
        this.name = name;
        this.status = status;
    }
}
