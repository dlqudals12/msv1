package com.project.msv.dto.response.receipt;

import java.time.LocalDateTime;

public interface ReceiptPointListResIn {
    Long getId();

    String getVocaName();

    String getToUser();

    String getFromUser();

    String getStatus();

    int getPoint();

    int getBuyCount();

    String getReceiptType();

    LocalDateTime getRegDt();

}
