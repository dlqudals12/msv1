package com.project.msv.dto;

import com.project.msv.domain.voca.Voca;
import com.project.msv.domain.voca.VocaWord;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VocaWordDto {
    private String word1;
    private String word2;
    private String word3;
    private String word4;

    @Builder
    @QueryProjection
    public VocaWordDto(String word1, String word2, String word3, String word4) {
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.word4 = word4;
    }


    public VocaWord toEntity(Voca voca) {
        return VocaWord.builder()
                .word1(word1)
                .word2(word2)
                .word3(word3)
                .word4(word4)
                .voca(voca)
                .build();
    }
}
