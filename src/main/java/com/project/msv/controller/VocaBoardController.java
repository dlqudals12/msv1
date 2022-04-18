package com.project.msv.controller;

import com.project.msv.domain.Membervoca;
import com.project.msv.domain.VocaBoard;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.VocaBoardDto;
import com.project.msv.service.MemberVocaService;
import com.project.msv.service.VocaBoardService;
import com.project.msv.service.VocaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VocaBoardController {

    private final VocaBoardService vocaBoardService;
    private final VocaService vocaService;
    private final MemberVocaService memberVocaService;

    @GetMapping("/vocaboard/list")
    public String boardList(Model model, @AuthenticationPrincipal MemberDetail memberDetail, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        Page<VocaBoard> listPage = vocaBoardService.findAllBoard(page);
        int totalPage = listPage.getTotalPages();

        model.addAttribute("vocaBoard", listPage.getContent());
        model.addAttribute("totalPage", totalPage);

        if (memberDetail != null) {
            return "/vocaboard/uservocaboard";
        }

        return "/vocaboard/listall";
    }

    @GetMapping("/user/vocaboard/new")
    public String newBoard(Model model, @ModelAttribute("vocaid") Long vocaid) {
        model.addAttribute("voca", vocaService.findVoca(vocaid));
        return "/vocaboard/new";
    }

    @PostMapping("/vocaboard/new")
    public String newBoard(VocaBoardDto vocaBoardDto, @RequestParam("vocaid") Long vocaid) {
        vocaBoardService.save(vocaBoardDto, vocaService.findVoca(vocaid));

        return "redirect:/user/vocaboard/memberlist";
    }

    @GetMapping("/user/vocaboard/memberlist")
    public String memberList(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        List<VocaBoard> byMember = vocaBoardService.findByMember(memberDetail.getMember().getId());

        model.addAttribute("vocaBoard",byMember);
        return "/vocaboard/memberlist";
    }

    @GetMapping("/user/vocaboard/detail/{id}")
    public String payment(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal MemberDetail memberDetail) {
        if (memberDetail == null) {
            return "redirect:/vocaboard/list";
        }
        List<Membervoca> memberid = memberVocaService.findMemberid(memberDetail.getMember().getId());
        VocaBoard byid = vocaBoardService.findByid(id);
        model.addAttribute("membervoca", memberid);
        model.addAttribute("vocaboard", byid);
        return "/vocaboard/detail";
    }

    @PostMapping("/vocaboard/detail/{id}/buy")
    @ResponseBody
    public String buyVoca(@PathVariable("id") Long id,@AuthenticationPrincipal MemberDetail memberDetail) {


        List<Membervoca> memberid = memberVocaService.findMemberid(memberDetail.getMember().getId());
        for (Membervoca membervoca : memberid) {
            if (membervoca.getVocaboardid() == id) {

                return "redirect:/vocaboard/list";
            }
        }

        vocaBoardService.saveVocaWord(id, memberDetail.getMember().getId());

        return "redirect:/";
    }
}
