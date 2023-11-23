package com.project.msv.dto.request.voca;

import com.project.msv.domain.Voca;
import com.project.msv.domain.VocaWord;
import lombok.Data;

import javax.persistence.Column;

@Data
public class SaveVocaWordReq {
    private Long vocaId;
    private String word1;
    private String word2;
    private String word3;
    private String word4;

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
