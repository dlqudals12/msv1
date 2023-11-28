package com.project.msv.dto.request.receipt;

import com.project.msv.domain.ReceiptPoint;
import com.project.msv.domain.enums.ReceiptType;
import lombok.Data;

@Data
public class ChargePointReq {

    private int point;

    public ReceiptPoint toEntity(String fromUser) {
        return ReceiptPoint.builder()
                .fromUser(fromUser)
                .toUser("ROOT@@")
                .point(point)
                .receiptType(ReceiptType.CHARGE)
                .build();
    }
}
