package com.project.msv.dto;

import com.project.msv.domain.VocaBoard;
import com.project.msv.domain.voca.Voca;
import lombok.Data;

@Data
public class VocaBoardDto {
    private String title;
    private String board;
    private int point;

    public VocaBoardDto(String title, String board, int point) {
        this.title = title;
        this.board = board;
        this.point = point;
    }

    public VocaBoard toEntity(Voca voca) {
        return VocaBoard.builder()
                .board(board)
                .point(point)
                .title(title)
                .voca(voca)
                .count(0)
                .buycount(0)
                .build();
    }
}
