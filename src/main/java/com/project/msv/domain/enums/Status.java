package com.project.msv.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Status {
    PROCESSING("처리중"),
    COMPLETED("완료"),
    FAIL("처리 실패")
    ;
    private final String value;

    Status(String value) {
        this.value = value;
    }
}
