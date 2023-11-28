package com.project.msv.dto.request.vocaBoard;

import com.project.msv.domain.Voca;
import com.project.msv.domain.VocaBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocaBoardDetailDto {
    private Long id;
    private String title;
    private String board;
    private int point;
    private int count;
    private int buycount;
    private Voca voca;
    private boolean own;
    private boolean haveVoca;


}
