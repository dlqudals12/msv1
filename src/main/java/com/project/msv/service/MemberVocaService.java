package com.project.msv.service;

import com.project.msv.domain.Member;
import com.project.msv.domain.Membervoca;
import com.project.msv.dto.MemberVocaDto;
import com.project.msv.repository.MembervocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberVocaService {

    private final MembervocaRepository membervocaRepository;

    @Transactional
    public Long save(Long id, Member member) {
        MemberVocaDto build = MemberVocaDto.builder().vocaboardid(id).build();
        return membervocaRepository.save(build.toEntity(member)).getId();
    }

    public List<Membervoca> findMemberid(Long id) {
        return membervocaRepository.findByMemberid(id);
    }


}
