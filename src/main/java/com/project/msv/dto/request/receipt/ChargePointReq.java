package com.project.msv.dto.request.receipt;

import com.project.msv.domain.ReceiptPoint;
import com.project.msv.domain.enums.PayType;
import com.project.msv.domain.enums.ReceiptType;
import lombok.Data;

@Data
public class ChargePointReq {

    private int point;
    private String orderId;
    private String payId;
    private PayType payType;

    public ReceiptPoint toEntity(String fromUser) {
        return ReceiptPoint.builder()
                .fromUser(fromUser)
                .toUser("ROOT@@")
                .orderId(orderId)
                .payId(payId)
                .point(point)
                .receiptType(ReceiptType.CHARGE)
                .payType(payType)
                .build();
    }
}
