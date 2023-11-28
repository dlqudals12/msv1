package com.project.msv.dto.response.receipt;

import com.project.msv.domain.enums.ReceiptType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceiptPointListRes {

    private Long id;
    private String vocaName;
    private String toUser;
    private String fromUser;
    private String status;
    private int point;
    private int buyCount;
    private LocalDateTime regDt;
}
