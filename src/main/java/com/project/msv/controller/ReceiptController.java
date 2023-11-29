package com.project.msv.controller;

import com.project.msv.config.security.model.CustomDetails;
import com.project.msv.dto.request.receipt.ChargePointReq;
import com.project.msv.dto.response.DefaultResponse;
import com.project.msv.service.ReceiptPointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final ReceiptPointService receiptPointService;

    @Operation(description = "거래 내역 조회", summary = "거래 내역 조회")
    @GetMapping(value = "/receipt_list")
    public DefaultResponse findReceiptList(@RequestParam int page, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        return new DefaultResponse(receiptPointService.findReceiptList(details.getIdx(), PageRequest.of(page - 1, 10)));
    }

    @Operation(description = "포인트 충전", summary = "포인트 충전")
    @PostMapping(value = "/charge_point")
    public DefaultResponse chargePoint(@RequestBody ChargePointReq chargePointReq, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();

        receiptPointService.ChargePoint(chargePointReq, details.getIdx());

        return new DefaultResponse();
    }

   
}
