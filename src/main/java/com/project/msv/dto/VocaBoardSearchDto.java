package com.project.msv.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class VocaBoardSearchDto {
    private String title;

    @Builder
    @QueryProjection
    public VocaBoardSearchDto(String title) {
        this.title = title;
    }
}
