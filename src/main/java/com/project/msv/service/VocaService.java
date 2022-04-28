package com.project.msv.service;

import com.project.msv.domain.Member;
import com.project.msv.domain.voca.Voca;
import com.project.msv.domain.voca.VocaWord;
import com.project.msv.dto.VocaDto;
import com.project.msv.dto.VocaWordDto;
import com.project.msv.repository.VocaJpaRepository;
import com.project.msv.repository.VocaRepository;
import com.project.msv.repository.VocaWordRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocaService {
    private final VocaRepository vocaRepository;
    private final VocaWordRepositoy vocaWordRepositoy;
    private final VocaJpaRepository vocaJpaRepository;

    @Transactional
    public Long save(VocaDto vocaDto, Member member) {
        return vocaRepository.save(vocaDto.toEntity(member)).getId();
    }

    public Voca findVoca(Long vocaId) {
        return vocaRepository.findById(vocaId).get();
    }

    public List<VocaWord> findAll()
    {
        return vocaWordRepositoy.findAll();
    }

    @Transactional
    public List<VocaWord> saveAllWord1(List<VocaWordDto> vocaWordDtos, Voca voca) {
        List<VocaWord> words = new ArrayList<>();
        for (VocaWordDto vocaWordDto : vocaWordDtos) {
            words.add(vocaWordDto.toEntity(voca));
        }
        return words;
    }

    public List<VocaWord> findVocaByVocawordId(Long id) {
        return vocaWordRepositoy.findByVocawordId(id);
    }


    @Transactional
    public Long saveWord(VocaWordDto vocaWordDto, Long vocaid) {
        Optional<Voca> voca = vocaRepository.findById(vocaid);
        return vocaWordRepositoy.save(vocaWordDto.toEntity(voca.get())).getId();
    }

    @Transactional
    public List<VocaWord> saveAllWord(List<VocaWord> vocaWord, Voca voca) {
        List<VocaWord> words = new ArrayList<>();
        for (VocaWord word : vocaWord) {
            words.add(VocaWord.builder()
                    .word4(word.getWord4())
                    .word3(word.getWord3())
                    .word2(word.getWord2())
                    .word1(word.getWord1())
                    .build());
        }
        return words;
    }

    public VocaWord findVocaWord(Long id) {
        return vocaWordRepositoy.findByVocaid(id);
    }

    public List<Voca> findVocaByMemberid(Long id) {
        return vocaJpaRepository.findVocaByMember(id);
    }

    public List<VocaWord> findByVocaid(Long id) {
        return vocaWordRepositoy.findByVocaId(id);
    }


}
