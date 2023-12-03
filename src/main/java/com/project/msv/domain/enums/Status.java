package com.project.msv.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
    PROCESSING("처리중"),
    COMPLETED("완료"),
    CANCEL("처리 거부"),
    REVOKE("취소");
    private final String value;

    Status(String value) {
        this.value = value;
    }
}
