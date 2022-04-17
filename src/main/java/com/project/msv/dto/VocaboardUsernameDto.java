package com.project.msv.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
@RequiredArgsConstructor
public class VocaboardUsernameDto {
    private String title;
    private String board;
    private int point;
    private String username;

    @QueryProjection
    @Builder
    public VocaboardUsernameDto(String title, String board, int point, String username) {
        this.title = title;
        this.board = board;
        this.point = point;
        this.username = username;
    }
}
