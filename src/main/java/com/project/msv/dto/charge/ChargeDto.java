package com.project.msv.dto.charge;

import com.project.msv.domain.Charge;
import com.project.msv.domain.Member;
import com.project.msv.domain.Status;
import lombok.Data;

import javax.persistence.*;

@Data
public class ChargeDto {

    private int money;
    private Status status;
    private String banknum;
    private String bank;
    private String name;

    public Charge toEntity(Member member) {
        Charge charge = Charge.builder()
                .bank(bank)
                .member(member)
                .status(Status.NO)
                .money(money)
                .name(name)
                .banknum(banknum)
                .build();
        return charge;
    }
}
