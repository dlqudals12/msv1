package com.project.msv.controller;

import com.project.msv.domain.Member;
import com.project.msv.domain.Membervoca;
import com.project.msv.domain.VocaBoard;
import com.project.msv.domain.voca.Voca;
import com.project.msv.domain.voca.VocaWord;
import com.project.msv.service.VocaService;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.VocaDto;
import com.project.msv.dto.VocaWordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VocaController {

    private final VocaService vocaService;

    @GetMapping("/user/voca/new")
    public String save() {
       return "/voca/new";
    }

    @PostMapping("/voca/new")
    public String save(VocaDto vocaDto, @AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        Member member = memberDetail.getMember();
        Long save = vocaService.save(vocaDto, member);
        Voca voca = vocaService.findVoca(save);
        model.addAttribute("voca", voca);
        return "voca/newvoca";
    }

    @GetMapping("/user/voca/newvoca")
    public String newVoca(Model model, @ModelAttribute("vocaid") Long vocaid)
    {
        Voca voca = vocaService.findVoca(vocaid);
        List<VocaWord> vocaWords = voca.getVocaWords();
        model.addAttribute("vocawordList", vocaWords);
        model.addAttribute("voca", voca);
        return "/voca/newVoca";
    }

    @PostMapping("voca/newvoca")
    public String newvoca(RedirectAttributes model, VocaWordDto vocawordDto, @RequestParam("vocaid") Long vocaid) {

        vocaService.saveWord(vocawordDto, vocaid);
        model.addFlashAttribute("vocaid", vocaid);

        return "redirect:/user/voca/newvoca";
    }

    @GetMapping("/user/voca/vocalist")
    public String list(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        List<Voca> voca = vocaService.findVocaByMemberid(memberDetail.getMember().getId());

        model.addAttribute("vocaList", voca);
        return "/voca/list";
    }

    @PostMapping("/vocalist/{vocaid}")
    public String chargeComp(@PathVariable("vocaid") Long vocaid, RedirectAttributes model) {
        Voca voca = vocaService.findVoca(vocaid);
        model.addFlashAttribute("voca", voca);
        return "/vocaboard/new";
    }

    @GetMapping("/user/voca/detail/{id}")
    public String payment(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal MemberDetail memberDetail) {
        if (memberDetail == null) {
            return "redirect:/";
        }
        Voca voca = vocaService.findVoca(id);
        List<VocaWord> byVocaid = vocaService.findByVocaid(id);

        model.addAttribute("voca", voca);
        model.addAttribute("vocawordList", byVocaid);

        return "/voca/vocadetail";
    }



}
