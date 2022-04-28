package com.project.msv.controller;

import com.project.msv.domain.Member;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.MemberDto;
import com.project.msv.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String sign() {
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String sign(MemberDto memberDto) {
        memberService.save(memberDto);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String main(@AuthenticationPrincipal MemberDetail memberDetail) {
        if (memberDetail != null) {
            return "/member/usermain";
        }

        return "/member/main";
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/user/member")
    public String userDetail(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        Optional<Member> byId = memberService.findById(memberDetail.getMember().getId());
        model.addAttribute("member", byId.get());
        return "/member/user";
    }
}
