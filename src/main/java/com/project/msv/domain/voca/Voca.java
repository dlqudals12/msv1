package com.project.msv.domain.voca;

import com.project.msv.domain.Member;
import com.project.msv.domain.VocaBoard;
import com.project.msv.domain.baseEntity.CUpdate;
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
public class Voca extends CUpdate {
    @Id @GeneratedValue
    @Column(name = "vova_id")
    private Long id;

    @Column(nullable = false)
    private String vocaname;

    @Column(nullable = false)
    private String country;;

    @Column(nullable = false)
    private String word1name;

    @Column(nullable = false)
    private String word2name;

    private String word3name;

    private String word4name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "voca")
    List<VocaWord> vocaWords = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "voca")
    private VocaBoard vocaBoard;

    //비지니스 로직


    @Builder
    public Voca(String vocaname, String country, String word1name, String word2name, String word3name, String word4name, Member member, List<VocaWord> vocaWords, VocaBoard vocaBoard) {
        this.vocaname = vocaname;
        this.country = country;
        this.word1name = word1name;
        this.word2name = word2name;
        this.word3name = word3name;
        this.word4name = word4name;
        this.member = member;
        this.vocaWords = vocaWords;
        this.vocaBoard = vocaBoard;
    }
}