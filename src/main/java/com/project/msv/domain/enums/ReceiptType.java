package com.project.msv.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReceiptType {

    CHARGE("충전"),
    EXCHANGE("반환"),
    DEALVOCA("단어장 거래");

    private final String value;

}
