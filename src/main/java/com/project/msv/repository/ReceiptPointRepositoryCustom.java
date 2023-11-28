package com.project.msv.repository;

import com.project.msv.dto.response.receipt.ReceiptPointListRes;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ReceiptPointRepositoryCustom {


    PageImpl<ReceiptPointListRes> findReceiptList(Long userId, Pageable pageable);
}
