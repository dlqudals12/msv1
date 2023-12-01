package com.project.msv.dto.request.exchange;

import com.project.msv.domain.Exchange;
import com.project.msv.domain.User;
import com.project.msv.domain.enums.Status;
import lombok.Data;

@Data
public class SaveExchangeReq {

    private int money;
    private String bank;
    private String name;
    private String banknum;

    public Exchange toEntity(User user) {
        return Exchange.builder()
                .bank(bank)
                .banknum(banknum)
                .money(money)
                .name(name)
                .status(Status.PROCESSING)
                .user(user)
                .build();
    }
}
