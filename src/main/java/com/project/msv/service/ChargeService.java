package com.project.msv.service;

import com.project.msv.domain.Charge;
import com.project.msv.domain.Member;
import com.project.msv.domain.Status;
import com.project.msv.dto.charge.ChargeDto;
import com.project.msv.repository.ChargeJpaRepository;
import com.project.msv.repository.ChargeRepository;
import com.project.msv.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChargeService {

    private final ChargeRepository chargeRepository;
    private final ChargeJpaRepository chargeJpaRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(ChargeDto chargeDto, Member member) {
        return chargeRepository.save(chargeDto.toEntity(member)).getId();
    }

    //user charge
    public List<Charge> findByMember(Member member) {
        return chargeJpaRepository.findByMember(member);
    }

    //페이징 findall
    public Page<Charge> findAll(int page) {
        return chargeRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    //admin statusfind
    public List<Charge> findBySta(Status status) {
        return chargeJpaRepository.findBySta(status);
    }

    //charge결제
    @Transactional
    public Long updateStatus(Long chargeId) {
        Optional<Charge> charge = chargeRepository.findById(chargeId);
        charge.get().setStatus(Status.COMP);
        Optional<Member> member = memberRepository.findOpByUsername(charge.get().getMember().getUsername());

        int realpoint = 0;
        realpoint = member.get().getPoint() - charge.get().getMoney();
        member.get().payment(realpoint);

        Optional<Charge> byId = chargeRepository.findById(charge.get().getId());
        memberRepository.findById(member.get().getId());

        return byId.get().getId();
    }




}
