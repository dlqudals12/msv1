package com.project.msv.repository;

import com.project.msv.domain.VocaBoard;
import com.project.msv.dto.request.vocaBoard.VocaBoardDetailDto;
import com.project.msv.dto.response.vocaBoard.VocaBoardListRes;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VocaBoardRepositoryCustom {

    PageImpl<VocaBoardListRes> findVocaBoardList(String title, Long userId, Pageable pageable);

    VocaBoardDetailDto findVocaDetail(Long id, Long userId);

    List<VocaBoard> findVocaBoardList();
}
