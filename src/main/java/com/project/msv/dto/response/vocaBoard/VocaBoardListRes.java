package com.project.msv.dto.response.vocaBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VocaBoardListRes {

    private Long vocaBoardId;
    private String title;
    private String board;
    private int point;
    private int viewCount;
    private int buyCount;
}
