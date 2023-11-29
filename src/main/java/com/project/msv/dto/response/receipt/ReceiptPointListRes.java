package com.project.msv.dto.response.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptPointListRes implements ReceiptPointListResIn {

    private Long id;
    private String vocaName;
    private String toUser;
    private String fromUser;
    private String status;
    private int point;
    private int buyCount;
    private String receiptType;
    private LocalDateTime regDt;


}
