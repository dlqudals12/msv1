package com.project.msv.controller;

import com.project.msv.domain.Charge;
import com.project.msv.dto.GiveMoneyDto;
import com.project.msv.dto.charge.ChargeDto;
import com.project.msv.dto.MemberDetail;
import com.project.msv.service.ChargeService;
import com.project.msv.service.GiveMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChargeController {

    private final ChargeService chargeService;
    private final GiveMoneyService giveMoneyService;

    @GetMapping("/user/charge/new")
    public String newCharge() {
        return "/charge/new";
    }

    @PostMapping("/charge/new")
    public String newCharge(ChargeDto chargeDto, @AuthenticationPrincipal MemberDetail memberDetail) {
        chargeService.save(chargeDto, memberDetail.getMember());
        return "redirect:/user/charge/list";
    }

    @GetMapping("/user/charge/list")
    public String chargeList(@AuthenticationPrincipal MemberDetail memberDetail, Model model) {
        List<Charge> chargeList = chargeService.findByMember(memberDetail.getMember());
        model.addAttribute("chargeList", chargeList);
        return "charge/list";
    }

    @GetMapping("admin/chargelist")
    public String adminChargeList(Model model, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {

        Page<Charge> listPage = chargeService.findAll(page);

        int totalPage = listPage.getTotalPages();

        model.addAttribute("chargeList", listPage.getContent());
        model.addAttribute("totalPage", totalPage);

        return "/admin/chargeList";
    }

    @PostMapping("/chargelist/{chargeid}/comp")
    public String chargeComp(@PathVariable("chargeid") Long chargeid) {
        chargeService.updateStatus(chargeid);
        return "redirect:/admin/chargeList";
    }

    @GetMapping("/user/exchange")
    public String exchage() {
        return "/charge/exchange";
    }

    @PostMapping("/user/exchange/point")
    public String exchagepoint(@ModelAttribute GiveMoneyDto giveMoneyDto, @AuthenticationPrincipal MemberDetail memberDetail) {
        giveMoneyService.savePoint(giveMoneyDto, memberDetail.getMember());
        return "redirect:/user/member";
    }
}
