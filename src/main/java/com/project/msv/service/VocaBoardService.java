package com.project.msv.service;

import com.project.msv.domain.*;
import com.project.msv.domain.enums.ReceiptType;
import com.project.msv.dto.request.vocaBoard.SaveVocaBoardReq;
import com.project.msv.dto.response.vocaBoard.VocaBoardListRes;
import com.project.msv.exception.*;
import com.project.msv.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocaBoardService {

    private final VocaRepository vocaRepository;
    private final VocaBoardRepository vocaBoardRepository;
    private final TradeVocaRepository tradeVocaRepository;
    private final UserRepository userRepository;
    private final ReceiptPointRepository receiptPointRepository;

    @Transactional
    public void saveVocaBoard(SaveVocaBoardReq saveVocaBoardReq, Long userId) {
        if(vocaBoardRepository.countVocaBoardByVocaIdOrTitle(saveVocaBoardReq.getVocaId(), saveVocaBoardReq.getTitle()) > 0) {
            throw new DuplicateException("단어장 거래가");
        }

        Voca voca = vocaRepository.findById(saveVocaBoardReq.getVocaId())
                .orElseThrow(() -> new NoneException("단어장이"));

        if(!userId.equals(voca.getUser().getId())) {
            throw new NoAccessUserException();
        }

        vocaBoardRepository.save(saveVocaBoardReq.toEntity(voca));
    }

    @Transactional
    public void tradeVoca(Long vocaBoardId, Long userId) {
        VocaBoard vocaBoard = vocaBoardRepository.findById(vocaBoardId).orElseThrow(() -> new NoneException("게시판이"));

        if(tradeVocaRepository.countByUserIdAndVocaId(userId, vocaBoard.getVoca().getId()) != 0) {
            throw new DuplicateException("단어장이");
        }

        Voca voca = vocaRepository.findById(vocaBoard.getVoca().getId()).orElseThrow(() -> new NoneException("단어장이"));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("없는"));

        if(user.getPoint() < vocaBoard.getPoint()) {
            throw new NotEnoughException("포인트가");
        }

        tradeVocaRepository.save(TradeVoca.builder()
                .user(user)
                .voca(voca)
                .build());

        user.setPoint(user.getPoint() - vocaBoard.getPoint());
        voca.getUser().setPoint(voca.getUser().getPoint() + vocaBoard.getPoint());

        receiptPointRepository.save(ReceiptPoint.builder()
                        .point(vocaBoard.getPoint())
                        .fromUser(user.getLoginId())
                        .toUser(voca.getUser().getLoginId())
                        .receiptType(ReceiptType.DEALVOCA)
                .build());
    }


    public PageImpl<VocaBoardListRes> findVocaBoardList(String title, String loginId,Pageable pageable) {
        User user = userRepository.findUserByLoginId(loginId).orElseThrow(() -> new NoAccessUserException());
        return vocaBoardRepository.findVocaBoardList(title,user.getId(),pageable);
    }

    public VocaBoard findVocaBoardById(Long id) {
        return vocaBoardRepository.findById(id).orElseThrow(() -> new NoneException("단어장 거래가"));
    }

    public void checkOwnVoca(Long vocaId, Long userId) {
        Voca voca = vocaRepository.findById(vocaId)
                .orElseThrow(() -> new NoneException("단어장이"));

        if(!userId.equals(voca.getUser().getId())) {
            throw new NoAccessUserException();
        }

        if(vocaBoardRepository.countVocaBoardByVocaIdOrTitle(vocaId, "") > 0) {
            throw new DuplicateException("단어장 거래가");
        }
    }



}
