package com.project.msv.controller;

import com.project.msv.domain.VocaBoard;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.VocaBoardDto;
import com.project.msv.service.VocaBoardService;
import com.project.msv.service.VocaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VocaBoardController {

    private final VocaBoardService vocaBoardService;
    private final VocaService vocaService;

    @GetMapping("/vocaboard/listall")
    public String boardList(Model model, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        Page<VocaBoard> listPage = vocaBoardService.findAllBoard(page);
        int totalPage = listPage.getTotalPages();

        model.addAttribute("vocaBoard", listPage.getContent());
        model.addAttribute("totalPage", totalPage);

        return "/vocaboard/listall";
    }

    @GetMapping("/vocaboard/new")
    public String newBoard(Model model, @ModelAttribute("vocaid") Long vocaid) {
        model.addAttribute("voca", vocaService.findVoca(vocaid));
        return "/vocaboard/new";
    }

    @PostMapping("/vocaboard/new")
    public String newBoard(VocaBoardDto vocaBoardDto, @RequestParam("vocaid") Long vocaid) {
        vocaBoardService.save(vocaBoardDto, vocaService.findVoca(vocaid));

        return "redirect:/vocaboard/memberlist";
    }

    @GetMapping("/vocaboard/memberlist")
    public String memberList(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        List<VocaBoard> byMember = vocaBoardService.findByMember(memberDetail.getMember().getId());

        model.addAttribute("vocaBoard",byMember);
        return "/vocaboard/memberlist";
    }

    @GetMapping("/vocaboard/detail")
    public String vocaDetail(@ModelAttribute("vocaboardid") Long id, Model model) {
        VocaBoard byid = vocaBoardService.findByid(id);
        model.addAttribute("vocaboard", byid);

        return "/vocaboard/detail";
    }

    @PostMapping("/vocaboard/detail/{id}")
    public String payment(@RequestParam("id") Long id,Model model) {
        VocaBoard byid = vocaBoardService.findByid(id);
        model.addAttribute("vocaboard", byid);
        return "/vocaboard/detail";
    }
}
