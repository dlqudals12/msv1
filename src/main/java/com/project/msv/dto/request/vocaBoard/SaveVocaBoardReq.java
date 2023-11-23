package com.project.msv.dto.request.vocaBoard;

import com.project.msv.domain.Voca;
import com.project.msv.domain.VocaBoard;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
public class SaveVocaBoardReq {

    private String title;
    private String board;
    private int point;
    private Long vocaId;

    public VocaBoard toEntity(Voca voca) {
        return VocaBoard.builder()
                .point(point)
                .title(title)
                .voca(voca)
                .board(board)
                .count(0)
                .buycount(0)
                .build();
    }
}
