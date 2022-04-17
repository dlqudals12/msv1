package com.project.msv.service;

import com.project.msv.domain.Member;
import com.project.msv.domain.VocaBoard;
import com.project.msv.domain.voca.Voca;
import com.project.msv.domain.voca.VocaWord;
import com.project.msv.dto.VocaBoardDto;
import com.project.msv.dto.VocaDto;
import com.project.msv.dto.VocaWordDto;
import com.project.msv.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocaBoardService {

    private final VocaBoardRepository vocaBoardRepository;
    private final VocaBoardJpaRepository vocaBoardJpaRepository;
    private final VocaService vocaService;

    @Transactional
    public Long save(VocaBoardDto vocaBoardDto, Voca voca) {
        return vocaBoardRepository.save(vocaBoardDto.toEntity(voca)).getId();
    }

    //포인트 결제시 복제 저장 로직
    @Transactional
    public List<VocaWord> saveVocaWord(Long id, Member member) {
        List<VocaWordDto> vocaWordDtos = vocaBoardJpaRepository.copyWord(id);
        Optional<VocaBoard> vocaBoard = vocaBoardRepository.findById(id);
        VocaDto vocaDtos = vocaBoardJpaRepository.copyVocaname(id);

        Long save = vocaService.save(vocaDtos, member);
        Voca voca = vocaService.findVoca(save);

        int realPoint = 0;
        realPoint = voca.getMember().getPoint() + vocaBoard.get().getPoint();
        voca.getMember().payment(realPoint);

        int payPoint = 0;
        payPoint = member.getPoint() - vocaBoard.get().getPoint();
        member.payment(payPoint);
        if (payPoint < 0) {
            throw new IllegalStateException("Point가 부족합니다");
        }

        int buycount = 0;
        buycount = vocaBoard.get().getBuycount() + 1;
        vocaBoard.get().updateBuycount(buycount);

        return vocaService.saveAllWord1(vocaWordDtos, voca);
    }

    public Page<VocaBoard> findAllBoard(int page) {
        return vocaBoardRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<VocaBoard> findByMember(Long id) {
        return vocaBoardJpaRepository.findByMemberid(id);
    }

    public VocaBoard findByid(Long id) {
        VocaBoard board = vocaBoardRepository.findById(id).get();

        int realcount = 0;
        realcount = board.getCount() + 1;

        board.updateCount(realcount);

        return board;
    }
}
