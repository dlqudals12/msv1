package com.project.msv.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayType {
    POINT("포인트"),
    TOSS("토스"),
    KAKAO("카카오");
    private final String value;
}
