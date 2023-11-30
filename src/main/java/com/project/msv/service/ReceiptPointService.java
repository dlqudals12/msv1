package com.project.msv.service;

import com.project.msv.domain.User;
import com.project.msv.dto.request.receipt.ChargePointReq;
import com.project.msv.dto.response.receipt.ReceiptPointListRes;
import com.project.msv.exception.NoneException;
import com.project.msv.repository.ReceiptPointRepository;
import com.project.msv.repository.UserRepository;
import com.project.msv.repository.mapper.ReceiptPointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptPointService {

    private final UserRepository userRepository;
    private final ReceiptPointRepository receiptPointRepository;
    private final ReceiptPointMapper receiptPointMapper;

    @Transactional
    public int ChargePoint(ChargePointReq chargePointReq, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));
        receiptPointRepository.save(chargePointReq.toEntity(user.getLoginId()));

        user.setPoint(user.getPoint() + chargePointReq.getPoint());

        return user.getPoint();
    }

    public PageImpl<ReceiptPointListRes> findReceiptList(Long userId, String receiptType, Pageable pageable) {
        List<ReceiptPointListRes> receiptList = receiptPointMapper.findReceiptList(userId, receiptType, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(receiptList, pageable, receiptPointMapper.findReceiptListCount(userId, receiptType));
    }

}
