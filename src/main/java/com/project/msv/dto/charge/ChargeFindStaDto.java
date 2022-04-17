package com.project.msv.dto.charge;

import com.project.msv.domain.Status;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ChargeFindStaDto {
    private Status status;

    @QueryProjection
    public ChargeFindStaDto(Status status) {
        this.status = status;
    }
}
