package com.project.msv.controller;

import com.project.msv.config.security.model.CustomDetails;
import com.project.msv.dto.request.voca.SaveVocaReq;
import com.project.msv.dto.request.voca.SaveVocaWordReq;
import com.project.msv.dto.request.voca.UpdateVocaWord;
import com.project.msv.dto.response.DefaultResponse;
import com.project.msv.repository.VocaRepository;
import com.project.msv.service.VocaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voca")
public class VocaController {

    private final VocaService vocaService;

    @Operation(summary = "단어장 저장",description = "단어장 저장")
    @PostMapping(value = "/save_voca")
    public DefaultResponse saveVoca(@RequestBody SaveVocaReq saveVocaReq, Authentication authentication){
        CustomDetails details = (CustomDetails) authentication.getDetails();
        return new DefaultResponse(vocaService.saveVoca(saveVocaReq, details.getIdx()));
    }

    @Operation(summary = "단어장 조회", description = "단어장 조회")
    @GetMapping(value = "/voca_list")
    public DefaultResponse findVocaList(@RequestParam(required = false, defaultValue = "") String vocaName, Authentication authentication,
                                        @RequestParam(defaultValue = "1") int page) {
        CustomDetails details = (CustomDetails) authentication.getDetails();

        return new DefaultResponse(vocaService.findVocaList(vocaName, details.getIdx(), PageRequest.of((page - 1), 10)));
    }

    @Operation(summary = "단어장 요소 저장", description = "단어장 요소 저장")
    @PostMapping(value = "/save_voca_word")
    public DefaultResponse saveVocaWord(@RequestBody SaveVocaWordReq saveVocaWordReq) {
        vocaService.saveVocaWord(saveVocaWordReq);

        return new DefaultResponse();
    }

    @Operation(summary = "단어장 요소 업데이트", description = "단어장 요소 업데이트")
    @PostMapping(value = "/update_voca_word")
    public DefaultResponse updateVocaWord(@RequestBody UpdateVocaWord updateVocaWord) {
        vocaService.updateVocaWord(updateVocaWord);
        return new DefaultResponse();
    }

    @Operation(summary = "단어장 요소 삭제", description = "단어장 요소 삭제")
    @DeleteMapping(value = "/delete_voca_word/{id}")
    public DefaultResponse deleteVocaWord(@PathVariable Long id) {
        vocaService.deleteVocaword(id);
        return new DefaultResponse();
    }
    @Operation(summary = "단어장 요소 조회", description = "단어장 요소 조회")
    @GetMapping(value = "/voca_word_list")
    public DefaultResponse vocaWorkdList(@RequestParam Long vocaId) {
        return new DefaultResponse(vocaService.findVocaWordList(vocaId));
    }

    @Operation(summary = "단어장 삭제", description = "단어장 삭제")
    @DeleteMapping(value = "/delete_voca/{vocaId}")
    public DefaultResponse deleteVoca(@PathVariable Long vocaId) {
        vocaService.deleteVoca(vocaId);
        return new DefaultResponse();
    }

}
