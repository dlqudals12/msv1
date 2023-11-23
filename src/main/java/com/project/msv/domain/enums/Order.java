package com.project.msv.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Order {
    COMP("COMP"),
    NO("NO");

    private final String value;


}
