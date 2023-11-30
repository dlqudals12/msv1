package com.project.msv.repository.mapper;

import com.project.msv.dto.response.receipt.ReceiptPointListRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReceiptPointMapper {

    List<ReceiptPointListRes> findReceiptList(Long userId, String receiptType, int limit, Long offset);

    int findReceiptListCount(Long userId, String receiptType);

}
