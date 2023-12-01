package com.project.msv.service;

import com.project.msv.domain.Exchange;
import com.project.msv.domain.ReceiptPoint;
import com.project.msv.domain.User;
import com.project.msv.domain.enums.PayType;
import com.project.msv.domain.enums.ReceiptType;
import com.project.msv.domain.enums.Status;
import com.project.msv.dto.request.exchange.SaveExchangeReq;
import com.project.msv.dto.request.exchange.UpdateExchangeReq;
import com.project.msv.exception.NoneException;
import com.project.msv.repository.ExchangeRepository;
import com.project.msv.repository.ReceiptPointRepository;
import com.project.msv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ReceiptPointRepository receiptPointRepository;
    private final UserRepository userRepository;
    private final ExchangeRepository exchangeRepository;

    public void saveExchange(SaveExchangeReq saveExchangeReq, Long userId) {
        exchangeRepository.save(saveExchangeReq.toEntity(userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"))));
    }

    public void updateState(UpdateExchangeReq updateExchangeReq, Long userId) {
        Exchange exchange = exchangeRepository.findById(updateExchangeReq.getExchangeId()).orElseThrow(() -> new NoneException("환전 내역이"));

        exchange.setStatus(updateExchangeReq.getStatus());

        if (updateExchangeReq.getStatus().equals(Status.COMPLETED)) {
            User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));

            receiptPointRepository.save(ReceiptPoint.builder()
                    .payType(PayType.POINT)
                    .point(exchange.getMoney())
                    .receiptType(ReceiptType.EXCHANGE)
                    .fromUser("ROOT@@")
                    .toUser(user.getLoginId())
                    .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                    .build());
        }
    }


}
