package com.project.msv.service;

import com.project.msv.domain.GiveMoney;
import com.project.msv.domain.Member;
import com.project.msv.dto.GiveMoneyDto;
import com.project.msv.repository.GiveMoneyRepository;
import com.project.msv.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GiveMoneyService {

    private final GiveMoneyRepository giveMoneyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long savePoint(GiveMoneyDto giveMoneyDto, Member member) {
        GiveMoney save = giveMoneyRepository.save(giveMoneyDto.toEntity(member));
        Member member1 = memberRepository.findById(member.getId()).get();

        int realPoint = 0;
        realPoint = member1.getPoint() + save.getMoney();
        member1.payment(realPoint);

        return save.getId();
    }
}
