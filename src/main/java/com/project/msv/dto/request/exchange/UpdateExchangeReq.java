package com.project.msv.dto.request.exchange;

import com.project.msv.domain.enums.Status;
import lombok.Data;

@Data
public class UpdateExchangeReq {

    private Long exchangeId;
    private Status status;
}
