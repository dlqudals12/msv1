package com.project.msv.service;

import com.project.msv.domain.Member;
import com.project.msv.dto.MemberDetail;
import com.project.msv.dto.MemberDto;
import com.project.msv.repository.MemberRepository;
import com.project.msv.repository.VocaBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final VocaBoardRepository vocaRepository;

    public Long save(MemberDto memberDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        memberDto.setPassword(encoder.encode(memberDto.getPassword()));

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    //포인트 차감(vocaboard결제)
    public Long boardPayment(MemberDetail memberDetail, Long id) {
        int point = 0;
        Integer memberPoint = memberRepository.findPointById(memberDetail.getMember().getId());
        Integer voca = vocaRepository.findPointById(id);
        point = memberPoint - voca;

        Optional<Member> member = memberRepository.findOpByUsername(memberDetail.getUsername());
        member.get().payment(point);

        return member.get().getId();
    }

    public Optional<Member> findbyvocaid(Long id) {
        return memberRepository.findByVocaid(id);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }


}
