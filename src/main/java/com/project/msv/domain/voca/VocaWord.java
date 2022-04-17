package com.project.msv.domain.voca;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vocaword")
public class VocaWord {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vocaword_id")
    private Long id;

    @Column(nullable = false)
    private String word1;

    @Column(nullable = false)
    private String word2;

    private String word3;

    private String word4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voca_id")
    private Voca voca;

    @Builder
    public VocaWord(Long id, String word1, String word2, String word3, String word4, Voca voca) {
        this.id = id;
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
        this.voca = voca;
    }
}
