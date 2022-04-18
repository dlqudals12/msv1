package com.project.msv.domain;

import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.domain.voca.Voca;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VocaBoard extends CUpdate {

    @Id @GeneratedValue
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

    public void updateCount(int count) {
        this.count = count;
    }

    public void updateBuycount(int count) {
        this.buycount = count;
    }
}
