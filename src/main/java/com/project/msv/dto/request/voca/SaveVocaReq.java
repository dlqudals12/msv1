package com.project.msv.dto.request.voca;

import com.project.msv.domain.User;
import com.project.msv.domain.Voca;
import lombok.Data;

@Data
public class SaveVocaReq {

    private String vocaName;
    private String country;
    private String column1;
    private String column2;
    private String column3;
    private String column4;

    public Voca toEntity(User user) {
        return Voca.builder()
                .vocaName(vocaName)
                .country(country)
                .column1(column1)
                .column2(column2)
                .column3(column3)
                .column4(column4)
                .user(user)
                .build();
    }
}
