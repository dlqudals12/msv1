package com.project.msv.controller;

import com.project.msv.domain.Member;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.MemberDto;
import com.project.msv.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String sign() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public void sign(MemberDto memberDto, HttpServletResponse response) throws IOException {
        if (memberDto == null) {
            response.sendRedirect("/");
        }

        memberService.save(memberDto);

        response.sendRedirect("/memberAlert?state=signup");
    }

    @GetMapping("/memberAlert")
    public String memberAlert(@RequestParam String state, Model model) {
        model.addAttribute("state", state);

        return "member/memberAlert";
    }

    @GetMapping("/")
    public String main(@AuthenticationPrincipal MemberDetail memberDetail) {
        if (memberDetail != null) {
            return "member/usermain";
        }

        return "member/main";
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/user/member")
    public String userDetail(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        Optional<Member> byId = memberService.findById(memberDetail.getMember().getId());
        model.addAttribute("member", byId.get());
        return "member/user";
    }
}
