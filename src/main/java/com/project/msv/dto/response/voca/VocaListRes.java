package com.project.msv.dto.response.voca;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocaListRes {

    private Long vocaId;
    private String vocaName;
    private String country;
    private String column1;
    private String column2;
    private String column3;
    private String column4;
    private boolean own;
    private boolean sell;


}
