package com.project.msv.controller;

import com.project.msv.domain.Member;
import com.project.msv.domain.Membervoca;
import com.project.msv.domain.VocaBoard;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.VocaBoardDto;
import com.project.msv.dto.VocaBoardSearchDto;
import com.project.msv.service.MemberVocaService;
import com.project.msv.service.VocaBoardService;
import com.project.msv.service.VocaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        int totalPage = listPage.getTotalPages() == 0 ? 1 : listPage.getTotalPages();
        System.out.println(totalPage);
        model.addAttribute("vocaBoard", listPage.getContent());
        model.addAttribute("totalPage", totalPage);

        return "/vocaboard/listall";
    }

    @GetMapping("/user/vocaboard/new")
    public String newBoard(Model model, @ModelAttribute("vocaid") Long vocaid) {
        model.addAttribute("voca", vocaService.findVoca(vocaid));
        return "/vocaboard/new";
    }

    @PostMapping("/vocaboard/new")
    public String newBoard(VocaBoardDto vocaBoardDto, @RequestParam("vocaid") Long vocaid, @AuthenticationPrincipal MemberDetail memberDetail) {
        Long save = vocaBoardService.save(vocaBoardDto, vocaService.findVoca(vocaid));
        memberVocaService.save(save, memberDetail.getMember());

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
    public String buyVoca(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetail memberDetail
    ,HttpServletResponse response) throws IOException {


        List<Membervoca> memberid = memberVocaService.findMemberid(memberDetail.getMember().getId());
        for (Membervoca membervoca : memberid) {
            if (membervoca.getVocaboardid() == id) {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.println("<script>alert('이미 존재하는 단어장입니다.'); location.href='http://localhost:8080/vocaboard/list'</script>");
                writer.flush();
            }
        }

        vocaBoardService.saveVocaWord(id, memberDetail.getMember().getId());

        return "redirect:/";
    }

    @PostMapping("/vocaboard/list/search")
    public String vocaboardSearch(RedirectAttributes model, VocaBoardSearchDto vocaBoardSearchDto,
                                  @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        PageRequest of = PageRequest.of(page, 10);
        Page<VocaBoard> title = vocaBoardService.findBytitle(vocaBoardSearchDto, of);



        int totalPage = title.getTotalPages();

        model.addFlashAttribute("totalPage", totalPage);
        model.addFlashAttribute("vocaBoard", title.getContent());



        return "redirect:/vocaboard/listsearch";

    }

    @GetMapping("/vocaboard/listsearch")
    public String vocasearch(@AuthenticationPrincipal MemberDetail memberDetail) {
        return memberDetail == null ? "vocaboard/searchboard" : "vocaboard/searchuserboard";
    }
}
