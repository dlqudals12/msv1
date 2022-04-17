package com.project.msv.dto;

import com.project.msv.domain.Member;
import com.project.msv.domain.voca.Voca;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class VocaDto {

    private String vocaname;
    private String country;
    private String word1name;
    private String word2name;
    private String word3name;
    private String word4name;

    @Builder
    @QueryProjection
    public VocaDto(String vocaname, String country, String word1name, String word2name, String word3name, String word4name) {
        this.vocaname = vocaname;
        this.country = country;
        this.word1name = word1name;
        this.word2name = word2name;
        this.word3name = word3name;
        this.word4name = word4name;
    }

    public Voca toEntity(Member member) {
        return Voca.builder()
                .member(member)
                .vocaname(vocaname)
                .country(country)
                .word1name(word1name)
                .word2name(word2name)
                .word3name(word3name)
                .word4name(word4name)
                .build();

    }
}
