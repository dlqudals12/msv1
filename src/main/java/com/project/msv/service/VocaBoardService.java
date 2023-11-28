package com.project.msv.service;

import com.project.msv.domain.*;
import com.project.msv.domain.enums.ReceiptType;
import com.project.msv.dto.request.vocaBoard.SaveVocaBoardReq;
import com.project.msv.dto.request.vocaBoard.VocaBoardDetailDto;
import com.project.msv.dto.response.vocaBoard.VocaBoardListRes;
import com.project.msv.exception.*;
import com.project.msv.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        System.out.println(vocaBoardRepository.countVocaBoardByVocaIdOrTitle(saveVocaBoardReq.getVocaId(), saveVocaBoardReq.getTitle()));
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
    public int tradeVoca(Long vocaBoardId, Long userId) {
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
        vocaBoard.updateBuycount();

        receiptPointRepository.save(ReceiptPoint.builder()
                        .vocaBoardId(vocaBoardId)
                        .point(vocaBoard.getPoint())
                        .fromUser(user.getLoginId())
                        .toUser(voca.getUser().getLoginId())
                        .receiptType(ReceiptType.DEALVOCA)
                .build());

        return user.getPoint();
    }


    public PageImpl<VocaBoardListRes> findVocaBoardList(String title, String loginId,Pageable pageable) {
        User user = null;
        if(!loginId.isEmpty()) user = userRepository.findUserByLoginId(loginId).orElseThrow(() -> new NoAccessUserException());
        return vocaBoardRepository.findVocaBoardList(title,user== null ? null : user.getId(),pageable);
    }

    @Transactional
    public VocaBoardDetailDto findVocaBoardById(Long id, boolean updateCount) {
        VocaBoard vocaBoard = vocaBoardRepository.findById(id).orElseThrow(() -> new NoneException("단어장 거래가"));

        if(updateCount) vocaBoard.updateCount();

        return vocaBoard.toDto(false);
    }

    @Transactional
    public VocaBoardDetailDto findUserVocaBoardById(Long id, Long userId, boolean updateCount) {
        VocaBoard vocaBoard = vocaBoardRepository.findById(id).orElseThrow(() -> new NoneException("단어장 거래가"));
        VocaBoardDetailDto vocaDetail = vocaBoardRepository.findVocaDetail(id, userId);

        if(updateCount) vocaBoard.updateCount();

        return vocaDetail;
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
