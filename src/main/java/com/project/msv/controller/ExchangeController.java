package com.project.msv.controller;

import com.project.msv.config.security.model.CustomDetails;
import com.project.msv.dto.request.exchange.SaveExchangeReq;
import com.project.msv.dto.request.exchange.UpdateExchangeReq;
import com.project.msv.dto.response.DefaultResponse;
import com.project.msv.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @Operation(summary = "환전 저장", description = "환전 저장")
    @PostMapping(value = "/save")
    public DefaultResponse saveExchange(@RequestBody SaveExchangeReq saveExchangeReq, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        exchangeService.saveExchange(saveExchangeReq, details.getIdx());
        return new DefaultResponse();
    }

    @Operation(summary = "환전 상태 업데이트", description = "환전 상태 업데이트")
    @PostMapping(value = "/update_status")
    public DefaultResponse updateStatus(@RequestBody UpdateExchangeReq updateExchangeReq) {
        exchangeService.updateState(updateExchangeReq);

        return new DefaultResponse();
    }

    @Operation(summary = "환전 전체 리스트", description = "환전 전체 리스트")
    @GetMapping(value = "/all_exchange")
    public DefaultResponse findAllExchange() {
        return new DefaultResponse(exchangeService.findAllExchange());
    }

    @Operation(summary = "유저 환전 내역", description = "유저 환전 내역")
    @GetMapping(value = "/exchange_user_list")
    public DefaultResponse finaExchangeUserList(@RequestParam int page, @RequestParam String status, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        return new DefaultResponse(exchangeService.findExchangeUser(PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "regDt")),
                details.getIdx(), status));
    }


}
