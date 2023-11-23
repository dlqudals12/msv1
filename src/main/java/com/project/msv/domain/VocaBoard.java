package com.project.msv.domain;

import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.dto.response.vocaBoard.VocaBoardListRes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VocaBoard extends CUpdate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vocaBoard_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String board;

    @Column(nullable = false)
    private int point;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voca_id")
    private Voca voca;


    private int count;

    private int buycount;



    @Builder
    public VocaBoard(String title, String board, int point, Voca voca, int count, int buycount) {
        this.title = title;
        this.board = board;
        this.point = point;
        this.voca = voca;
        this.count = count;
        this.buycount = buycount;
    }

    public void updateCount() {
        this.count += 1;
    }

    public void updateBuycount() {
        this.buycount += 1;
    }

    public VocaBoardListRes toDto() {
        return VocaBoardListRes.builder()
                .board(board)
                .title(title)
                .point(point)
                .vocaBoardId(voca.getId())
                .viewCount(count)
                .buyCount(buycount)
                .build();
    }
}
