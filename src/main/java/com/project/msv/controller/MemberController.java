package com.project.msv.controller;

import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.MemberDto;
import com.project.msv.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
