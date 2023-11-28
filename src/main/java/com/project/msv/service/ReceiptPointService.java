package com.project.msv.service;

import com.project.msv.domain.User;
import com.project.msv.dto.request.receipt.ChargePointReq;
import com.project.msv.dto.response.receipt.ReceiptPointListRes;
import com.project.msv.exception.NoneException;
import com.project.msv.repository.ReceiptPointRepository;
import com.project.msv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReceiptPointService {

    private final UserRepository userRepository;
    private final ReceiptPointRepository receiptPointRepository;

    @Transactional
    public void ChargePoint(ChargePointReq chargePointReq, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));
        receiptPointRepository.save(chargePointReq.toEntity(user.getLoginId()));

        user.setPoint(user.getPoint() + chargePointReq.getPoint());
    }

    public PageImpl<ReceiptPointListRes> findReceiptList(Long userId, Pageable pageable) {
        return receiptPointRepository.findReceiptList(userId, pageable);
    }
}
