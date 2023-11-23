package com.project.msv.repository;

import com.project.msv.domain.Voca;
import com.project.msv.dto.response.voca.VocaListRes;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VocaRepositoryCustom {

    PageImpl<VocaListRes> findVocaList(String vocaName, Long userId, Pageable pageable);

}
