package com.project.msv.service;

import com.project.msv.domain.ReceiptPoint;
import com.project.msv.domain.User;
import com.project.msv.dto.request.receipt.ChargePointReq;
import com.project.msv.dto.response.receipt.ReceiptPointListResIn;
import com.project.msv.exception.NoneException;
import com.project.msv.repository.ReceiptPointRepository;
import com.project.msv.repository.UserRepository;
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

    @Transactional
    public void ChargePoint(ChargePointReq chargePointReq, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));
        ReceiptPoint entity = chargePointReq.toEntity(user.getLoginId());
        receiptPointRepository.save(entity);
        //receiptPointRepository.save(chargePointReq.toEntity(user.getLoginId()));

        //user.setPoint(user.getPoint() + chargePointReq.getPoint());
    }

    public PageImpl<ReceiptPointListResIn> findReceiptList(Long userId, Pageable pageable) {
        List<ReceiptPointListResIn> receiptList = receiptPointRepository.findReceiptList(userId, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(receiptList, pageable, receiptPointRepository.findReceiptListCount(userId));
    }

}
