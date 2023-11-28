package com.project.msv.controller;

import com.project.msv.config.security.model.CustomDetails;
import com.project.msv.dto.request.vocaBoard.SaveVocaBoardReq;
import com.project.msv.dto.request.vocaBoard.TradeVocaReq;
import com.project.msv.dto.response.DefaultResponse;
import com.project.msv.service.VocaBoardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voca_board")
public class VocaBoardController {

    private final VocaBoardService vocaBoardService;

    @Operation(summary = "게시판 저장",description = "게시판 저장")
    @PostMapping(value = "/save_voca_board")
    public DefaultResponse saveVocaBoard(@RequestBody SaveVocaBoardReq saveVocaBoardReq, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        vocaBoardService.saveVocaBoard(saveVocaBoardReq, details.getIdx());
        return new DefaultResponse();
    }

    @Operation(summary = "단어장 거래",description = "단어장 거래")
    @PostMapping("/trade_voca")
    public DefaultResponse tradeVoca(@RequestBody TradeVocaReq tradeVocaReq, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        return new DefaultResponse(vocaBoardService.tradeVoca(tradeVocaReq.getVocaBoardId(), details.getIdx()));
    }

    @Operation(summary = "게시판 체크", description = "게시판 체크")
    @GetMapping(value = "/check_voca_board")
    public DefaultResponse checkVocaBoard(@RequestParam Long vocaId, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        vocaBoardService.checkOwnVoca(vocaId, details.getIdx());
        return new DefaultResponse();
    }

    @Operation(summary = "게시판 리스트", description = "게시판 리스트")
    @GetMapping(value = "/list_voca_board")
    public DefaultResponse findVocaBoardList(@RequestParam String title, @RequestParam String loginId,
                                             @RequestParam int page) {
        return new DefaultResponse(vocaBoardService.findVocaBoardList(title, loginId, PageRequest.of(page - 1, 10)));
    }

    @Operation(summary = "게시판 상세", description = "게시판 상세")
    @GetMapping(value = "/voca_board_detail")
    public DefaultResponse findVocaBoardDetail(@RequestParam Long id, @RequestParam boolean updateCount) {
        return new DefaultResponse(vocaBoardService.findVocaBoardById(id, updateCount));
    }

    @Operation(summary = "게시판 상세 USER", description = "게시판 상세 USER")
    @GetMapping(value = "/user_voca_board_detail")
    public DefaultResponse findVocaBoardUserDetail(@RequestParam Long id, @RequestParam boolean updateCount, Authentication authentication) {
        CustomDetails details = (CustomDetails) authentication.getDetails();
        return new DefaultResponse(vocaBoardService.findUserVocaBoardById(id, details.getIdx(),updateCount));
    }
}
