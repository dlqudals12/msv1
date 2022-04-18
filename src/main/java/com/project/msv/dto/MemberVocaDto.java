package com.project.msv.dto;

import com.project.msv.domain.Member;
import com.project.msv.domain.Membervoca;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberVocaDto {

    private Long vocaboardid;

    @Builder
    public MemberVocaDto(Long vocaboardid) {
        this.vocaboardid = vocaboardid;
    }

    public Membervoca toEntity(Member member) {
        return Membervoca.builder()
                .vocaboardid(vocaboardid)
                .member(member)
                .build();
    }
}
