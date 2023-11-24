package com.project.msv.service;

import com.project.msv.domain.TradeVoca;
import com.project.msv.domain.User;
import com.project.msv.domain.Voca;
import com.project.msv.domain.VocaWord;
import com.project.msv.dto.request.voca.SaveVocaReq;
import com.project.msv.dto.request.voca.SaveVocaWordReq;
import com.project.msv.dto.request.voca.UpdateVocaWord;
import com.project.msv.dto.response.voca.VocaListRes;
import com.project.msv.exception.DuplicateException;
import com.project.msv.exception.NoneException;
import com.project.msv.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocaService {

    private final VocaRepository vocaRepository;
    private final UserRepository userRepository;
    private final VocaWordRepository vocaWordRepository;
    private final TradeVocaRepository tradeVocaRepository;

    @Transactional
    public Long saveVoca(SaveVocaReq saveVocaReq, Long idx) {
        if(vocaRepository.countVocaByVocaName(saveVocaReq.getVocaName()) > 0) {
            throw new DuplicateException("단어장이");
        }

        User user = userRepository.findById(idx).orElseThrow(() -> new NoneException("유저가"));

        Voca voca = vocaRepository.save(saveVocaReq.toEntity(user));

        tradeVocaRepository.save(TradeVoca.builder()
                        .voca(voca)
                        .user(user)
                        .build());

        return voca.getId();
    }

    @Transactional
    public void saveVocaWord(SaveVocaWordReq saveVocaWordReq) {
        if(vocaWordRepository.countDuplicateVocaWord(saveVocaWordReq.getWord1(), saveVocaWordReq.getWord2(),
                saveVocaWordReq.getWord3(), saveVocaWordReq.getWord4(), saveVocaWordReq.getVocaId()) > 0){
            throw new DuplicateException("단어장의 요소가");
        }

        vocaWordRepository.save(saveVocaWordReq.toEntity(
                vocaRepository.findById(saveVocaWordReq.getVocaId()).orElseThrow(() -> new NoneException("단어장이"))));
    }

    @Transactional
    public void updateVocaWord(UpdateVocaWord updateVocaWord) {
        VocaWord vocaWord = vocaWordRepository.findById(updateVocaWord.getVocaWordId()).orElseThrow(() -> new NoneException("단어장 요소가"));

        vocaWord.updateVocaWord(updateVocaWord);
    }

    @Transactional
    public void deleteVocaword(Long id) {
        vocaWordRepository.deleteById(id);
    }

    @Transactional
    public void deleteVoca(Long id) {
        vocaRepository.deleteById(id);
    }

    public PageImpl<VocaListRes> findVocaList(String vocaName, Long userId, Pageable pageRequest) {
        return vocaRepository.findVocaList(vocaName, userId, pageRequest);
    }

    public HashMap<String, Object> findVocaWordList(Long vocaId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("voca", vocaRepository.findById(vocaId).orElseThrow(() -> new NoneException("단어장이")));
        result.put("vocaWordList", vocaWordRepository.findVocaWordByVocaIdOrderByRegDtAsc(vocaId));
        return result;
    }

}
