package com.project.msv.repository;

import com.project.msv.dto.response.vocaBoard.VocaBoardListRes;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface VocaBoardRepositoryCustom {

    PageImpl<VocaBoardListRes> findVocaBoardList(String title, Long userId,Pageable pageable);
}
